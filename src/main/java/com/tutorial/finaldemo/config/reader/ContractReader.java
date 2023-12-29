package com.tutorial.finaldemo.config.reader;

import com.tutorial.finaldemo.config.ContractConfig;
import com.tutorial.finaldemo.contract.SimpleStorage;
import com.tutorial.finaldemo.dto.ContractResponse;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.item.ItemReader;
import org.web3j.abi.EventEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ContractReader implements ItemReader<ContractResponse> {

    private final ContractConfig contractConfig;
    private final List<ContractResponse> list = new ArrayList<>();
    private EthBlock.Block latestBlock;
    private Disposable subscription;
    private int index;

    public ContractReader(ContractConfig contractConfig) {
        this.contractConfig = contractConfig;
    }

    @BeforeRead
    public void readBlock() {
        Web3j web3j = contractConfig.web3j();
        BigInteger gasPrice = BigInteger.valueOf(200_000L);
        BigInteger gasLimit = BigInteger.valueOf(8_000_000);
        StaticGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);

        SimpleStorage simpleStorage = SimpleStorage.load("0x215695cd356baa734512665e0df7050b8ac5270e", web3j, contractConfig.credentials(), gasProvider);

        try {
            if (latestBlock == null) {
                latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.EARLIEST, false).send().getBlock();
            }

            EthFilter filter = new EthFilter(
                    DefaultBlockParameter.valueOf(latestBlock.getNumber()),
                    DefaultBlockParameterName.LATEST,
                    simpleStorage.getContractAddress()
            );

            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
            filter.addSingleTopic(EventEncoder.encode(SimpleStorage.VALUEUPDATED_EVENT));
            Flowable<SimpleStorage.ValueUpdatedEventResponse> flowable = simpleStorage.valueUpdatedEventFlowable(filter);
            subscription = flowable.subscribe(response -> {
                ContractResponse contractResponse = new ContractResponse();
                contractResponse.setId(Integer.parseInt(String.valueOf(response.id)));
                contractResponse.setNewData(Integer.parseInt(String.valueOf(response.newValue)));
                contractResponse.setCreator(response.creator);
                log.info("block with id = {}", response.id);
                list.add(contractResponse);
            });

            filter.addSingleTopic(EventEncoder.encode(SimpleStorage.VALUEDELETED_EVENT));
            Flowable<SimpleStorage.ValueDeletedEventResponse> flowable1 = simpleStorage.valueDeletedEventFlowable(filter);
            subscription = flowable1.subscribe(response -> {
                ContractResponse contractResponse = new ContractResponse();
                contractResponse.setId(Integer.parseInt(String.valueOf(response.id)));
                contractResponse.setDeletor(response.deleter);
                log.info("block with id = {}", response.id);
                list.add(contractResponse);
            });

            latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();
        } catch (IOException e) {
            log.error("Error while reading block", e);
        }
    }

    @Override
    public ContractResponse read() {
        if (index < list.size()) {
            return list.get(index++);
        } else {
            index = 0;
            list.clear();
            return null;
        }
    }
}

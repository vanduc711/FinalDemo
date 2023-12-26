package com.tutorial.finaldemo.service.impl;

import com.tutorial.finaldemo.contract.SimpleStorage;
import com.tutorial.finaldemo.entity.Contract;
import com.tutorial.finaldemo.reponsitory.ContractReponsitory;
import com.tutorial.finaldemo.service.ContractService;
import io.reactivex.disposables.Disposable;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {


    @Autowired
    ContractReponsitory contractReponsitory;

    public ContractServiceImpl() throws Exception {
    }

    public Optional<Contract> getBlockById(int id) {
        return contractReponsitory.findById(id);
    }

    public void saveBlock(Contract contract) {
        contractReponsitory.save(contract);
    }

    public void deleteBlock(int id) {
        contractReponsitory.deleteById(id);
    }


    BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L); // Set your desired gas price
    BigInteger gasLimit = BigInteger.valueOf(6_300_000); // Set your desired gas limit

    Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:7545"));

    public String privateKey = "0xd11d0e0ab923da8f353dc90bc9b1df0edb41ab64c20eb4cf2ddf98fd3b530c73";
    Credentials credentials = Credentials.create(privateKey);
    StaticGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
    SimpleStorage smartContract = SimpleStorage.deploy(web3j, credentials, gasProvider).send();

    public void ContractSocketConfig() throws Exception {
        startEventListening();
    }

    public String contractAddress() {
        return smartContract.getContractAddress();
    }

    SimpleStorage loadContract = SimpleStorage.load(
            contractAddress(),
            web3j,
            new ClientTransactionManager(web3j, credentials.getAddress()), // Pass the account address, not the private key
            gasProvider
    );

    public SimpleStorage getLoadContract = loadContract;

    private void startEventListening() throws IOException {
        try {
            // After smart contract deployment
            System.out.println("Smart Contract deployed at address: " + smartContract.getContractAddress());

            // Lắng nghe sự kiện của smart contract
            Event event = new Event("ValueUpdated",
                    List.of(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));

            // Lưu trữ Disposable để sau này có thể huỷ lắng nghe sự kiện
            Disposable blockListener1 = web3j.ethLogFlowable(new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, smartContract.getContractAddress()))
                    .filter(log -> log.getTopics().contains(event.getName()))
                    .subscribe(log -> handleEvent((Log) log, event));

            // Lắng nghe sự kiện của từng block
            Disposable blockListener = web3j.blockFlowable(false)
                    .doOnError(Throwable::printStackTrace) // Log error if there's an issue with blockFlowable
                    .subscribe(this::handleBlock);

            // Inside startEventListening() after the event filter setup
            System.out.println("Event listening started for contract address: " + smartContract.getContractAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleEvent(Log log, Event event) {
        // Xử lý sự kiện ở đây
        EventValues eventValues = org.web3j.tx.Contract.staticExtractEventParameters(event, (org.web3j.protocol.core.methods.response.Log) log);
        Address address = (Address) eventValues.getIndexedValues().get(0);
        Uint256 value = (Uint256) eventValues.getIndexedValues().get(1);

        System.out.println("Received event: Address - " + address.getValue() + ", Value - " + value.getValue());
    }

    private void handleBlock(EthBlock ethBlock) {
        // Xử lý dữ liệu của block ở đây
        System.out.println("Block Number: " + ethBlock.getBlock().getTransactions());
        System.out.println("Timestamp: " + ethBlock.getBlock().getTimestamp());
    }
    public List<SimpleStorage.ValueUpdatedEventResponse> createBlock(int value) throws Exception {
        SimpleStorage contract = loadContract;
        // Gọi hàm thêm block từ smart contract
        TransactionReceipt transactionReceipt = contract.create(BigInteger.valueOf(value)).send();

        // Lấy thông tin từ sự kiện emit cụ thể (ví dụ: ValueUpdated)
        return SimpleStorage.getValueUpdatedEvents(transactionReceipt);
    }

    public List<SimpleStorage.ValueUpdatedEventResponse> updateBlock(int id, int newValue) throws Exception {
        SimpleStorage contract = loadContract;

        // Gọi hàm sửa block từ smart contract
        TransactionReceipt transactionReceipt = contract.update(id, BigInteger.valueOf(newValue)).send();
        return SimpleStorage.getValueUpdatedEvents(transactionReceipt);
    }

    public List<SimpleStorage.ValueDeletedEventResponse> removeBlock(int id) throws Exception {
        SimpleStorage contract = loadContract;

        // Gọi hàm xóa block từ smart contract
        TransactionReceipt transactionReceipt = contract.remove(id).send();
        return SimpleStorage.getValueDeletedEvents(transactionReceipt);
    }
}

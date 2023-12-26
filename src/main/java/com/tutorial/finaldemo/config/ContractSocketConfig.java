package com.tutorial.finaldemo.config;

import com.tutorial.finaldemo.contract.SimpleStorage;
import io.reactivex.disposables.Disposable;
import org.apache.commons.logging.Log;
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
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.StaticGasProvider;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class ContractSocketConfig {

    BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L); // Set your desired gas price
    BigInteger gasLimit = BigInteger.valueOf(6_300_000); // Set your desired gas limit

    Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:7545"));

    public String privateKey = "0xd11d0e0ab923da8f353dc90bc9b1df0edb41ab64c20eb4cf2ddf98fd3b530c73";
    Credentials credentials = Credentials.create(privateKey);
    StaticGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
    SimpleStorage smartContract = SimpleStorage.deploy(web3j, credentials, gasProvider).send();

    public ContractSocketConfig() throws Exception {
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
        EventValues eventValues = Contract.staticExtractEventParameters(event, (org.web3j.protocol.core.methods.response.Log) log);
        Address address = (Address) eventValues.getIndexedValues().get(0);
        Uint256 value = (Uint256) eventValues.getIndexedValues().get(1);

        System.out.println("Received event: Address - " + address.getValue() + ", Value - " + value.getValue());
    }

    private void handleBlock(EthBlock ethBlock) {
        // Xử lý dữ liệu của block ở đây
        System.out.println("Block Number: " + ethBlock.getBlock().getTransactions());
        System.out.println("Timestamp: " + ethBlock.getBlock().getTimestamp());
    }
}

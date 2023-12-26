package com.tutorial.finaldemo.contract;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import org.web3j.protocol.http.HttpService;

import org.web3j.tx.gas.StaticGasProvider;


import java.math.BigInteger;

public class InteractSmartConTract {

    private SimpleStorage simpleStorage;

    BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L); // Set your desired gas price
    BigInteger gasLimit = BigInteger.valueOf(6_300_000); // Set your desired gas limit

    Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:7545"));

    String privateKey = "0xc3fff3c5cc595d48313ec18ccff1a1cebdbe97284ee70fbaa288570ecaa7acc8";
    Credentials credentials = Credentials.create(privateKey);

    StaticGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);

    SimpleStorage smartContract = SimpleStorage.deploy(web3j, credentials, gasProvider).send();

    public String contractAddress() {
        return smartContract.getContractAddress();
    }
    public InteractSmartConTract() throws Exception {
    }

//        SimpleStorage loadContract = SimpleStorage.load(
//                contractAddress,
//                web3j,
//                new ClientTransactionManager(web3j, null),
//                new StaticGasProvider(BigInteger.ZERO, BigInteger.valueOf(5000000)));
//

//    public void InteractSmartContract(SimpleStorage simpleStorage) {
//        this.simpleStorage = simpleStorage;
//    }
//
//    public TransactionReceipt setValue(BigInteger newValue) throws Exception {
//        return simpleStorage.create(newValue).send();
//    }
//
//    public Tuple2<BigInteger, String> getValue(BigInteger id) throws Exception {
//        return simpleStorage.get(id).send();
//    }
//
//    public TransactionReceipt removeValue(BigInteger id) throws Exception {
//        return simpleStorage.remove(id).send();
//    }
//
//    public TransactionReceipt updateValue(BigInteger id, BigInteger newData) throws Exception {
//        return simpleStorage.update(id, newData).send();
//    }
//
//    public Flowable<SimpleStorage.ValueDeletedEventResponse> subscribeToValueDeletedEvents(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
//        return simpleStorage.valueDeletedEventFlowable(startBlock, endBlock);
//    }
//
//    public Flowable<SimpleStorage.ValueUpdatedEventResponse> subscribeToValueUpdatedEvents(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
//        return simpleStorage.valueUpdatedEventFlowable(startBlock, endBlock);
//    }

//    public void runExample() {
//        try {
//            // Example: Set a new value
//            BigInteger newValue = BigInteger.valueOf(42);
//            client.setValue(newValue);
//
//            // Example: Get the value
//            BigInteger id = BigInteger.valueOf(1);
//            Tuple2<BigInteger, String> value = client.getValue(id);
//            System.out.println("Value with ID " + id + ": " + value.component1());
//
//            // Example: Remove a value
//            BigInteger idToRemove = BigInteger.valueOf(2);
//            client.removeValue(idToRemove);
//
//            // Example: Update a value
//            BigInteger idToUpdate = BigInteger.valueOf(3);
//            BigInteger newData = BigInteger.valueOf(99);
//            client.updateValue(idToUpdate, newData);
//
//            // Example: Subscribe to events
//            client.subscribeToValueDeletedEvents(DefaultBlockParameter.valueOf("latest"), DefaultBlockParameter.valueOf("latest"))
//                    .subscribe(event -> {
//                        System.out.println("ValueDeleted event - ID: " + event.id + ", Deleter: " + event.deleter);
//                    });
//
//            client.subscribeToValueUpdatedEvents(DefaultBlockParameter.valueOf("latest"), DefaultBlockParameter.valueOf("latest"))
//                    .subscribe(event -> {
//                        System.out.println("ValueUpdated event - ID: " + event.id + ", Creator: " + event.creator + ", NewValue: " + event.newValue);
//                    });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

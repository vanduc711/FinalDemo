package com.tutorial.finaldemo.service.impl;

import com.tutorial.finaldemo.config.ContractConfig;
import com.tutorial.finaldemo.contract.SimpleStorage;
import com.tutorial.finaldemo.entity.Contract;
import com.tutorial.finaldemo.reponsitory.ContractReponsitory;
import com.tutorial.finaldemo.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractConfig contractManager;


    @Autowired
    ContractReponsitory contractReponsitory;

    private SimpleStorage simpleStorage;

    public ContractServiceImpl() throws Exception {
    }

    public Contract saveContract(Contract contract) {
        contractReponsitory.save(contract);
        return contract;
    }

    public Optional<Contract> getBlockById(int id) {
        return contractReponsitory.findById(id);
    }


    public org.web3j.tx.Contract deployContract() throws Exception {
        BigInteger gasPrice = BigInteger.valueOf(2_000_000L);
        BigInteger gasLimit = BigInteger.valueOf(8_000_000);
        StaticGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
        return SimpleStorage.deploy(contractManager.web3j(), contractManager.credentials(), gasProvider).send();
    }

    public String getContractAddress() throws Exception {
        return deployContract().getContractAddress();
    }

    public SimpleStorage loadContract(String contractAddress) {
        BigInteger gasPrice = BigInteger.valueOf(200_000L);
        BigInteger gasLimit = BigInteger.valueOf(8_000_000);
        StaticGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
        return SimpleStorage.load(contractAddress, contractManager.web3j(), contractManager.credentials(), gasProvider);
    }

    public SimpleStorage createEntry(BigInteger newData, String contractAddress) throws Exception {
        SimpleStorage simpleStorageCreate = loadContract(contractAddress);
        simpleStorageCreate.create(newData, contractAddress).send();
        return simpleStorageCreate;
    }

    @Override
    public SimpleStorage updateEntry(int id, int newData, String contractAddress) throws Exception {
        SimpleStorage simpleStorageUpdate = loadContract(contractAddress);
        simpleStorageUpdate.update(BigInteger.valueOf(id), BigInteger.valueOf(newData), contractAddress).send();
        return simpleStorageUpdate;
    }

    @Override
    public SimpleStorage removeEntry(int id, String contractAddress) throws Exception {
        SimpleStorage simpleStorageRemove = loadContract(contractAddress);
        simpleStorageRemove.remove(BigInteger.valueOf(id)).send();
        SimpleStorage.getValueDeletedEvents(new TransactionReceipt());
        return simpleStorageRemove;
    }

    @Override
    public List getEntry(int id, String contractAddress) throws Exception {
        SimpleStorage simpleStorageGet = loadContract(contractAddress);
        return Collections.singletonList(simpleStorageGet.get(BigInteger.valueOf(id)).send());
    }
}
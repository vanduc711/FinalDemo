package com.tutorial.finaldemo.service;

import com.tutorial.finaldemo.contract.SimpleStorage;

import java.math.BigInteger;
import java.util.List;

public interface ContractService {

    org.web3j.tx.Contract deployContract() throws Exception;

    //    void createEntry(BigInteger newData, String contractAddress) throws Exception;
    String getContractAddress() throws Exception;

    SimpleStorage createEntry(BigInteger newData, String contractAddress) throws Exception;

    SimpleStorage updateEntry(int id, int newData, String contractAddress) throws Exception;

    SimpleStorage removeEntry(int id, String contractAddress) throws Exception;

    List getEntry(int id, String contractAddress) throws Exception;
}

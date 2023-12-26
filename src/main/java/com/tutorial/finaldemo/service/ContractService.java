package com.tutorial.finaldemo.service;

import com.tutorial.finaldemo.contract.SimpleStorage;
import com.tutorial.finaldemo.entity.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractService {

    List<SimpleStorage.ValueUpdatedEventResponse> createBlock(int value) throws Exception;

    List<SimpleStorage.ValueUpdatedEventResponse> updateBlock(int id, int newValue) throws Exception;

    List<SimpleStorage.ValueDeletedEventResponse> removeBlock(int id) throws Exception;

    Optional<Contract> getBlockById(int id);

    void saveBlock(Contract contract);
    void deleteBlock(int id);

}

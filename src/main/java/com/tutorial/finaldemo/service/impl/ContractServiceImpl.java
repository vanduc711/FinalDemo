//package com.tutorial.finaldemo.service.impl;
//
//import com.tutorial.finaldemo.entity.Contract;
//import com.tutorial.finaldemo.reponsitory.ContractReponsitory;
//import com.tutorial.finaldemo.service.ContractService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class ContractServiceImpl implements ContractService {
//
//    @Autowired
//    ContractReponsitory contractReponsitory;
//
//    public Optional<Contract> getBlockById(int id) {
//        return contractReponsitory.findById(id);
//    }
//
//    public void saveBlock(Contract contract) {
//        contractReponsitory.save(contract);
//    }
//
//    public void deleteBlock(int id) {
//        contractReponsitory.deleteById(id);
//    }
//}

package com.tutorial.finaldemo.controller;

import com.tutorial.finaldemo.config.ContractConfig;
import com.tutorial.finaldemo.dto.CreateBlockRequest;
import com.tutorial.finaldemo.dto.UpdateBlockRequest;
import com.tutorial.finaldemo.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/contract")
@AllArgsConstructor
public class ContractController {

    @Autowired
    private ContractConfig contractManager;

    @Autowired
    private ContractService contractService;

    @PostMapping("/deploy")
    public String deployContract() {
        try {
            contractService.deployContract();// Assuming this method triggers deployment
            return "Contract deployed successfully at address: " + contractService.getContractAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to deploy contract. Error: " + e.getMessage();
        }
    }

    @PostMapping("/create")
    public String createEntry(@RequestBody CreateBlockRequest request) throws Exception {
        int newData = request.getNewData();
        String contractAddress = request.getContractAddress();
        contractService.createEntry(BigInteger.valueOf(newData), contractAddress);
        return "Create Completed" + contractService.createEntry(BigInteger.valueOf(newData), contractAddress);
    }

    @PutMapping("/update/{id}")
    public String updateEntry(@PathVariable("id") int id, @RequestBody UpdateBlockRequest request) throws Exception {
        int newData = request.getNewData();
        String contractAddress = request.getContractAddress();
        contractService.updateEntry(id, newData, contractAddress);
        return "Update Completed";
    }

    @DeleteMapping("/remove/{id}")
    public String removeEntry(@PathVariable("id") int id, @RequestParam("address") String address) throws Exception {
        contractService.removeEntry(id, address);
        return "Remove Completed";
    }

    @GetMapping("/{id}")
    public String getEntry(@PathVariable("id") int id, @RequestParam("address") String address) throws Exception {
        return "Get Completed" + contractService.getEntry(id, address);
    }


}

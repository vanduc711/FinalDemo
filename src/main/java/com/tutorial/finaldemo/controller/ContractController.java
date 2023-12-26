package com.tutorial.finaldemo.controller;

import com.tutorial.finaldemo.contract.SimpleStorage;
import com.tutorial.finaldemo.dto.CreateBlockRequest;
import com.tutorial.finaldemo.dto.UpdateBlockRequest;
import com.tutorial.finaldemo.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
@AllArgsConstructor
public class ContractController {
    
    
    private final ContractService contractService;

    @PostMapping("/create")
    public List<SimpleStorage.ValueUpdatedEventResponse> createBlock(@RequestBody CreateBlockRequest request) throws Exception {
        int value = request.getValue();
        return  contractService.createBlock(value);
    }

    @PutMapping("/update/{id}")
    public List<SimpleStorage.ValueUpdatedEventResponse> updateBlock(@PathVariable int id, @RequestBody UpdateBlockRequest updateBlockRequest) throws Exception {
            int newValue = updateBlockRequest.getNewValue();
            return contractService.updateBlock(id, newValue);
    }

    @DeleteMapping("/delete/{id}")
    public List<SimpleStorage.ValueDeletedEventResponse> removeBlock(@PathVariable int id) throws Exception {
        return contractService.removeBlock(id);
    }

}

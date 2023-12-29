package com.tutorial.finaldemo.config.process;


import com.tutorial.finaldemo.dto.ContractResponse;
import org.springframework.batch.item.ItemProcessor;

public class ContractProcessor implements ItemProcessor<ContractResponse, ContractResponse> {

    @Override
    public ContractResponse process(ContractResponse item) throws Exception {
        return item;
    }


}
package com.tutorial.finaldemo.config.writer;


import com.tutorial.finaldemo.dto.ContractResponse;
import com.tutorial.finaldemo.entity.Contract;
import com.tutorial.finaldemo.reponsitory.ContractReponsitory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class ContractWriter implements ItemWriter<ContractResponse> {

    private final ContractReponsitory contractReponsitory;


    public ContractWriter(ContractReponsitory contractReponsitory) {
        super();
        this.contractReponsitory = contractReponsitory;
    }

    @Override
    public void write(Chunk<? extends ContractResponse> chunk) throws Exception {
        for (ContractResponse contractResponse : chunk) {
            Contract contract = new Contract();
            contract.setId(contractResponse.getId());
            contract.setCreator(contractResponse.getCreator());
            contract.setValue(contractResponse.getNewData());
            contract.setEvent(contractResponse.getEvent());
            contractReponsitory.save(contract);
        }
    }


}
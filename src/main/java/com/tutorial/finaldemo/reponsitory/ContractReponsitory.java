package com.tutorial.finaldemo.reponsitory;

import com.tutorial.finaldemo.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractReponsitory extends JpaRepository<Contract, Integer> {

}

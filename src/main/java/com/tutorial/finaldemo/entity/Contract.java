package com.tutorial.finaldemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Addressfrom;

    private String Addressto;

    private int value;

    private String creator;

    public int getId(BigInteger blockNumber) {
        return id;
    }

    public String getAddressfrom(String from) {
        return Addressfrom;
    }

    public void setAddressfrom(String addressfrom) {
        Addressfrom = addressfrom;
    }

    public String getAddressto(String transactionReceiptTo) {
        return Addressto;
    }

    public void setAddressto(String addressto) {
        Addressto = addressto;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCreator(String from) {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}

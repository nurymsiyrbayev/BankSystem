package kz.edu.nurym.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "BankCardHistoryEntity")
@Table(name = "bank_card_history")
public class BankCardHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "bankCardId")
    private int bankCardId;
    @Column(name = "userId")
    private int userId;
    @Column(name = "changedDate")
    private Timestamp changedDate;
    @Column(name = "KZTCurrency")
    private float KZTCurrency;
    @Column(name = "EURCurrency")
    private float EURCurrency;
    @Column(name = "USDCurrency")
    private float USDCurrency;
    @Column(name = "newKZTCurrency")
    private float newKZTCurrency;
    @Column(name = "newEURCurrency")
    private float newEURCurrency;
    @Column(name = "newUSDCurrency")
    private float newUSDCurrency;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getBankCardId() {
        return bankCardId;
    }
    public void setBankCardId(int bankCardId) {
        this.bankCardId = bankCardId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getChangedDate() {
        return changedDate;
    }
    public void setChangedDate(Timestamp changedDate) {
        this.changedDate = changedDate;
    }

    public float getKZTCurrency() {
        return KZTCurrency;
    }
    public void setKZTCurrency(float KZTCurrency) {
        this.KZTCurrency = KZTCurrency;
    }

    public float getEURCurrency() {
        return EURCurrency;
    }
    public void setEURCurrency(float EURCurrency) {
        this.EURCurrency = EURCurrency;
    }

    public float getUSDCurrency() {
        return USDCurrency;
    }
    public void setUSDCurrency(float USDCurrency) {
        this.USDCurrency = USDCurrency;
    }

    public float getNewKZTCurrency() {
        return newKZTCurrency;
    }
    public void setNewKZTCurrency(float newKZTCurrency) {
        this.newKZTCurrency = newKZTCurrency;
    }

    public float getNewEURCurrency() {
        return newEURCurrency;
    }
    public void setNewEURCurrency(float newEURCurrency) {
        this.newEURCurrency = newEURCurrency;
    }

    public float getNewUSDCurrency() {
        return newUSDCurrency;
    }
    public void setNewUSDCurrency(float newUSDCurrency) {
        this.newUSDCurrency = newUSDCurrency;
    }
}

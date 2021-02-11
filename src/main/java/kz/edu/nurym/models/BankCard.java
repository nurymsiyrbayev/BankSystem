package kz.edu.nurym.models;

import jakarta.validation.constraints.Size;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "BankCardEntity")
@Table(name = "bank_cards")
public class BankCard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "number",unique = true)
    @Size(min = 12, max = 12)
    private int number;
    @Column(name = "expirationDate")
    private Date expirationDate;
    @Column(name = "secureNumber")
    @Size(min = 3, max = 3)
    private int secureNumber;
    @Column(name = "KZTCurrency")
    private float KZTCurrency;
    @Column(name = "USDCurrency")
    private float USACurrency;
    @Column(name = "EURCurrency")
    private float EURCurrency;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bankId")
    private Bank bank;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;



    public long getBank_card_id() {
        return id;
    }
    public void setBank_card_id(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getSecureNumber() {
        return secureNumber;
    }
    public void setSecureNumber(int secureNumber) {
        this.secureNumber = secureNumber;
    }

    public float getKZTCurrency() {
        return KZTCurrency;
    }
    public void setKZTCurrency(float KZTCurrency) {
        this.KZTCurrency = KZTCurrency;
    }

    public float getUSACurrency() {
        return USACurrency;
    }
    public void setUSACurrency(float USACurrency) {
        this.USACurrency = USACurrency;
    }

    public float getEURCurrency() {
        return EURCurrency;
    }
    public void setEURCurrency(float EURCurrency) {
        this.EURCurrency = EURCurrency;
    }

    public Bank getBank() {
        return bank;
    }
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}

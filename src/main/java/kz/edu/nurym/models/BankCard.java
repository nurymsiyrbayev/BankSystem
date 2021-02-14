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
    private long number;
    @Column(name = "expirationDate")
    private Date expirationDate;
    @Column(name = "secureNumber")
    @Size(min = 3, max = 3)
    private int secureNumber;
    @Column(name = "KZTCurrency", precision=10, scale = 2)
    private double KZTCurrency;
    @Column(name = "USDCurrency", precision=10, scale = 2)
    private double USDCurrency;
    @Column(name = "EURCurrency", precision=10, scale = 2)
    private double EURCurrency;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bankId")
    private Bank bank;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;


    public void setNumber(long number) {
        this.number = number;
    }

    public long getNumber() {
        return number;
    }

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

    public double getKZTCurrency() {
        return KZTCurrency;
    }
    public void setKZTCurrency(double KZTCurrency) {
        this.KZTCurrency = KZTCurrency;
    }

    public double getUSDCurrency() {
        return USDCurrency;
    }
    public void setUSDCurrency(double USDCurrency) {
        this.USDCurrency = USDCurrency;
    }

    public double getEURCurrency() {
        return EURCurrency;
    }
    public void setEURCurrency(double EURCurrency) {
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

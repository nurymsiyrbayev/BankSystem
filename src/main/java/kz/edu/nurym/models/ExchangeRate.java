package kz.edu.nurym.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ExchangeRateEntity")
@Table(name = "exchange_rates")
public class ExchangeRate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "currency")
    private String currency;
    @Column(name = "value")
    private float value;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }
}

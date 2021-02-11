package kz.edu.nurym.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "BankServiceEntity")
@Table(name = "bank_services")
public class BankService implements Serializable {
    private long id;
    private String name;
    private float price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
}

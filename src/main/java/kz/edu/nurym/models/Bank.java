package kz.edu.nurym.models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "BankEntity")
@Table(name = "banks")
public class Bank implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<BankCard> bank_bankCards;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


//    public List<BankCard> getBank_bankCards() {
//        return bank_bankCards;
//    }
//    public void setBank_bankCards(List<BankCard> bank_bankCards) {
//        this.bank_bankCards = bank_bankCards;
//    }
}

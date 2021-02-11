package kz.edu.nurym.services;

import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.User;
import kz.edu.nurym.repository.interfaces.IBankCardRepo;
import kz.edu.nurym.services.interfaces.IBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BankCardService implements IBankCardService {
    private final IBankCardRepo bankCardRepo;

    @Autowired
    public BankCardService(IBankCardRepo bankCardRepo) {
        this.bankCardRepo = bankCardRepo;
    }

    @Override
    public boolean insert(BankCard entity) {
        BankCard bankCardFromDB = bankCardRepo.findByNumber(entity.getNumber());
        if (bankCardFromDB != null) {
            return false;
        }
        bankCardRepo.save(entity);
        return true;
    }

    @Override
    public boolean update(BankCard entity) {
        bankCardRepo.saveAndFlush(entity);
        return true;
    }

    @Override
    public boolean remove(BankCard entity) {
        if (bankCardRepo.findById(entity.getBank_card_id()).isEmpty()) {
            return false;
        }
        bankCardRepo.delete(entity);
        return  false;
    }

    @Override
    public Optional<BankCard> select(long id) {
        return bankCardRepo.findById(id);
    }

    @Override
    public BankCard findByNumber(int number) {
        return bankCardRepo.findByNumber(number);
    }

    @Override
    public Set<BankCard> findAllByUser(User user) {
        return bankCardRepo.findAllByUser(user);
    }
}

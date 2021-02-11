package kz.edu.nurym.services;

import kz.edu.nurym.models.Bank;
import kz.edu.nurym.repository.interfaces.IBankRepo;
import kz.edu.nurym.services.interfaces.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service()
public class BankService implements IBankService {

    private final IBankRepo bankRepo;

    @Autowired
    public BankService(IBankRepo bankRepo) {
        this.bankRepo = bankRepo;
    }

    @Override
    public boolean insert(Bank entity) {
        Bank bankFromDB = bankRepo.findByName(entity.getName());
        if (bankFromDB != null) {
            return false;
        }
        bankRepo.save(entity);
        return true;
    }

    @Override
    public boolean update(Bank entity) {
        bankRepo.saveAndFlush(entity);
        return true;
    }

    @Override
    public boolean remove(Bank entity) {
        if (bankRepo.findById(entity.getId()).isEmpty()) {
            return false;
        }
        bankRepo.delete(entity);
        return  false;
    }

    @Override
    public Optional<Bank> select(long id) {
        return bankRepo.findById(id);
    }

    @Override
    public Bank findByName(String bankName) {
        return bankRepo.findByName(bankName);
    }
}

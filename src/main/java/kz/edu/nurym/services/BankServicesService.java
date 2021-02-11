package kz.edu.nurym.services;

import kz.edu.nurym.models.BankService;
import kz.edu.nurym.repository.interfaces.IBankServicesRepo;
import kz.edu.nurym.services.interfaces.IBankServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankServicesService implements IBankServicesService {

    private final IBankServicesRepo bankServicesRepo;

    @Autowired
    public BankServicesService(IBankServicesRepo bankServicesRepo) {
        this.bankServicesRepo = bankServicesRepo;
    }

    @Override
    public List<BankService> findAll() {
        return bankServicesRepo.findAll();
    }

    @Override
    public Optional<BankService> select(Long id) {
        return bankServicesRepo.findById(id);
    }
}

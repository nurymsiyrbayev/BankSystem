package kz.edu.nurym.services.interfaces;

import kz.edu.nurym.models.BankService;

import java.util.List;
import java.util.Optional;

public interface IBankServicesService {
    List<BankService> findAll();

    Optional<BankService> select(Long id);
}

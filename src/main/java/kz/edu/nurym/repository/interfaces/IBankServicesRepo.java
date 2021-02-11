package kz.edu.nurym.repository.interfaces;

import kz.edu.nurym.models.BankService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBankServicesRepo extends JpaRepository<BankService, Long> {
}

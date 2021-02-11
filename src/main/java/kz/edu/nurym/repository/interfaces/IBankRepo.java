package kz.edu.nurym.repository.interfaces;


import kz.edu.nurym.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankRepo extends JpaRepository<Bank, Long> {
    Bank findByName(String name);
}

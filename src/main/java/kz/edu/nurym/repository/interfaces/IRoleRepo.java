package kz.edu.nurym.repository.interfaces;

import kz.edu.nurym.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepo extends JpaRepository<Role, Long> {
}

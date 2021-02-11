package kz.edu.nurym.repository.interfaces;


import kz.edu.nurym.models.Authority;
import kz.edu.nurym.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IAuthorityRepo extends JpaRepository<Authority, Long> {
    Set<Authority> findAuthoritiesByRoles(Role role);
}

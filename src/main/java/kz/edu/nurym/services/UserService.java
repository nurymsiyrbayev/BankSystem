package kz.edu.nurym.services;

import kz.edu.nurym.filters.Logger;
import kz.edu.nurym.models.Authority;
import kz.edu.nurym.models.Role;
import kz.edu.nurym.models.User;
import kz.edu.nurym.repository.interfaces.IAuthorityRepo;
import kz.edu.nurym.repository.interfaces.IRoleRepo;
import kz.edu.nurym.repository.interfaces.IUserRepo;
import kz.edu.nurym.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("userDetailsService")
public class UserService implements IUserService, UserDetailsService {
    private final IUserRepo userRepo;
    private final IRoleRepo roleRepo;
    private final IAuthorityRepo authorityRepo;
    private final PasswordEncoder passwordEncoder;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    public UserService(IUserRepo userRepo, IRoleRepo roleRepo, IAuthorityRepo authorityRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.authorityRepo = authorityRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userUsername) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(userUsername);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<Authority> authorities = user.getRoles().getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }
        GrantedAuthority roleAuthority = new SimpleGrantedAuthority(user.getRoles().getName());
        grantedAuthorities.add(roleAuthority);

        return buildUserForAuthentication(user, grantedAuthorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUserList() {
        return userRepo.findAll();
    }

    @Override
    public User changePassword(String password) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

    @Override
    public boolean insert(User entity) {
        User userFromDB = userRepo.findByUsername(entity.getUsername());
        if (userFromDB != null) {
            return false;
        }
        entity.setRoles(roleRepo.findById(1L).get());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        userRepo.save(entity);
        if (userRepo.findByUsername(entity.getUsername()) == null) {
            executorService.submit(new Logger(IUserRepo.class.getName(), "Insert was successfully. User -> " + entity));
            return true;
        }
        return false;
    }

    @Override
    public boolean update(User entity) {
        userRepo.saveAndFlush(entity);
        executorService.submit(new Logger(IUserRepo.class.getName(), "Update was successfully. User -> " + entity));
        return true;
    }

    @Override
    public boolean remove(User entity) {
        if (userRepo.findById(entity.getId()).isEmpty()) {
            return false;
        }
        userRepo.delete(entity);
        executorService.submit(new Logger(IUserRepo.class.getName(), "Remove was successfully. User -> " + entity));
        return true;
    }

    @Override
    public Optional<User> select(long id) {
        return userRepo.findById(id);
    }
}

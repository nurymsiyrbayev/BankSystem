package kz.edu.nurym.services.interfaces;

import kz.edu.nurym.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    boolean insert(User entity);

    boolean update(User entity);

    boolean remove(User entity);

    Optional<User> select(long id);

    User findByUsername(String username);

    List<User> getUserList();

    User changePassword(String password);
}

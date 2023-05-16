package raf.rs.blog.repo.user;

import raf.rs.blog.entities.User;
import raf.rs.blog.requests.LoginRequest;

public interface UserRepo {
    public User findUser(String username);

    public User createUser(LoginRequest loginRequest);
}

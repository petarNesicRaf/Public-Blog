package raf.rs.blog.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import raf.rs.blog.entities.User;
import raf.rs.blog.repo.user.UserRepo;
import raf.rs.blog.requests.LoginRequest;

import javax.inject.Inject;
import java.util.Date;

public class UserService {
    @Inject
    UserRepo userRepo;

    public String login(String username, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);
        User user = this.userRepo.findUser(username);
        if(user == null || !user.getPassword().equals(hashedPassword))
        {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000);

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(username)
                .sign(algorithm);
    }

    public User createUser(LoginRequest loginRequest)
    {
        return this.userRepo.createUser(loginRequest);
    }
    public boolean isAuthorized(String token)
    {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String username = jwt.getSubject();
        User user = this.userRepo.findUser(username);

        if(user == null)
            return false;

        return true;
    }

}

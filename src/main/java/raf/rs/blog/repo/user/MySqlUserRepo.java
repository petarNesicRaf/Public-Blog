package raf.rs.blog.repo.user;

import org.apache.commons.codec.digest.DigestUtils;
import raf.rs.blog.entities.User;
import raf.rs.blog.repo.MySqlAbstractRepo;
import raf.rs.blog.requests.LoginRequest;

import java.sql.*;
import java.text.SimpleDateFormat;

public class MySqlUserRepo extends MySqlAbstractRepo implements UserRepo {
    @Override
    public User findUser(String username) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;

    }

    @Override
    public User createUser(LoginRequest loginRequest) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                "INSERT INTO user (username, password) VALUES (?,?)",
                generatedColumns
            );
            String hashedPassword = DigestUtils.sha256Hex(loginRequest.getPassword());
            preparedStatement.setString(1, loginRequest.getUsername());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"));
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

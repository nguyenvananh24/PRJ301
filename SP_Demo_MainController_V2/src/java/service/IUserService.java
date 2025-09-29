package service;

import model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    void addUser(User user) throws SQLException;
    User getUser(int id);
    List<User> getAllActiveUsers();
    List<User> searchActiveUsers(String keyword);
    boolean deactivateUser(int id) throws SQLException;
    boolean updateUser(User user) throws SQLException;
}

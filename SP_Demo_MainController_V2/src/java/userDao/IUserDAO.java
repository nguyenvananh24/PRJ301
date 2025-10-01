package userDao;

import model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    void insertUser(User user) throws SQLException;
    User selectUser(int id);
    List<User> selectAllActiveUsers();
    List<User> searchActiveUsers(String keyword);
    boolean deactivateUser(int id) throws SQLException; // update status
    boolean updateUser(User user) throws SQLException;
    User checkLogin(String username, String password);

}

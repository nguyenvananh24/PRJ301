package service;

import model.User;
import userDao.IUserDAO;
import userDao.UserDAO;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements IUserService {
    private IUserDAO userDAO = new UserDAO();

    @Override
    public void addUser(User user) throws SQLException { userDAO.insertUser(user); }

    @Override
    public User getUser(int id) { return userDAO.selectUser(id); }

    @Override
    public List<User> getAllActiveUsers() { return userDAO.selectAllActiveUsers(); }

    @Override
    public List<User> searchActiveUsers(String keyword) { return userDAO.searchActiveUsers(keyword); }

    @Override
    public boolean deactivateUser(int id) throws SQLException { return userDAO.deactivateUser(id); }

    @Override
    public boolean updateUser(User user) throws SQLException { return userDAO.updateUser(user); }
}


package service;
import dao.DBConnection;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDAO {

    private static final String LOGIN = "SELECT id, username, role FROM Users WHERE username=? AND password=?";
    private static final String INSERT_USER = "INSERT INTO Users (username, email, country, role, status, password, dob) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM Users WHERE id=?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Users";
    private static final String DELETE_USER_SQL = "DELETE FROM Users WHERE id=?";
    private static final String UPDATE_USER_SQL = "UPDATE Users SET username=?, email=?, country=?, role=?, status=?, password=?, dob=? WHERE id=?";

    public User checkLogin(String username, String password) {
        User user = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ptm = conn.prepareStatement(LOGIN)) {

            ptm.setString(1, username);
            ptm.setString(2, password);

            try (ResultSet rs = ptm.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String role = rs.getString("role");
                    user = new User(id, username, "", "", role, true, password, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_USER)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getRole());
            ps.setBoolean(5, user.isStatus());
            ps.setString(6, user.getPassword());
            ps.setDate(7, user.getDob());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public User selectUser(int id) {
        User user = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String country = rs.getString("country");
                    String role = rs.getString("role");
                    boolean status = rs.getBoolean("status");
                    String password = rs.getString("password");
                    Date dob = rs.getDate("dob");
                    user = new User(id, username, email, country, role, status, password, dob);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String role = rs.getString("role");
                boolean status = rs.getBoolean("status");
                String password = rs.getString("password");
                Date dob = rs.getDate("dob");
                users.add(new User(id, username, email, country, role, status, password, dob));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_USER_SQL)) {
            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new SQLException(e);
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_USER_SQL)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getRole());
            ps.setBoolean(5, user.isStatus());
            ps.setString(6, user.getPassword());
            ps.setDate(7, user.getDob());
            ps.setInt(8, user.getId());
            rowUpdated = ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new SQLException(e);
        }
        return rowUpdated;
    }

    public static void main(String[] args) {
        UserDao dao = new UserDao();
        List<User> list = dao.selectAllUsers();
        for (User u : list) {
            System.out.println(u);
        }
    }
}

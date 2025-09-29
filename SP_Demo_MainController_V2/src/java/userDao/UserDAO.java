package userDao;

import dao.DBConnection;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private static final String INSERT_SQL =
        "INSERT INTO Users (username,email,country,role,status,password,dob) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_BY_ID = "SELECT * FROM Users WHERE id = ?";
    private static final String SELECT_ACTIVE = "SELECT * FROM Users WHERE status = 1";
    private static final String SEARCH_ACTIVE =
        "SELECT * FROM Users WHERE status=1 AND (username LIKE ? OR email LIKE ? OR country LIKE ?)";
    private static final String UPDATE_SQL =
        "UPDATE Users SET username=?, email=?, country=?, role=?, status=?, password=?, dob=? WHERE id = ?";
    private static final String DEACTIVATE_SQL = "UPDATE Users SET status = 0 WHERE id = ?";

    @Override
    public void insertUser(User user) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getRole());
            ps.setBoolean(5, user.isStatus());
            ps.setString(6, user.getPassword());
            ps.setDate(7, user.getDob());
            ps.executeUpdate();
        }
    }

    @Override
    public User selectUser(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<User> selectAllActiveUsers() {
        List<User> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ACTIVE);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public List<User> searchActiveUsers(String keyword) {
        List<User> list = new ArrayList<>();
        String kw = "%" + keyword + "%";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SEARCH_ACTIVE)) {
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public boolean deactivateUser(int id) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DEACTIVATE_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getRole());
            ps.setBoolean(5, user.isStatus());
            ps.setString(6, user.getPassword());
            ps.setDate(7, user.getDob());
            ps.setInt(8, user.getId());
            return ps.executeUpdate() > 0;
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("country"),
            rs.getString("role"),
            rs.getBoolean("status"),
            rs.getString("password"),
            rs.getDate("dob")
        );
    }
}

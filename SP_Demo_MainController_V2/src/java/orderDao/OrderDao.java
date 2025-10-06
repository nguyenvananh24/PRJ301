package orderDao;

import dao.DBConnection;
import model.Order;
import java.sql.*;
import java.util.*;

public class OrderDao implements IOrderDao {

    @Override
    public int insertOrder(Order order) throws SQLException {
        String sql = "INSERT INTO Orders(user_id, total_price, status) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    @Override
    public void insertOrderDetails(int orderId, Map<Integer, Integer> cartItems) throws SQLException {
        String sql = "INSERT INTO OrderDetails(order_id, product_id, quantity, price) " +
                     "SELECT ?, id, ?, price FROM Product WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (Map.Entry<Integer, Integer> e : cartItems.entrySet()) {
                ps.setInt(1, orderId);
                ps.setInt(2, e.getValue());
                ps.setInt(3, e.getKey());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public List<Order> getOrdersByUser(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(Order.fromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

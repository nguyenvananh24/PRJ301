package orderDao;

import model.Order;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IOrderDao {
    int insertOrder(Order order) throws SQLException;
    void insertOrderDetails(int orderId, Map<Integer, Integer> cartItems) throws SQLException;
    List<Order> getOrdersByUser(int userId);
}

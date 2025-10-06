package service;

import model.Order;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

public interface IOrderService {
    int createOrder(Order order, Map<Integer, Integer> cartItems) throws SQLException;
    List<Order> getOrdersByUser(int userId);
}

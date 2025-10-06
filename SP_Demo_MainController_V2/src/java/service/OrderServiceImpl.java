package service;

import orderDao.IOrderDao;
import orderDao.OrderDao;
import model.Order;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements IOrderService {
    private IOrderDao orderDao = new OrderDao();

    @Override
    public int createOrder(Order order, Map<Integer, Integer> cartItems) throws SQLException {
        int orderId = orderDao.insertOrder(order);
        orderDao.insertOrderDetails(orderId, cartItems);
        return orderId;
    }

    @Override
    public List<Order> getOrdersByUser(int userId) {
        return orderDao.getOrdersByUser(userId);
    }
}

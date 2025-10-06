package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private IOrderService orderService;

    @Override
    public void init() {
        orderService = new OrderServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");

        // ✅ Lấy giỏ hàng an toàn
        Object cartObj = session.getAttribute("cart");
        Map<Integer, CartItem> cart = null;

        if (cartObj instanceof Map<?, ?>) {
            try {
                cart = (Map<Integer, CartItem>) cartObj;
            } catch (ClassCastException e) {
                cart = new HashMap<>();
            }
        }

        // ✅ Kiểm tra đăng nhập
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ✅ Kiểm tra giỏ hàng
        if (cart == null || cart.isEmpty()) {
            request.setAttribute("message", "Giỏ hàng trống, không thể thanh toán!");
            request.getRequestDispatcher("/product/productList.jsp").forward(request, response);
            return;
        }

        // ✅ Tính tổng tiền
        double total = 0;
        for (CartItem item : cart.values()) {
            total += item.getTotalPrice();
        }

        // ✅ Áp dụng giảm giá 10%
        total = total * 0.9;

        // ✅ Tạo order
        Order order = new Order();
        order.setUserId(u.getId());
        order.setTotalPrice(total);
        order.setStatus("Completed");

        // ✅ Tạo danh sách chi tiết sản phẩm trong giỏ
        Map<Integer, Integer> cartItems = new HashMap<>();
        for (CartItem c : cart.values()) {
            cartItems.put(c.getProduct().getId(), c.getQuantity());
        }

        // ✅ Lưu đơn hàng
        try {
            orderService.createOrder(order, cartItems);
            session.removeAttribute("cart"); // Xóa giỏ hàng sau khi checkout xong
            request.getRequestDispatcher("/checkout/success.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Đặt hàng thất bại, vui lòng thử lại!");
            request.getRequestDispatcher("/checkout/error.jsp").forward(request, response);
        }
    }
}
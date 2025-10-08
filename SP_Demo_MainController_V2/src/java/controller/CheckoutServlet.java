package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import service.*;
import util.EmailUtil;

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

        Object cartObj = session.getAttribute("cart");
        Map<Integer, CartItem> cart = null;

        if (cartObj instanceof Map<?, ?>) {
            try {
                cart = (Map<Integer, CartItem>) cartObj;
            } catch (ClassCastException e) {
                cart = new HashMap<>();
            }
        }

        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (cart == null || cart.isEmpty()) {
            request.setAttribute("message", "Giỏ hàng trống, không thể thanh toán!");
            request.getRequestDispatcher("/product/productList.jsp").forward(request, response);
            return;
        }

        double total = 0;
        for (CartItem item : cart.values()) {
            total += item.getTotalPrice();
        }

        String promoCode = "SALE10";
        double discount = total * 0.1;
        total -= discount;

        Order order = new Order();
        order.setUserId(u.getId());
        order.setTotalPrice(total);
        order.setStatus("Completed");

        try {
//            order.setPromoCode(promoCode);
        } catch (Exception ignored) {}

        Map<Integer, Integer> cartItems = new HashMap<>();
        for (CartItem c : cart.values()) {
            cartItems.put(c.getProduct().getId(), c.getQuantity());
        }

        try {
            orderService.createOrder(order, cartItems);
            session.removeAttribute("cart"); // Xóa giỏ hàng sau khi thanh toán

            String subject = "Xác nhận đơn hàng #" + order.getId();
            String message = "Xin chào " + u.getUsername()+ ",\n\n"
                    + "Cảm ơn bạn đã đặt hàng tại cửa hàng của chúng tôi.\n"
                    + "Tổng tiền sau giảm giá (" + promoCode + "): " + total + " VND.\n\n"
                    + "Đơn hàng của bạn đang được xử lý.\n\nTrân trọng!";
            EmailUtil.sendMail(u.getEmail(), subject, message);

            request.setAttribute("order", order);
            request.getRequestDispatcher("/checkout/success.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Đặt hàng thất bại, vui lòng thử lại!");
            request.getRequestDispatcher("/checkout/error.jsp").forward(request, response);
        }
    }
}

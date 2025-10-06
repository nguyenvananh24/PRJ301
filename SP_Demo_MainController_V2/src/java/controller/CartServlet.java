package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.CartItem;
import model.Product;
import productDao.ProductDao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private ProductDao productDAO;

    @Override
    public void init() {
        productDAO = new ProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "view";

        HttpSession session = request.getSession();
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new HashMap<>();

        switch (action) {
            case "clear":
                cart.clear();
                session.setAttribute("cart", cart);
                break;
            default:
                break;
        }

        session.setAttribute("cart", cart);
        request.getRequestDispatcher("/cart/cartView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "add";

        HttpSession session = request.getSession();
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new HashMap<>();

        if (action.equals("add")) {
            int id = Integer.parseInt(request.getParameter("id"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Product p = productDAO.selectProduct(id);
            if (p != null) {
                CartItem item = cart.get(id);
                if (item == null) item = new CartItem(p, quantity);
                else item.setQuantity(item.getQuantity() + quantity);
                cart.put(id, item);
            }
        }

        session.setAttribute("cart", cart);
        response.sendRedirect(request.getContextPath() + "/cart?action=view");
    }
}

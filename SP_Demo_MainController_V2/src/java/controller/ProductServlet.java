package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import java.io.IOException;
import java.util.List;
import productDao.ProductDao;
import java.sql.SQLException;
import model.User;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductDao productDAO;

    public void init() {
        productDAO = new ProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedUser = (User) request.getSession().getAttribute("user");
        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deactivateProduct(request, response);
                break;
            default:
                listProducts(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedUser = (User) request.getSession().getAttribute("user");
        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if ("insert".equals(action)) {
            insertProduct(request, response);
        } else if ("update".equals(action)) {
            updateProduct(request, response);
        } else {
            doGet(request, response);
        }
    }

//    private void listProducts(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        List<Product> list = productDAO.selectAllActiveProducts();
//        request.setAttribute("products", list);
//        RequestDispatcher rd = request.getRequestDispatcher("/product/productList.jsp");
//        rd.forward(request, response);
//    }
    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User logged = (User) request.getSession().getAttribute("user");
        if (logged == null) {
            response.sendRedirect(request.getContextPath() + "/users?action=loginForm");
            return;
        }

        List<Product> list = productDAO.selectAllActiveProducts();
        request.setAttribute("products", list);
        RequestDispatcher rd = request.getRequestDispatcher("/product/productList.jsp");
        rd.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/product/createProduct.jsp");
        rd.forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Product p = new Product();
            p.setName(request.getParameter("name"));
            p.setPrice(Double.parseDouble(request.getParameter("price")));
            p.setDescription(request.getParameter("description"));
            p.setStock(Integer.parseInt(request.getParameter("stock")));
            p.setStatus(true);

            productDAO.insertProduct(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/products");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existing = productDAO.selectProduct(id);
        request.setAttribute("product", existing);
        RequestDispatcher rd = request.getRequestDispatcher("/product/editProduct.jsp");
        rd.forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Product p = new Product();
            p.setId(id);
            p.setName(request.getParameter("name"));
            p.setPrice(Double.parseDouble(request.getParameter("price")));
            p.setDescription(request.getParameter("description"));
            p.setStock(Integer.parseInt(request.getParameter("stock")));
            p.setStatus(request.getParameter("status") != null);

            productDAO.updateProduct(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/products");
    }

    private void deactivateProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.deactivateProduct(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/products");
    }
}

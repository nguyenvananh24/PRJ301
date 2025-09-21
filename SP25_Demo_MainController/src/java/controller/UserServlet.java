package controller;

import service.UserDao;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "MainController", urlPatterns = {"/"})

public class UserServlet extends HttpServlet {
    private UserDao userDAO;

    @Override
    public void init() {
        userDAO = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    req.getRequestDispatcher("/user-form.jsp").forward(req, resp);
                    break;
                case "insert":
                    insertUser(req, resp);
                    break;
                case "delete":
                    deleteUser(req, resp);
                    break;
                case "edit":
                    showEditForm(req, resp);
                    break;
                case "update":
                    updateUser(req, resp);
                    break;
                default:
                    listUser(req, resp);
                    resp.sendRedirect("users");

                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<User> listUser = userDAO.selectAllUsers();
        req.setAttribute("listUser", listUser);
        req.getRequestDispatcher("/user-list.jsp").forward(req, resp);
    }

    private void insertUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        String role = req.getParameter("role");
        boolean status = "on".equals(req.getParameter("status")) || "1".equals(req.getParameter("status"));
        String password = req.getParameter("password");
        String dobStr = req.getParameter("dob"); // YYYY-MM-DD
        Date dob = (dobStr == null || dobStr.isEmpty()) ? null : Date.valueOf(dobStr);

        User newUser = new User(0, username, email, country, role, status, password, dob);
        userDAO.insertUser(newUser);
        resp.sendRedirect("users");
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        userDAO.deleteUser(id);
        resp.sendRedirect("users");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        User existingUser = userDAO.selectUser(id);
        req.setAttribute("user", existingUser);
        req.getRequestDispatcher("/user-form.jsp").forward(req, resp);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        String role = req.getParameter("role");
        boolean status = "on".equals(req.getParameter("status")) || "1".equals(req.getParameter("status"));
        String password = req.getParameter("password");
        String dobStr = req.getParameter("dob");
        Date dob = (dobStr == null || dobStr.isEmpty()) ? null : Date.valueOf(dobStr);

        User user = new User(id, username, email, country, role, status, password, dob);
        userDAO.updateUser(user);
        resp.sendRedirect("users");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

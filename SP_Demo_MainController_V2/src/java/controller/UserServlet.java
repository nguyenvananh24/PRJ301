package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.IUserService;
import service.UserServiceImpl;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/", "/users"})
public class UserServlet extends HttpServlet {
    private IUserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new": showNewForm(req, resp); break;
            case "edit": showEditForm(req, resp); break;
            case "delete": deactivateUser(req, resp); break;
            case "search": searchUsers(req, resp); break;
            default: listUsers(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("insert".equals(action)) insertUser(req, resp);
        else if ("update".equals(action)) updateUser(req, resp);
        else doGet(req, resp);
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> list = userService.getAllActiveUsers();
        req.setAttribute("listUser", list);
        RequestDispatcher rd = req.getRequestDispatcher("/user/listUser.jsp");
        rd.forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/user/createUser.jsp");
        rd.forward(req, resp);
    }

    private void insertUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            User u = buildUserFromRequest(req, 0);
            userService.addUser(u);
        } catch (Exception e) { e.printStackTrace(); }
        resp.sendRedirect(req.getContextPath() + "/users");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        User existing = userService.getUser(id);
        req.setAttribute("user", existing);
        RequestDispatcher rd = req.getRequestDispatcher("/user/editUser.jsp");
        rd.forward(req, resp);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            User u = buildUserFromRequest(req, id);
            userService.updateUser(u);
        } catch (Exception e) { e.printStackTrace(); }
        resp.sendRedirect(req.getContextPath() + "/users");
    }

    private void deactivateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            userService.deactivateUser(id);
        } catch (Exception e) { e.printStackTrace(); }
        resp.sendRedirect(req.getContextPath() + "/users");
    }

    private void searchUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<User> list = userService.searchActiveUsers(keyword == null ? "" : keyword);
        req.setAttribute("listUser", list);
        RequestDispatcher rd = req.getRequestDispatcher("/user/listUser.jsp");
        rd.forward(req, resp);
    }

    private User buildUserFromRequest(HttpServletRequest req, int id) {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        String role = req.getParameter("role");
        boolean status = req.getParameter("status") != null;
        String password = req.getParameter("password");
        Date dob = null;
        String bd = req.getParameter("dob");
        if (bd != null && !bd.isEmpty()) dob = Date.valueOf(bd);
        return new User(id, username, email, country, role, status, password, dob);
    }
}

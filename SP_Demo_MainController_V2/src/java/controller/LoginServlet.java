package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import service.IUserService;
import service.UserServiceImpl;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@WebServlet({"/", "/login"})
public class LoginServlet extends HttpServlet {

    private IUserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cookie[] arr = request.getCookies();
        if (arr != null) {
            for (Cookie c : arr) {
                if (c.getName().equals("userC")) {
                    String decodedUser = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
                    request.setAttribute("usernameC", decodedUser);
                }
                if (c.getName().equals("passC")) {
                    String decodedPass = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
                    request.setAttribute("passwordC", decodedPass);
                }
            }
        }

        request.getRequestDispatcher("/user/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        User u = userService.checkLogin(username, password);

        if (u == null) {
            request.setAttribute("error", "Username or password is incorrect");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user", u);

            Cookie ckUser = new Cookie("userC", URLEncoder.encode(username, StandardCharsets.UTF_8));
            Cookie ckPass = new Cookie("passC", URLEncoder.encode(password, StandardCharsets.UTF_8));

            if (remember != null) {
                ckUser.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
                ckPass.setMaxAge(60 * 60 * 24 * 7);
            } else {
                ckUser.setMaxAge(0);
                ckPass.setMaxAge(0);
            }

            response.addCookie(ckUser);
            response.addCookie(ckPass);

            if ("admin".equalsIgnoreCase(u.getRole())) {
                response.sendRedirect(request.getContextPath() + "/users");
            } else {
                response.sendRedirect(request.getContextPath() + "/products");
            }
        }
    }
}

package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employee;
import service.EmployeeService;

import java.io.IOException;
import java.util.List;
import service.EmployeeServiceImpl;

@WebServlet(name = "EmployeeServlet", urlPatterns = "/employees")
public class EmployeeServlet extends HttpServlet {

    private EmployeeService employeeService = new EmployeeServiceImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse respone)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createEmp(request, response);
                break;
            case "edit":
                editEmp(request, response);
                break;
            case "delete":
                deleteEmp(request, response);
                break;
            default:
                listEmp(request, response);
                break;
        }

    }

    private void listEmp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Employee> empList = employeeService.findAll();
        request.setAttribute("employees", empList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/employee/listEmp.jsp");
        dispatcher.forward(request, response);
    }

    private void createEmp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/employee/createEmp.jsp");
        dispatcher.forward(request, response);
    }

    private void editEmp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employee existingEmployee = employeeService.findById(id);
        request.setAttribute("employee", existingEmployee);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/employee/editEmp.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteEmp(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        employeeService.remove(id);
        response.sendRedirect(request.getContextPath() + "/employees");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        // proceessRequest(request, response);
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                createEmployee(request, response);
                break;
            case "edit":
                updateEmployee(request, response);
                break;
        }
    }

    private void createEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        Employee newEmp = new Employee(id, name, email, address);
        employeeService.save(newEmp);
        response.sendRedirect(request.getContextPath() + "/employees");
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        Employee updatedEmp = new Employee(id, name, email, address);
        employeeService.update(id, updatedEmp);
        response.sendRedirect(request.getContextPath() + "/employees");
    }
}

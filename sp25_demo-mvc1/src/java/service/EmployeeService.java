package service;

import model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    void save(Employee customer);
    Employee findById(int id);
    void update(int id, Employee customer);
    void remove(int id);
}

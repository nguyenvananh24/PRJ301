
package service;

import java.util.List;
import model.Employee;

public interface EmployeeService {
    List<Employee> findAll();
    void save(Employee customer);
    Employee findById(int id);
    void update(int id, Employee customer);
    void remove(int id);
}
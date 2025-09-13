
package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Employee;

public class EmployeeServiceImpl implements EmployeeService {

    private static Map<Integer, Employee> employees = new HashMap<>();

    static {
        employees.put(1, new Employee(1, "John Doe", "john@example.com", "New York"));
        employees.put(2, new Employee(2, "Jane Doe", "jane@example.com", "California"));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public void save(Employee emp) {
        employees.put(emp.getId(), emp);
    }

    @Override
    public Employee findById(int id) {
        return employees.get(id);
    }

    @Override
    public void update(int id, Employee emp) {
        employees.put(id, emp);
    }

    @Override
    public void remove(int id) {
        employees.remove(id);
    }

    public static void main(String[] args) {
        for (Integer key : employees.keySet()) {
            System.out.println(key + " " + employees.get(key));
        }
    }
}
package ua.com.kl.cmathtutor.repository;

import java.util.List;
import java.util.Optional;

import ua.com.kl.cmathtutor.domain.entity.Employee;

public interface EmployeeRepository {

    List<Employee> findAll();

    Optional<Employee> findById(Integer id);

    Employee save(Employee employee);
}

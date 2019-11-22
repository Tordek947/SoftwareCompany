package ua.com.kl.cmathtutor.repository;

import java.util.List;
import java.util.Optional;

import ua.com.kl.cmathtutor.domain.entity.Department;

public interface DepartmentRepository {

    List<Department> findAll();

    Optional<Department> findById(Integer id);

    Department save(Department department);
}

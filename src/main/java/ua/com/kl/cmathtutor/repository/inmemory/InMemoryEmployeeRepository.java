package ua.com.kl.cmathtutor.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.com.kl.cmathtutor.domain.entity.Employee;
import ua.com.kl.cmathtutor.repository.EmployeeRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InMemoryEmployeeRepository extends AbstractInMemoryRepository<Employee> implements EmployeeRepository {

    private static InMemoryEmployeeRepository instance;

    public static InMemoryEmployeeRepository getInstance() {
	if (instance == null) {
	    instance = new InMemoryEmployeeRepository();
	}
	return instance;
    }

    private Map<Integer, Employee> employeesById = new ConcurrentHashMap<>();

    @Override
    public List<Employee> findAll() {
	return employeesById.values().stream().map(this::deepCopy).collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> findById(Integer id) {
	return Optional.ofNullable(employeesById.get(id));
    }

    @Override
    public Employee save(Employee employee) {
	if (false == employeesById.containsKey(employee.getId())) {
	    employee.setId(selectId());
	}
	employeesById.put(employee.getId(), deepCopy(employee));
	return employee;
    }
}

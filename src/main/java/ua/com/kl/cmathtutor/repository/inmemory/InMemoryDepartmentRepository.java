package ua.com.kl.cmathtutor.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.com.kl.cmathtutor.domain.entity.Department;
import ua.com.kl.cmathtutor.repository.DepartmentRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InMemoryDepartmentRepository extends AbstractInMemoryRepository<Department>
	implements DepartmentRepository {

    private static InMemoryDepartmentRepository instance;

    public static InMemoryDepartmentRepository getInstance() {
	if (instance == null) {
	    instance = new InMemoryDepartmentRepository();
	}
	return instance;
    }

    private Map<Integer, Department> departmentsById = new ConcurrentHashMap<>();

    @Override
    public List<Department> findAll() {
	return departmentsById.values().stream().map(this::deepCopy).collect(Collectors.toList());
    }

    @Override
    public Optional<Department> findById(Integer id) {
	return Optional.ofNullable(departmentsById.get(id));
    }

    @Override
    public Department save(Department department) {
	if (false == departmentsById.containsKey(department.getId())) {
	    department.setId(selectId());
	}
	departmentsById.put(department.getId(), deepCopy(department));
	return department;
    }

}

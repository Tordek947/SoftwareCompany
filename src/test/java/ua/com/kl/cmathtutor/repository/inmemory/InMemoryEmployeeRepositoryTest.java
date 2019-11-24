package ua.com.kl.cmathtutor.repository.inmemory;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.ReflectionUtils;

import ua.com.kl.cmathtutor.domain.entity.Employee;
import ua.com.kl.cmathtutor.repository.inmemory.InMemoryEmployeeRepository;

class InMemoryEmployeeRepositoryTest extends AbstractCrudInMemoryRepositoryTest<Employee> {

    @Override
    protected InMemoryEmployeeRepository getRepositoryForTesting() {
	return ReflectionUtils.newInstance(InMemoryEmployeeRepository.class);
    }

    @Override
    protected Employee getDummyEntity() {
	return new Employee();
    }

    @Override
    protected void modifyNotIdFields(Employee savedEntity) {
	savedEntity.setName("Jackson");
    }

    @Override
    @MethodSource("employeesToSave")
    void whenSeveralEntitiesAreCreated_Then_findAll_ShouldReturnAllSavedEntities(Stream<Employee> departments) {
	super.whenSeveralEntitiesAreCreated_Then_findAll_ShouldReturnAllSavedEntities(departments);
    }

    static Stream<Arguments> employeesToSave() {
	return Stream.of(arguments(Stream.of(new Employee())),
		arguments(Stream.of(new Employee(), new Employee(), new Employee())),
		arguments(Stream.of(Employee.builder().name("Jane").build(),
			Employee.builder().name("Greg").id(532).build())),
		arguments(Stream.of(new Employee(), new Employee(),
			Employee.builder().departmentId(-5342).name("Meriaddoc").build(),
			Employee.builder().name("Jack").departmentId(Integer.MAX_VALUE).build(),
			Employee.builder().name("Jack").departmentId(Integer.MAX_VALUE).build(),
			Employee.builder().id(Integer.MIN_VALUE).build())));
    }

}

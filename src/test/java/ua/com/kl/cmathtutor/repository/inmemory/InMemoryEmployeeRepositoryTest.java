package ua.com.kl.cmathtutor.repository.inmemory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.IsNot.not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.ReflectionUtils;

import ua.com.kl.cmathtutor.domain.entity.Employee;
import ua.com.kl.cmathtutor.repository.inmemory.InMemoryEmployeeRepository;

class InMemoryEmployeeRepositoryTest {

    InMemoryEmployeeRepository repository;

    @BeforeEach
    void setUp() throws Exception {
	repository = ReflectionUtils.newInstance(InMemoryEmployeeRepository.class);
    }

    @Test
    final void save_ShouldReturnSameEmployee() {
	Employee employee = new Employee();

	final Employee savedEmployee = repository.save(employee);

	assertSame(employee, savedEmployee);
    }

    @Test
    final void save_ShouldCreateNewEmployee() {
	final Employee savedEmployee = repository.save(new Employee());

	assertThat("The first created entity should has id of 1", savedEmployee.getId(), is(equalTo(1)));
    }
    
    @Test
    final void whenEmployeeIsCreated_Then_findById_withThisEmployeeId_ShouldReturnEqualButNotSameEmployee() {
	final Employee savedEmployee = repository.save(new Employee());

	final Optional<Employee> foundEmployee = repository.findById(savedEmployee.getId());

	assertAll(() -> assertTrue(foundEmployee.isPresent()),
		() -> assertThat(foundEmployee.get(), is(equalTo(savedEmployee))),
		() -> assertThat(foundEmployee.get(), not(sameInstance(savedEmployee))));
    }

    @Test
    final void whenEmployeeIsCreated_AndThenIsModified_Then_findById_withThisEmployeeId_ShouldReturnSavedEmployee() {
	final Employee savedEmployee = repository.save(new Employee());
	final int savedEmployeeId = savedEmployee.getId();
	savedEmployee.setName("Tom");
	repository.save(savedEmployee);

	final Optional<Employee> foundEmployee = repository.findById(savedEmployeeId);

	assertThat(foundEmployee.get(), is(equalTo(savedEmployee)));
    }
    @Test
    final void whenEmployeeIsCreated_AndThenIsModified_Then_findById_withThisEmployeeId_ShouldReturnOldEmployee() {
	final Employee savedEmployee = repository.save(new Employee());
	final int savedEmployeeId = savedEmployee.getId();
	savedEmployee.setName("Tom");
	savedEmployee.setId(453);

	final Optional<Employee> foundEmployee = repository.findById(savedEmployeeId);

	assertThat(foundEmployee.get(), not(equalTo(savedEmployee)));
    }

    @Test
    final void whenEmployeeIsCreated_AndThenItsIdModified_Then_findById_withUpdatedEmployeeId_ShouldReturnNewCreatedEmployee() {
	final Employee savedEmployee = repository.save(new Employee());
	savedEmployee.setId(665);
	repository.save(savedEmployee);

	final Optional<Employee> foundEmployee = repository.findById(savedEmployee.getId());

	assertAll(() -> assertNotNull(foundEmployee.get()),
		() -> assertThat(foundEmployee.get(), is(equalTo(savedEmployee))),
		() -> assertThat(foundEmployee.get(), not(sameInstance(savedEmployee))));
    }

    @ParameterizedTest
    @MethodSource("employeesToSave")
    final void whenSeveralEmployeesAreCreated_Then_findAll_ShouldReturnAllSavedEmployees() {
	final Employee savedEmployee = repository.save(new Employee());
	final Employee secondSavedEmployee = repository.save(new Employee());

	final List<Employee> foundEmployees = repository.findAll();

	assertThat(foundEmployees, containsInAnyOrder(savedEmployee, secondSavedEmployee));
    }

    public static Stream<Arguments> employeesToSave() {
	return Stream.of(Arguments.of(new Employee()), Arguments.of(new Employee(), new Employee(), new Employee()),
		Arguments.of(Employee.builder().name("Jane").build(), Employee.builder().name("Greg").id(532).build()),
		Arguments.of(new Employee(), new Employee(),
			Employee.builder().departmentId(-5342).name("Meriaddoc").build(),
			Employee.builder().name("Jack").departmentId(Integer.MAX_VALUE).build(),
			Employee.builder().name("Jack").departmentId(Integer.MAX_VALUE).build(),
			Employee.builder().id(Integer.MIN_VALUE).build()));
    }

    @ParameterizedTest(name = "Id of created entity among {0} created ones with index {1} must be {1}+1")
    @CsvSource({// @formatter:off
	 "2, 1",
	 "1, 0",
	 "49, 25",
	 "13, 3"})// @formatter:on
    final void whenSeveralEntitiesAreCreated_Then_TheyShouldContainsSequentialId(int n, int k) {
	List<Employee> createdEmployees = new LinkedList<>();

	for (int i = 0; i < n; ++i) {
	    createdEmployees.add(repository.save(new Employee()));
	}

	assertThat(createdEmployees.get(k).getId(), is(equalTo(Integer.valueOf(k + 1))));
    }

}

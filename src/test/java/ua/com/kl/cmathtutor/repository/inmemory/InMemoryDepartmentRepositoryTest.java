package ua.com.kl.cmathtutor.repository.inmemory;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.ReflectionUtils;

import ua.com.kl.cmathtutor.domain.entity.Department;

class InMemoryDepartmentRepositoryTest {

    InMemoryDepartmentRepository repository;

    @BeforeEach
    void setUp() throws Exception {
	repository = ReflectionUtils.newInstance(InMemoryDepartmentRepository.class);
    }

    @Test
    final void save_ShouldReturnSameDepartment() {
	Department department = new Department();

	final Department savedDepartment = repository.save(department);

	assertSame(department, savedDepartment);
    }

    @Test
    final void save_ShouldCreateNewDepartment() {
	final Department savedDepartment = repository.save(new Department());

	assertThat("The first created entity should has id of 1", savedDepartment.getId(), is(equalTo(1)));
    }

    @Test
    final void whenDepartmentIsCreated_Then_findById_withThisDepartmentId_ShouldReturnEqualButNotSameDepartment() {
	final Department savedDepartment = repository.save(new Department());

	final Optional<Department> foundDepartment = repository.findById(savedDepartment.getId());

	assertAll(() -> assertTrue(foundDepartment.isPresent()),
		() -> assertThat(foundDepartment.get(), is(equalTo(savedDepartment))),
		() -> assertThat(foundDepartment.get(), not(sameInstance(savedDepartment))));
    }

    @Test
    final void whenDepartmentIsCreated_AndThenIsModified_Then_findById_withThisDepartmentId_ShouldReturnOldDepartment() {
	final Department savedDepartment = repository.save(new Department());
	final int savedDepartmentId = savedDepartment.getId();
	savedDepartment.setName("Tom");
	savedDepartment.setId(453);

	final Optional<Department> foundDepartment = repository.findById(savedDepartmentId);

	assertThat(foundDepartment.get(), not(equalTo(savedDepartment)));
    }

    @Test
    final void whenDepartmentIsCreated_AndThenIsModified_Then_save_withUpdatedDepartment_ShouldReturnUpdatedDepartment() {
	final Department savedDepartment = repository.save(new Department());
	savedDepartment.setName("Tom");

	final Department updatedDepartment = repository.save(savedDepartment);

	assertThat(updatedDepartment, is(equalTo(savedDepartment)));
    }

    @Test
    final void whenDepartmentIsCreated_AndThenItsIdModified_Then_findById_withUpdatedDepartment_ShouldReturnNewCreatedDepartment() {
	final Department savedDepartment = repository.save(new Department());
	savedDepartment.setId(665);
	repository.save(savedDepartment);

	final Optional<Department> foundDepartment = repository.findById(savedDepartment.getId());

	assertAll(() -> assertNotNull(foundDepartment.get()),
		() -> assertThat(foundDepartment.get(), is(equalTo(savedDepartment))),
		() -> assertThat(foundDepartment.get(), not(sameInstance(savedDepartment))));
    }

    @ParameterizedTest
    @MethodSource("departmentsToSave")
    final void whenSeveralDepartmentsAreCreated_Then_findAll_ShouldReturnAllSavedDepartments() {
	final Department savedDepartment = repository.save(new Department());
	final Department secondSavedDepartment = repository.save(new Department());

	final List<Department> foundDepartments = repository.findAll();

	assertThat(foundDepartments, containsInAnyOrder(savedDepartment, secondSavedDepartment));
    }

    public static Stream<Arguments> departmentsToSave() {
	return Stream.of(Arguments.of(new Department()),
		Arguments.of(new Department(), new Department(), new Department()),
		Arguments.of(Department.builder().name("Jane").build(),
			Department.builder().name("Greg").id(532).build()),
		Arguments.of(new Department(), new Department(),
			Department.builder().id(-5342).name("Meriaddoc").build(),
			Department.builder().name("Jack").id(Integer.MAX_VALUE).build(),
			Department.builder().name("Jack").id(Integer.MAX_VALUE).build(),
			Department.builder().id(Integer.MIN_VALUE).build()));
    }

}

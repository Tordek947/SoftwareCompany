package ua.com.kl.cmathtutor.repository.inmemory;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.ReflectionUtils;

import ua.com.kl.cmathtutor.domain.entity.Department;

class InMemoryDepartmentRepositoryTest extends AbstractCrudInMemoryRepositoryTest<Department> {

    @Override
    protected InMemoryDepartmentRepository getRepositoryForTesting() {
	return ReflectionUtils.newInstance(InMemoryDepartmentRepository.class);
    }

    @Override
    protected Department getDummyEntity() {
	return new Department();
    }

    @Override
    protected void modifyNotIdFields(Department savedEntity) {
	savedEntity.setName("The last dep");
    }

    @Override
    @MethodSource("departmentsToSave")
    void whenSeveralEntitiesAreCreated_Then_findAll_ShouldReturnAllSavedEntities(Stream<Department> departments) {
	super.whenSeveralEntitiesAreCreated_Then_findAll_ShouldReturnAllSavedEntities(departments);
    }

    static Stream<Arguments> departmentsToSave() {
	return Stream.of(arguments(Stream.of(new Department())),
		arguments(Stream.of(new Department(), new Department(), new Department())),
		arguments(Stream.of(Department.builder().name("Jane").build(),
			Department.builder().name("Greg").id(532).build())),
		arguments(Stream.of(new Department(), new Department(),
			Department.builder().id(-5342).name("Meriaddoc").build(),
			Department.builder().name("Jack").id(Integer.MAX_VALUE).build(),
			Department.builder().name("Jack").id(Integer.MAX_VALUE).build(),
			Department.builder().id(Integer.MIN_VALUE).build())));
    }
}

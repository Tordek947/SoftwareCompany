package ua.com.kl.cmathtutor.repository.inmemory;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.ReflectionUtils;

import ua.com.kl.cmathtutor.domain.entity.Bug;

class InMemoryBugRepositoryTest extends AbstractCrudInMemoryRepositoryTest<Bug> {

    @Test
    final void getInstance_ShouldReturnTheSameInstance() {
	final InMemoryBugRepository firstInstance = InMemoryBugRepository.getInstance();

	assertThat(InMemoryBugRepository.getInstance(), is(sameInstance(firstInstance)));
    }

    @Override
    protected InMemoryBugRepository getRepositoryForTesting() {
	return ReflectionUtils.newInstance(InMemoryBugRepository.class);
    }

    @Override
    protected Bug getDummyEntity() {
	return new Bug();
    }

    @Override
    protected void modifyNotIdFields(Bug savedEntity) {
	savedEntity.setDescription("Some another very big description");
    }

    @Override
    @MethodSource("bugsToSave")
    void whenSeveralEntitiesAreCreated_Then_findAll_ShouldReturnAllSavedEntities(Stream<Bug> entities) {
	super.whenSeveralEntitiesAreCreated_Then_findAll_ShouldReturnAllSavedEntities(entities);
    }

    static Stream<Arguments> bugsToSave() {
	return Stream.of(arguments(Stream.of(new Bug())), // formatter:off
		arguments(Stream.of(new Bug(), new Bug(), new Bug())),
		arguments(Stream.of(Bug.builder().description("Some description").build(),
			Bug.builder().description("Descr").id(532).build())),
		arguments(Stream.of(new Bug(), new Bug(), Bug.builder().id(-5342).description("Very old bug").build(),
			Bug.builder().description("Super urgent one").id(Integer.MAX_VALUE).build(),
			Bug.builder().description("Just empty description").id(Integer.MAX_VALUE).build(),
			Bug.builder().id(Integer.MIN_VALUE).build())));// formatter:on
    }

}

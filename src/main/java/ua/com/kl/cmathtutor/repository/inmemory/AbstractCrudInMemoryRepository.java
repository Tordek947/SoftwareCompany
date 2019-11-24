package ua.com.kl.cmathtutor.repository.inmemory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import ua.com.kl.cmathtutor.domain.entity.IdContainer;

public abstract class AbstractCrudInMemoryRepository<T extends Serializable & IdContainer>
	extends AbstractInMemoryRepository<T> {

    private Map<Integer, T> entitiesById = new ConcurrentHashMap<>();

    public List<T> findAll() {
	return entitiesById.values().stream().map(this::deepCopy).collect(Collectors.toList());
    }

    public Optional<T> findById(Integer id) {
	return Optional.ofNullable(entitiesById.get(id));
    }

    public T save(T entity) {
	if (false == entitiesById.containsKey(entity.getId())) {
	    entity.setId(selectId());
	}
	entitiesById.put(entity.getId(), deepCopy(entity));
	return entity;
    }

    public boolean deleteById(Integer id) {
	return entitiesById.remove(id) == null ? false : true;
    }

    public boolean delete(T entity) {
	return deleteById(entity.getId());
    }
}

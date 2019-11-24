package ua.com.kl.cmathtutor.repository.inmemory;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.SerializationUtils;

public abstract class AbstractInMemoryRepository<T extends Serializable> {

    private AtomicInteger idCounter = new AtomicInteger(1);

    protected Integer selectId() {
	return Integer.valueOf(idCounter.getAndIncrement());
    }

    protected T deepCopy(T entity) {
	return SerializationUtils.roundtrip(entity);
    }
}

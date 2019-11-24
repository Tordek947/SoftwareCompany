package ua.com.kl.cmathtutor.repository;

import java.util.List;
import java.util.Optional;

import ua.com.kl.cmathtutor.domain.entity.Bug;

public interface BugRepository {

    List<Bug> findAll();

    Optional<Bug> findById(Integer id);

    Bug save(Bug bug);
}

package ua.com.kl.cmathtutor.domain.entity;

import java.io.Serializable;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@Builder
public class Employee implements Serializable {

    @Builder.Default
    private Integer id = 0;
    private String name;
    @Builder.Default
    private Optional<Integer> departmentId = Optional.empty();
}

package ua.com.kl.cmathtutor.domain.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@Builder
public class Department implements Serializable {

    private Integer id;
    private String name;
}

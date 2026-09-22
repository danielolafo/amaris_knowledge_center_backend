package com.amaris.knowledgecenter.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("employee_technologies")
public class EmployeeTechnology {

    @Id
    private Long id;
    private Long employeeId;
    private Long technologyId;

    public EmployeeTechnology() {
    }

    public EmployeeTechnology(Long id, Long employeeId, Long technologyId) {
        this.id = id;
        this.employeeId = employeeId;
        this.technologyId = technologyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Long technologyId) {
        this.technologyId = technologyId;
    }
}
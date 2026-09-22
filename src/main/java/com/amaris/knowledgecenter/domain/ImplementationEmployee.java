package com.amaris.knowledgecenter.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("implementation_employees")
public class ImplementationEmployee {

    @Id
    private Long id;
    private Long implementationId;
    private Long employeeId;

    public ImplementationEmployee() {
    }

    public ImplementationEmployee(Long id, Long implementationId, Long employeeId) {
        this.id = id;
        this.implementationId = implementationId;
        this.employeeId = employeeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImplementationId() {
        return implementationId;
    }

    public void setImplementationId(Long implementationId) {
        this.implementationId = implementationId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
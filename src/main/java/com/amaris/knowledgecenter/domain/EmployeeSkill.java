package com.amaris.knowledgecenter.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("employee_skills")
public class EmployeeSkill {

    @Id
    private Long id;
    private Long employeeId;
    private String skill;

    public EmployeeSkill() {
    }

    public EmployeeSkill(Long id, Long employeeId, String skill) {
        this.id = id;
        this.employeeId = employeeId;
        this.skill = skill;
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

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
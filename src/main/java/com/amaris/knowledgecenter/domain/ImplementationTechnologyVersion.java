package com.amaris.knowledgecenter.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("implementation_technology_versions")
public class ImplementationTechnologyVersion {

    @Id
    private Long id;
    private Long implementationId;
    private Long technologyId;
    private Long versionId;

    public ImplementationTechnologyVersion() {
    }

    public ImplementationTechnologyVersion(Long id, Long implementationId, Long technologyId, Long versionId) {
        this.id = id;
        this.implementationId = implementationId;
        this.technologyId = technologyId;
        this.versionId = versionId;
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

    public Long getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Long technologyId) {
        this.technologyId = technologyId;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }
}
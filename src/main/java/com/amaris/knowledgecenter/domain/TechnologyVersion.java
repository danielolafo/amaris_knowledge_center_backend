package com.amaris.knowledgecenter.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("technology_versions")
public class TechnologyVersion {

    @Id
    private Long id;
    private Long technologyId;
    private String version;

    public TechnologyVersion() {
    }

    public TechnologyVersion(Long id, Long technologyId, String version) {
        this.id = id;
        this.technologyId = technologyId;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Long technologyId) {
        this.technologyId = technologyId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
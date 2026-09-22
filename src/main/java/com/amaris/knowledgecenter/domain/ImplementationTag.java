package com.amaris.knowledgecenter.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("implementation_tags")
public class ImplementationTag {

    @Id
    private Long id;
    private Long implementationId;
    private String tag;

    public ImplementationTag() {
    }

    public ImplementationTag(Long id, Long implementationId, String tag) {
        this.id = id;
        this.implementationId = implementationId;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
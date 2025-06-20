package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
public class TaskSubmission extends AuditableModel {
    @Getter
    @Embedded
    private Description description;
}
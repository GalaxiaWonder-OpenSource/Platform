package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.Justification;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.entities.AuditableModel;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ChangeProcessId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.MilestoneId;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
public class ChangeOrder extends AuditableModel {

    @Getter
    @Embedded
    public MilestoneId milestoneId;

    @Getter
    @Embedded
    public Justification description;

    @Getter
    @Embedded
    public ChangeProcessId changeProcessId;
}

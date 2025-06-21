package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.Justification;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.entities.AuditableModel;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ChangeProcessId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.OrganizationMemberId;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
public class ChangeResponse extends AuditableModel {

    @Getter
    @Embedded
    public OrganizationMemberId responsedBy;

    @Getter
    @Embedded
    public Justification notes;

    @Getter
    @Embedded
    public ChangeProcessId changeProcessId;
}

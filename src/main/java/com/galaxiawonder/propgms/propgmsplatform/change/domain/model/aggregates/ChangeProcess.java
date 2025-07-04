package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.CreateChangeProcessCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeOrigin;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeProcessStatus;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeOrderId;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeProcessStatuses;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeResponse;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.Justification;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Change Process
 *
 * @summary
 * Aggregate root that represents a change process within the system.
 * A change process has an origin, status, justification, approvedBy, change order and response.
 *
 * @author
 * Galaxia Wonde Development Team
 * @since 1.0
 */
@Entity
public class ChangeProcess extends AuditableAbstractAggregateRoot<ChangeProcess> {

    /**
     * Current origin of the change process, represented as an entity
     */
    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="origin_id", nullable=false, unique = false)
    private ChangeOrigin origin;

    /**
     * Current status of the change process, represented as an entity
     */
    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="status_id", nullable=false, unique = false)
    private ChangeProcessStatus status;

    /**
     * Justification of the change process, encapsulated in a justification object.
     */
    @Getter
    @Embedded
    private Justification justification;

    /**
     * Identifier of the change response for the change process.
     */
    @Getter
    @Embedded
    private ChangeResponse response;

    /**
     * Identifier of the project linked to change process
     */
    @Getter
    @Embedded
    private ProjectId projectId;

    /**
     * Default constructor required by JPA
     */
    public ChangeProcess() {}

    /**
     * Initializes a new instance of the Change process class with the specific command.
     * @param command the command containing the necessary information to create a change process.
     */
    public ChangeProcess(CreateChangeProcessCommand command){
        this.justification = command.justification();
        this.projectId = command.projectId();
    }

    /**
     * Sets the origin and status of the change process
     * @param origin the origin of the change process
     * @param status the status of the change process
     */
    public void SetInformation(ChangeOrigin origin, ChangeProcessStatus status){
        if (origin != null && status != null){
           throw new IllegalArgumentException("Origin and status cannot be null.");
        }
        this.origin = origin;
        this.status = status;
    }

    /**
     * Reassigns the current change process status to {@code APPROVED}
     *
     * @param status the new {@link ChangeProcessStatus} to be assigned
     * @param response the new {@link ChangeResponse} to be assigned
     * @throws IllegalArgumentException if the change process is not pending
     */
    public void respondToChange(ChangeProcessStatus status, ChangeResponse response) {
        if (response.response() != null){
            throw new IllegalArgumentException("Change process response has already been set.");
        }
        this.status = status;
        this.response = response;
    }
}

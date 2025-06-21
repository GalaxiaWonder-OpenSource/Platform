package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates;

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
     * Identifier of the change order for the change process.
     */
    @Getter
    @Embedded
    @AttributeOverride(name = "changeOrderId", column = @Column(name = "change_order_id", nullable = true))
    private ChangeOrderId changeOrderId;

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
     * Construct a project with the required fields.
     *
     * @param origin the origin of the change process
     * @param status the current status of the change process
     * @param justification the justification of the change process
     * @param projectId the ID of the project linked to the change process
     */
    public ChangeProcess(ChangeOrigin origin, ChangeProcessStatus status, Justification justification, ProjectId projectId){
        this.origin = origin;
        this.status = status;
        this.justification = justification;
        this.projectId = projectId;
    }

    /**
     * Verify if the status of the chang process is {@code PENDING}
     * @return true if status is pending, false if not.
     */
    public boolean isPending() {
        return ChangeProcessStatuses.PENDING.equals(this.status.getName());
    }

    /**
     * Reassigns the current change process status to {@code APPROVED}
     *
     * @param status the new {@link ChangeProcessStatus} to be assigned
     * @param response the new {@link ChangeResponse} to be assigned
     * @throws IllegalArgumentException if the change process is not pending
     */
    public void respondToChange(ChangeProcessStatus status, ChangeResponse response) {
        if (!isPending()) {
            throw new IllegalStateException("Only pending changes can be approved or rejected.");
        }
        this.status = status;
        this.response = response;
    }

    /**
     * Reassigns the {@link ChangeOrder} linked to the change process.
     *
     * @param changeOrderId the new {@link ChangeOrderId} to be assigned
     */
    public void setChangeOrder(ChangeOrderId changeOrderId) {
        this.changeOrderId = changeOrderId;
    }

    /**
     * Verify if change process has an order
     * @return false if change process has a null change order id, true if not.
     */
    public boolean hasOrder() {
        return this.changeOrderId != null;
    }

    /**
     * Verify if change process has a response
     * @return false if change process has a null change response id, true if not.
     */
    public boolean hasResponse() {
        return this.response != null;
    }

}

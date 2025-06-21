package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries;

/**
 * Query object used to request all organization invitations
 * associated with a specific person.
 *
 * <p>This query is typically handled by the application service
 * to retrieve all pending or historical invitations for a given
 * person ID, which can be used to render lists or notifications.</p>
 *
 * @param personId the unique identifier of the person whose invitations are being requested
 *
 * @since 1.0
 */
public record GetAllInvitationsByPersonIdQuery(
        Long personId
) {
}

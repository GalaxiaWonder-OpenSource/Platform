package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries;

/**
 * GetPersonIdByEmailQuery
 *
 * @summary
 * Query object used to retrieve the unique identifier of a person
 * based on their email address.
 *
 * <p>This query is typically handled within the IAM context to resolve identity
 * links when only the email is available, such as during invitations or account linking.</p>
 *
 * @param email the email address of the person to be searched
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record GetPersonIdByEmailQuery(
        String email
) {}


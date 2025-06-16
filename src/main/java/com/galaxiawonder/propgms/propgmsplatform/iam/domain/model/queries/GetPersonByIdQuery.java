package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries;

/**
 * GetPersonByIdQuery
 *
 * @summary
 * Query object used to retrieve basic information about a person
 * using their unique identifier within the system.
 *
 * <p>This query is typically handled within the IAM context to resolve
 * person identity details (e.g., projectName, email) based on a given {@code personId}.</p>
 *
 * @param personId the unique identifier of the person to be retrieved
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record GetPersonByIdQuery(Long personId) {
}


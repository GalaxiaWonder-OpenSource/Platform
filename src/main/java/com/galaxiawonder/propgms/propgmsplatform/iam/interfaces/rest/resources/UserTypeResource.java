package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources;

/**
 * UserType Resource
 *
 * @summary
 * A data transfer object (DTO) representing a user type in the system.
 * Used to expose user type information through the REST API.
 *
 * @param id   the unique identifier of the user type
 * @param name the name of the user type (e.g., CONTRACTOR, CLIENT)
 *
 * @since 1.0
 */
public record UserTypeResource(Long id, String name) {
}

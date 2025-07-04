package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import java.util.Date;

/**
 * @param id the unique identifier of the milestone
 * @param name the official name of the milestone to be created or updated
 * @param description a short, meaningful description detailing the purpose or scope of the milestone
 * @param projectId the unique identifier of the project to which this milestone belongs
 * @param startDate the planned starting date for the milestone
 * @param endDate the expected completion or ending date for the milestone
 */

public record MilestoneResource (Long id, String name, String description, Long projectId, Date startDate, Date endDate) {
}

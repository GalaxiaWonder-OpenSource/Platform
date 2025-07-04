package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;
/**
 * Command to delete a milestone
 * @param id the id of a milestone
 * Cannot be null
 */
public record DeleteMilestoneCommand(Long id) {
    /**
     * Constructor
     * @param id of the id of
     * @throws IllegalArgumentException if id is null
     */
    public DeleteMilestoneCommand {
        if(id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }
}

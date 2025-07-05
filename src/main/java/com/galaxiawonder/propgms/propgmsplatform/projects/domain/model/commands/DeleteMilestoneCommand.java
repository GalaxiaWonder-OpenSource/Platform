package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

public record DeleteMilestoneCommand(Long milestoneId) {
    public DeleteMilestoneCommand {
        if(milestoneId == null) throw new IllegalArgumentException("Milestone id cannot be null");
    }
}

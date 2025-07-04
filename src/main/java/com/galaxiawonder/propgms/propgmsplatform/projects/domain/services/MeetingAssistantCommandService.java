package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.MeetingAssistantCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.MeetingAssistant;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public interface MeetingAssistantCommandService {
    /**
     * Handles the command to assign a person to a meeting.
     * @param command the command with meeting ID and person ID
     * @return the created MeetingAssistant entity
     * @throws EntityNotFoundException if the meeting or person does not exist
     * @see MeetingAssistantCommand
     */
    Optional<MeetingAssistant> handle(MeetingAssistantCommand command);
}

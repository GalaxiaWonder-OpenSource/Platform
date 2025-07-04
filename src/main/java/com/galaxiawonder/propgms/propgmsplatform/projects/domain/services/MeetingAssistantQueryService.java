package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.MeetingAssistant;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllMeetingAssistantsByMeetingIdQuery;

import java.util.List;

public interface MeetingAssistantQueryService {

    /**
     * Retrieves all meeting assistants for a specific meeting.
     *
     * @param query the query containing the meeting ID
     * @return List of MeetingAssistant entities associated with the meeting
     * @throws IllegalArgumentException if the meeting doesn't exist
     */
    List<MeetingAssistant> handle(GetAllMeetingAssistantsByMeetingIdQuery query);
}

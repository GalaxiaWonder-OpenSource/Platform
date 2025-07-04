package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Meeting;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.MeetingAssistant;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllMeetingAssistantsByMeetingIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.MeetingAssistantQueryService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.MeetingAssistantRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.MeetingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MeetingAssistantQueryService Implementation
 *
 * @summary
 * Implementation of the MeetingAssistantQueryService interface.
 * It is responsible for handling meeting assistant queries.
 */
@Service
public class MeetingAssistantQueryServiceImpl implements MeetingAssistantQueryService {

    /** Repository interface for performing CRUD operations on {@link MeetingAssistant} entities. */
    private final MeetingAssistantRepository meetingAssistantRepository;

    /** Repository interface for performing CRUD operations on {@link Meeting} entities. */
    private final MeetingRepository meetingRepository;

    /**
     * Constructs the service implementation with required dependencies.
     *
     * @param meetingAssistantRepository repository for retrieving meeting assistants
     * @param meetingRepository repository for validating meeting existence
     */
    public MeetingAssistantQueryServiceImpl(MeetingAssistantRepository meetingAssistantRepository,
                                            MeetingRepository meetingRepository) {
        this.meetingAssistantRepository = meetingAssistantRepository;
        this.meetingRepository = meetingRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeetingAssistant> handle(GetAllMeetingAssistantsByMeetingIdQuery query) {
        // Primero validamos que la reunión existe
        if (!meetingRepository.existsById(query.meetingId())) {
            throw new IllegalArgumentException(
                    "No meeting found with ID: " + query.meetingId());
        }

        // Luego obtenemos los asistentes de la reunión
        List<MeetingAssistant> assistants = meetingAssistantRepository.findAllByMeetingId(query.meetingId());

        // Verificamos si encontramos asistentes
        if (assistants.isEmpty()) {
            throw new IllegalArgumentException(
                    "No meeting assistants found for meeting with ID: " + query.meetingId());
        }

        return assistants;
    }

}
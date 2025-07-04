package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Meeting;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.MeetingAssistantCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.MeetingAssistant;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.events.ProjectCreatedEvent;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.MeetingAssistantCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.MeetingAssistantRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.MeetingRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

public class MeetingAssistantCommandServiceImpl implements MeetingAssistantCommandService {
    /** Repository interface for performing CRUD operations on {@link MeetingAssistant} entities. */
    private final MeetingAssistantRepository meetingAssistantRepository;

    /** Repository interface for performing CRUD operations on {@link Meeting} entities. */
    private final MeetingRepository meetingRepository;

    /** Facade for accessing IAM-related information such as user profiles and identities. */
    private final IAMContextFacade iamContextFacade;

    /**
     * Constructs the service implementation with required dependencies.
     *
     * @param meetingAssistantRepository repository for persisting and retrieving meeting assistants
     * @param meetingRepository repository for retrieving meetings
     * @param iamContextFacade facade for accessing identity and profile data
     */
    public MeetingAssistantCommandServiceImpl(MeetingAssistantRepository meetingAssistantRepository,
                                              MeetingRepository meetingRepository,
                                              IAMContextFacade iamContextFacade) {
        this.meetingAssistantRepository = meetingAssistantRepository;
        this.meetingRepository = meetingRepository;
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * Creates a new MeetingAssistant after validating person and meeting existence.
     *
     * @param command the CreateMeetingAssistantCommand containing personId and meetingId
     * @return Optional containing the created MeetingAssistant if successful
     * @throws IllegalArgumentException if command is null, person doesn't exist, or meeting doesn't exist
     */
    @Override
    public Optional<MeetingAssistant> handle(MeetingAssistantCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("CreateMeetingAssistantCommand must not be null");
        }

        ProfileDetails profileDetails = iamContextFacade.getProfileDetailsById(command.personId());
        if (profileDetails == null) {
            throw new IllegalArgumentException("Person not found with ID: " + command.personId());
        }

        Meeting meeting = meetingRepository.findById(command.meetingId())
                .orElseThrow(() -> new IllegalArgumentException("Meeting not found with ID: " + command.meetingId()));

        var meetingAssistant = new MeetingAssistant();
        var createdMeetingAssistant = meetingAssistantRepository.save(meetingAssistant);

        return Optional.of(createdMeetingAssistant);
    }
}

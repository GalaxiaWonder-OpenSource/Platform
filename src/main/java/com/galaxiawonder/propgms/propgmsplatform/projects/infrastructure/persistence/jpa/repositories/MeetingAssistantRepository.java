package com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.MeetingAssistant;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * MeetingAssistantRepository
 *
 * @summary
 * Repository interface for managing {@link MeetingAssistant} entities.
 * Provides standard CRUD operations and query capabilities via Spring Data JPA.
 *
 * This repository serves as the primary interface for accessing meeting assistant data
 * from the underlying relational database.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface MeetingAssistantRepository extends JpaRepository<MeetingAssistant, Long> {
    List<MeetingAssistant> findAllByMeetingId(Long meetingId);

}

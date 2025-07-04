package com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
 //findbyId
}


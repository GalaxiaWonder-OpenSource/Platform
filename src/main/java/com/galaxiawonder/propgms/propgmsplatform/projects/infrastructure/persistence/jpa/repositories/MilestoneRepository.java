package com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

}

package com.galaxiawonder.propgms.propgmsplatform.change.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeOrigin;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeProcessStatus;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeOrigins;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeProcessStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Change Origin Repository
 *
 * @summary
 * JPA Repository for managing {@link ChangeOrigin} entities.
 * Provides methods to query Change Origins
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface ChangeOriginRepository extends JpaRepository<ChangeOrigin, Long> {
    Optional<ChangeOrigin> findByName(ChangeOrigins name);
    boolean existsByName(ChangeOrigins name);
}

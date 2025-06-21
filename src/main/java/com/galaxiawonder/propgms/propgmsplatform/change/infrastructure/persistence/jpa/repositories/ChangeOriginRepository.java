package com.galaxiawonder.propgms.propgmsplatform.change.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeOrigin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}

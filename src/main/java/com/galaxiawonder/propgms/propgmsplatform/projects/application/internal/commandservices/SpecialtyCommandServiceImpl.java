package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedSpecialtyCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.Specialty;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Specialties;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.SpecialtyCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * SpecialtyCommandService Implementation
 *
 * @summary
 * Service implementation responsible for handling specialty command operations,
 * such as seeding default specialties into the database if they do not exist.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class SpecialtyCommandServiceImpl implements SpecialtyCommandService {

    /** Repository for managing {@link Specialty} entities. */
    private final SpecialtyRepository specialtyRepository;

    /**
     * Constructs a new {@code SpecialtyCommandServiceImpl} with the specified repository.
     *
     * @param specialtyRepository the repository used to persist {@link Specialty} entities
     */
    public SpecialtyCommandServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(SeedSpecialtyCommand command) {
        Arrays.stream(Specialties.values()).forEach(specialty -> {
            if (!specialtyRepository.existsByName(specialty)) {
                specialtyRepository.save(new Specialty(specialty));
            }
        });
    }
}

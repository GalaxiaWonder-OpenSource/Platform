package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.Person;
import jakarta.annotation.Nullable;

/**
 * CreatePersonCommand
 *
 * @summary
 * Command object used to request the creation of a new {@link Person}.
 * It encapsulates the personal information required to register a person, including optional fields
 * that may be provided later in the system.
 *
 * @param firstName       the person's first name (required)
 * @param lastName        the person's last name (required)
 * @param email           the person's email (required, must be unique and valid)
 * @param phone           optional phone number in E.164 format (e.g., +51987654321)
 * @param professionalId  optional professional ID in CIP/CAP format (e.g., CIP123456 or CAP654321)
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record CreatePersonCommand(String firstName, String lastName, String email, @Nullable String phone, @Nullable String professionalId) {}

package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Specialties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Specialty
 *
 * @summary
 * Entity that represents a professional specialty within a project.
 * This class is backed by the {@link Specialties} enum and defines
 * a fixed set of technical domains relevant to project tasks and roles.
 *
 * <p>Examples: {@code ARCHITECTURE}, {@code STRUCTURES}, {@code SANITATION}</p>
 *
 * @see Specialties
 * @since 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialty {

    /**
     * Database identifier for the specialty.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Enum description representing the specialty type.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Specialties name;

    /**
     * Constructs a {@code Specialty} with a specified {@link Specialties} description.
     *
     * @param name the predefined specialty
     */
    public Specialty(Specialties name) {
        this.name = name;
    }

    /**
     * Returns the default specialty to be assigned when none is specified.
     *
     * @return a {@code Specialty} instance with {@code ARCHITECTURE} as default
     */
    public static Specialty getDefaultSpecialty() {
        return new Specialty(Specialties.ARCHITECTURE);
    }

    /**
     * Converts a string representation of the specialty projectName to a {@link Specialty} instance.
     *
     * @param name the string description of the enum constant
     * @return a new {@code Specialty} instance with the corresponding enum description
     * @throws IllegalArgumentException if the projectName does not match any enum constant
     */
    public static Specialty toSpecialtyFromName(String name) {
        return new Specialty(Specialties.valueOf(name));
    }

    /**
     * Validates a list of specialities. If null or empty, returns a list with the default specialty.
     *
     * @param specialities the list of specialities to validate
     * @return the original list or a singleton list with the default specialty
     */
    public static List<Specialty> validateSpecialtySet(List<Specialty> specialities) {
        return specialities == null || specialities.isEmpty()
                ? List.of(getDefaultSpecialty())
                : specialities;
    }

    /**
     * Returns the string representation of the enum-based specialty.
     *
     * @return the projectName of the specialty as a string
     */
    public String getStringName() {
        return name.name();
    }
}

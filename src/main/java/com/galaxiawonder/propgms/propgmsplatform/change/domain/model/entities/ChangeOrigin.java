package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeOrigins;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeOrigin {

    /**
     * Database identifier for the change origin.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Enum description representing the change origin.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false, unique = true)
    private ChangeOrigins name;

    /**
     * Constructs a {@code TaskStatus} with the given {@link ChangeOrigins}
     *
     * @param name the enum description representing the change origin
     */
    public ChangeOrigin(ChangeOrigins name) { this.name = name; }

    /**
     * Returns the default task status to be assigned when none is specified.
     *
     * @return a {@code ChangeOrigin} instance with {@code CHANGE_REQUEST} as default
     */
    public static ChangeOrigin getDefaultOrigin() { return new ChangeOrigin(ChangeOrigins.CHANGE_REQUEST);}

    /**
     * Converts a string to a {@link ChangeOrigin} using the corresponding enum description
     *
     * @param name the string representatin of the enum constant
     * @return a new {@code ChangeOrigin} instance with the corresponding enum description
     * @throws IllegalArgumentException if the name does not match any enum constant
     */
    public static ChangeOrigin toChangeOriginFromName(String name) { return new ChangeOrigin(ChangeOrigins.valueOf(name));}

    /**
     * Validates a list of task statuses. If null or empty, returns a list with the default status.
     *
     * @param origins the list of change origins to validate
     * @return the original list or a singleton list with the default origin
     */
    public static List<ChangeOrigin> validateChangeOriginSet(List<ChangeOrigin> origins) {
        return origins == null || origins.isEmpty()
                ? List.of(getDefaultOrigin())
                : origins;
    }

    /**
     * Returns the string representation of the enum-based change origin.
     *
     * @return the name of the change origin as a string
     */
    public String getStringName() { return name.name(); }


}

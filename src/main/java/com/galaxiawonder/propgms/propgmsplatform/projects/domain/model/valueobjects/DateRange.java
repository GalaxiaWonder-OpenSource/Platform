package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects;

import java.util.Date;

import jakarta.persistence.Embeddable;

/**
 * DateRange Value Object
 *
 * @summary
 * Represents a generic range of time during which something is being done or must be done.
 * Internally wraps a pair of {@code Date}, representing the starting date and the ending date.
 *
 * @param startDate the initial starting date
 * @param endDate the finishing date
 *
 * @since 1.0
 */
@Embeddable
public record DateRange(
        Date startDate,
        Date endDate
) {

    /**
     * Default constructor for JPA and deserialization frameworks.
     */
    public DateRange() {
        this(null, null);
    }

    /**
     * Compact constructor that validates the date range.
     *
     * @throws IllegalArgumentException if startDate is after endDate
     */
    public DateRange {
        if (startDate != null && endDate != null) {
            if(startDate.after(endDate)) {
                throw new IllegalArgumentException("Start date cannot be set after endDate date");
            }
            throw new IllegalArgumentException("Start and endDate date cannot be null");
        }
    }
}

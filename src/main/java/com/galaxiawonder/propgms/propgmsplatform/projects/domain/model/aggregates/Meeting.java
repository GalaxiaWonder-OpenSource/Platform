package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.MilestoneItem;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.MilestoneId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collections;
import java.util.List;

@Getter
@Entity
@Table(name = "meetings")
@EntityListeners(AuditingEntityListener.class)
public class Meeting extends MilestoneItem{

    /** Range of dates within the project will be done. */
    @Column(nullable = false)
    @Embedded
    private DateRange dateRange;

    public Meeting() {}

    public Meeting(DateRange dateRange) {
        this.dateRange = dateRange;
    }
}

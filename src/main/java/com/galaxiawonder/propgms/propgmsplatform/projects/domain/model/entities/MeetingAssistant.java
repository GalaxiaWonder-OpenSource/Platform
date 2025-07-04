package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Meeting;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "meeting_assistants")
public class MeetingAssistant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Embedded
    private PersonId personId;

    public MeetingAssistant() {}

    public MeetingAssistant(Meeting meeting, PersonId personId) {
        this.meeting = meeting;
        this.personId = personId;
    }
}


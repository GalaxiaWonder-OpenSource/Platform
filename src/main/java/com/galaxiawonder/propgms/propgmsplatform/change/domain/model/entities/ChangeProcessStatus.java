package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeProcessStatuses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeProcessStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false, unique = true)
    private ChangeProcessStatuses name;

    public ChangeProcessStatus(ChangeProcessStatuses name) { this.name = name; }

    public static ChangeProcessStatus getDefaultStatus() { return new ChangeProcessStatus(ChangeProcessStatuses.PENDING); }

    public static ChangeProcessStatus toChangeProcessStatusFromName(String name) { return new ChangeProcessStatus(ChangeProcessStatuses.valueOf(name));}

    public static List<ChangeProcessStatus> validateChangeProcessStatusSet(List<ChangeProcessStatus> statuses) {
        return statuses == null || statuses.isEmpty()
                ? List.of(getDefaultStatus())
                : statuses;
    }

    public String getStringName() { return name.name(); }
}

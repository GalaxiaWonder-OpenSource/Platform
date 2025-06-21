package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeProcessStatuses;
import jakarta.annotation.Nullable;

public record CreateChangeProcessCommand(Long origin, @Nullable ChangeProcessStatuses status, String justification, Long projectId)
{
    public CreateChangeProcessCommand {
        if (origin == null || origin < 1) {
            throw new IllegalArgumentException("origin must be greater than 0");
        }
        if (justification == null || justification.isBlank()) {
            throw new IllegalArgumentException("justification must be non-blank");
        }
        if (projectId == null || projectId < 1) {
            throw new IllegalArgumentException("projectId must be greater than 0");
        }
    }
}

package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands;
import jakarta.annotation.Nullable;

public record CreateChangeProcessCommand(String origin, @Nullable String status, String justification, Long projectId)
{
    public CreateChangeProcessCommand {
        if (origin == null || origin.isEmpty()) {
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

package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.Justification;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import jakarta.annotation.Nullable;

public record CreateChangeProcessCommand(Justification justification, ProjectId projectId)
{
    public CreateChangeProcessCommand {
        if (justification == null || justification.justification().isBlank()) {
            throw new IllegalArgumentException("justification must be non-blank");
        }
        if (projectId == null || projectId.projectId() < 1) {
            throw new IllegalArgumentException("projectId must be greater than 0");
        }
    }
}

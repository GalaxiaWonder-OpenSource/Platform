package com.galaxiawonder.propgms.propgmsplatform.change.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.SeedChangeOriginCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeOrigin;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeOrigins;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.services.ChangeOriginCommandService;
import com.galaxiawonder.propgms.propgmsplatform.change.infrastructure.persistence.jpa.repositories.ChangeOriginRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ChangeOriginCommandServiceImpl implements ChangeOriginCommandService {
    private final ChangeOriginRepository changeOriginRepository;

    public ChangeOriginCommandServiceImpl(ChangeOriginRepository changeOriginRepository) {
        this.changeOriginRepository = changeOriginRepository;
    }

    @Override
    public void handle (SeedChangeOriginCommand command) {
        Arrays.stream(ChangeOrigins.values()).forEach(origin -> {
            if (!changeOriginRepository.existsByName(origin)) {
                changeOriginRepository.save(new ChangeOrigin(origin));
            }
        });
    }
}

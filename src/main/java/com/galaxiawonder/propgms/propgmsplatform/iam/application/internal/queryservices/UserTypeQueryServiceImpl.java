package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetAllUserTypesQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetUserTypeByNameQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.UserTypeQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTypeQueryServiceImpl implements UserTypeQueryService {
    private final UserTypeRepository userTypeRepository;

    public UserTypeQueryServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public List<UserType> handle(GetAllUserTypesQuery query) {
        return userTypeRepository.findAll();
    }

    @Override
    public Optional<UserType> handle(GetUserTypeByNameQuery query) {
        return userTypeRepository.findByName(query.userType());
    }
}

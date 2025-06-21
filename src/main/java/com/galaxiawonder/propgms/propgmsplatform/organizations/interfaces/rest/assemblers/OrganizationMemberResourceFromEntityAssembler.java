package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.OrganizationMemberResource;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class OrganizationMemberResourceFromEntityAssembler {

    /**
     * Converts a pair of {@link OrganizationMember} and {@link ProfileDetails} into
     * an {@link OrganizationMemberResource}.
     *
     * @param entity the pair consisting of a member and their associated profile details
     * @return a resource DTO representing the organization member
     */
    public static OrganizationMemberResource toResourceFromEntity(
            OrganizationMember entity
    ) {
        return new OrganizationMemberResource(
                entity.getId(),
                entity.getName().getFullName(),
                entity.getMemberType().getStringName(),
                entity.getCreatedAt()
        );
    }
}

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
     * @param pair the pair consisting of a member and their associated profile details
     * @return a resource DTO representing the organization member
     */
    public static OrganizationMemberResource toResourceFromPair(
            ImmutablePair<OrganizationMember, ProfileDetails> pair
    ) {
        OrganizationMember member = pair.getLeft();
        ProfileDetails profile = pair.getRight();

        return new OrganizationMemberResource(
                member.getId(),
                profile.firstName() + " " + profile.lastName(),
                member.getMemberType().getStringName(),
                member.getCreatedAt()
        );
    }
}

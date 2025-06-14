package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.OrganizationInvitationResource;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import org.apache.commons.lang3.tuple.Triple;

/**
 * Assembler class for converting the latest {@link OrganizationInvitation} from an
 * {@link Organization} entity into a {@link OrganizationInvitationResource}.
 */
public class OrganizationInvitationResourceFromEntityAssembler {

    /**
     * Converts a {@link Triple} of organization, invitation, and inviter profile into a {@link OrganizationInvitationResource}.
     *
     * @param triple the triple containing organization, invitation, and inviter profile details
     * @return a mapped {@link OrganizationInvitationResource}, or {@code null} if the invitation is {@code null}
     * @since 1.0
     */
    public static OrganizationInvitationResource toResourceFromEntity(
            Triple<Organization, OrganizationInvitation, ProfileDetails> triple) {

        Organization organization = triple.getLeft();
        OrganizationInvitation invitation = triple.getMiddle();
        ProfileDetails profileDetails = triple.getRight();

        if (invitation == null) return null;

        return new OrganizationInvitationResource(
                invitation.getId(),
                organization.getCommercialName().commercialName(),
                profileDetails.firstName() + " " + profileDetails.lastName(),
                invitation.getStatus().getStringName(),
                invitation.getCreatedAt()
        );
    }
}


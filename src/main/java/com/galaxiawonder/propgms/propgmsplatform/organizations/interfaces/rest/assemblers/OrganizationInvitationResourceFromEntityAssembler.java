package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.OrganizationInvitationResource;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;

/**
 * Assembler class for converting the latest {@link OrganizationInvitation} from an
 * {@link Organization} entity into a {@link OrganizationInvitationResource}.
 */
public class OrganizationInvitationResourceFromEntityAssembler {

    /**
     * Converts the last added {@link OrganizationInvitation} from the given {@link Organization}
     * into an {@link OrganizationInvitationResource}, using the provided {@link ProfileDetails}
     * to resolve who created the invitation.
     *
     * <p>This method is typically used after successfully adding a new invitation to an organization,
     * and is useful for returning a detailed response including organization name, inviter identity,
     * status, and timestamp.</p>
     *
     * @param organization the organization entity containing the latest invitation
     * @param profileDetails the profile details of the user who created the organization (inviter)
     * @return the corresponding invitation resource, or {@code null} if no invitations exist
     *
     * @since 1.0
     */
    public static OrganizationInvitationResource toResourceFromEntity(Organization organization, ProfileDetails profileDetails) {
        OrganizationInvitation invitation = organization.getLastInvitation();
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


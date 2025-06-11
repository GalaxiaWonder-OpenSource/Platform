package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.SignInResponseResource;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.UserAccountResource;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * SignInResponseResourceFromEntityAssembler
 *
 * @summary
 * Utility class that transforms the result of a successful sign-in operation into a {@link SignInResponseResource}.
 * Combines the {@link UserAccount} and generated JWT token into a single response object for the client.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public class SignInResponseResourceFromEntityAssembler {

    /**
     * Converts an {@link ImmutablePair} of {@link UserAccount} and token into a {@link SignInResponseResource}.
     *
     * @param pair a pair containing the authenticated user and their generated token
     * @return a {@code SignInResponseResource} encapsulating user account info and access token
     */
    public static SignInResponseResource toResourceFromEntity(ImmutablePair<UserAccount, String> pair) {
        UserAccount userAccount = pair.getLeft();
        String token = pair.getRight();

        UserAccountResource userResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(userAccount);

        return new SignInResponseResource(userResource, token);
    }
}


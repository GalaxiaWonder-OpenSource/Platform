package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands;

/**
 * Command to sign in a user.
 * <p>
 *     This command is used to sign in a user with the provided username and password.
 * </p>
 */
public record SignInCommand(String username, String password) {
}

package com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.authorization.sfs.model;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of {@link UserDetails} for Spring Security,
 * adapted to the {@link UserAccount} domain model.
 */
@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

    private final String username;

    @JsonIgnore
    private final String password;

    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructor for UserDetailsImpl.
     *
     * @param username    the username as plain String
     * @param password    the hashed password
     * @param authorities the granted authorities
     */
    public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    /**
     * Builds a {@link UserDetailsImpl} instance from a {@link UserAccount} domain object.
     *
     * @param userAccount the user account from the domain model
     * @return a Spring Security compatible {@link UserDetails} object
     */
    public static UserDetailsImpl build(UserAccount userAccount) {
        String roleName = userAccount.getUserType().getName().name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);

        return new UserDetailsImpl(
                userAccount.getUserName().username(),
                userAccount.getHashedPassword().hashedPassword(),
                Collections.singletonList(authority)
        );
    }
}
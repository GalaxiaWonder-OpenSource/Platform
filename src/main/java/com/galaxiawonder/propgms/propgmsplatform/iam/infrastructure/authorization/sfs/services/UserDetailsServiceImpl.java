package com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.authorization.sfs.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.UserName;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for providing the user details to the Spring Security framework.
 * It implements the UserDetailsService interface.
 */
@Service(value = "defaultUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository UserAccountRepository;

    public UserDetailsServiceImpl(UserAccountRepository UserAccountRepository) {
        this.UserAccountRepository = UserAccountRepository;
    }

    /**
     * This method is responsible for loading the user details from the database.
     * @param username The username.
     * @return The UserDetails object.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = UserAccountRepository.findByUserName(new UserName(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return UserDetailsImpl.build(user);
    }
}

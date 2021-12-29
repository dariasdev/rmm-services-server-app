package rmm.services.server.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * The Class RmmUserDetailService simple implemtation of users service to be used by header based authorization
 */
@Service
public class RmmUserDetailService implements UserDetailsService {

  /** The Constant DUMMY_PW. */
  private static final String DUMMY_PW = "n/a";

  /** The security properties. */
  @Autowired
  private RmmSecurityProperties securityProperties;

  /**
   * Load user by username provided in header
   *
   * @param apiKey the api key
   * @return the user details
   * @throws UsernameNotFoundException the username not found exception
   */
  @Override
  public UserDetails loadUserByUsername(String apiKey) throws UsernameNotFoundException {
    // if header not found properties reject
    if (!securityProperties.getApiKeyToRoleMap().containsKey(apiKey)) {
      throw new UsernameNotFoundException("Not a valid API key: " + apiKey);
    }
    // find ROLE by header
    String user = securityProperties.getApiKeyToRoleMap().get(apiKey);
    List<SimpleGrantedAuthority> grantedAutorities = new ArrayList<>();
    // populate grants by ROLE
    if (securityProperties.getUserToPrivilegeMap().containsKey(user)) {
      List<String> roles = securityProperties.getUserToPrivilegeMap().get(user);
      grantedAutorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
    return new User(user, DUMMY_PW, grantedAutorities);
  }

}

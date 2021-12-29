package rmm.services.server.security;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

/**
 * The Class RmmSecurityConfiguration security configuration
 */
@EnableWebSecurity
@Order(RmmSecurityConfiguration.SECURITY_FILTER_ORDER)
public class RmmSecurityConfiguration extends WebSecurityConfigurerAdapter {

  /** The Constant SECURITY_FILTER_ORDER. */
  public static final int SECURITY_FILTER_ORDER = org.springframework.boot.autoconfigure.security.SecurityProperties.BASIC_AUTH_ORDER + 1;
  
  /** The Constant BASE_API_URL. */
  private static final String BASE_API_URL =  "/ninja-rmm-services/api/*/**";

  /** The rmm user detail service. */
  @Autowired
  private RmmUserDetailService rmmUserDetailService;

  /** The security properties. */
  @Autowired
  private RmmSecurityProperties securityProperties;

  /** The preauth filter. */
  private RequestHeaderAuthenticationFilter preauthFilter;

  /** The preauth auth provider. */
  private PreAuthenticatedAuthenticationProvider preauthAuthProvider;

  /** The user details service wrapper. */
  private UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper;

  /** The access denied handler. */
  @Autowired
  private RmmRestAccessDeniedHandler accessDeniedHandler;
  
  /** The authentication entry point. */
  @Autowired
  private RmmRestAuthenticationEntryPoint authenticationEntryPoint;

  @PostConstruct
  protected final void postConstruct() throws Exception {
    preauthAuthProvider = new PreAuthenticatedAuthenticationProvider();
    userDetailsServiceWrapper = new UserDetailsByNameServiceWrapper<>();
    userDetailsServiceWrapper.setUserDetailsService(rmmUserDetailService);
    preauthAuthProvider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper);
    preauthFilter = new RequestHeaderAuthenticationFilter();
    preauthFilter.setExceptionIfHeaderMissing(false);
    preauthFilter.setPrincipalRequestHeader(securityProperties.getApiKeyHeader());
    preauthFilter.setAuthenticationManager(authenticationManager());
    preauthFilter.setContinueFilterChainOnUnsuccessfulAuthentication(false);

  }

  /**
   * Secure HTTP methods by role
   *
   * @param http the http
   * @throws Exception the exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // allow access to root and swagger page
    http.authorizeRequests().antMatchers("/swagger-ui/**", "/v3/api-docs/**","/").permitAll();

    // secure HTTP methods for base api url
    http.authorizeRequests()
         .antMatchers(HttpMethod.GET, BASE_API_URL).hasAnyAuthority("READ")
         .antMatchers(HttpMethod.POST, BASE_API_URL).hasAnyAuthority("CREATE")
         .antMatchers(HttpMethod.PUT, BASE_API_URL).hasAnyAuthority("UPDATE")
         .antMatchers(HttpMethod.DELETE, BASE_API_URL).hasAnyAuthority("DELETE")
         .and()
         .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
         .authenticationEntryPoint(authenticationEntryPoint);

    // default authetication for others
    http.authorizeRequests().anyRequest().authenticated();

    // setup to load users service based header as part of security filter chain
    http.addFilterAfter(preauthFilter, RequestHeaderAuthenticationFilter.class).authenticationProvider(preauthAuthProvider);
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(preauthAuthProvider);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // allow unrestricted traffic to h2 console to view DB
    web.ignoring().antMatchers("/h2-console/**");
  }


}

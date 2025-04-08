package no.ntnu.rentalroulette.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.enums.UserType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * Contains authentication information, needed by UserDetailsService.
 */
public class AccessUserDetails implements UserDetails {
  private final String username;
  private final String password;
  private final boolean isActive;
  private final List<GrantedAuthority> authorities = new LinkedList<>();

  /**
   * Create access object.
   *
   * @param user Teh user to copy data from
   */
  public AccessUserDetails(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
    System.out.println("User from database " + this.username + " " + this.password);
    this.isActive = user.isActive();
    this.addUserTypeAuthority(user.getUserType());
  }

  private void addUserTypeAuthority(UserType userType) {
    authorities.add(new SimpleGrantedAuthority("ROLE_" + userType.name()));
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(this, this.password, authorities));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isActive;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isActive;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isActive;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

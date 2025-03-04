package no.ntnu.rentalroulette.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import no.ntnu.rentalroulette.security.AuthenticationRequest;
import no.ntnu.rentalroulette.security.AuthenticationResponse;
import no.ntnu.rentalroulette.security.JwtUtil;
import no.ntnu.rentalroulette.security.AccessUserService;

@RestController
public class AuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AccessUserService userDetailsService;

  @Autowired
  JwtUtil jwtUtil;

  @CrossOrigin("http://localhost:5173")
  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(
      @RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException {
    System.out.println(authenticationRequest.toString());
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authenticationRequest.getUsername(),
              authenticationRequest.getPassword()
          )
      );
    } catch (BadCredentialsException e) {
      return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
    }

    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }
}

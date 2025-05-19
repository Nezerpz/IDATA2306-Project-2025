package no.ntnu.rentalroulette.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.RequestBody;
import no.ntnu.rentalroulette.security.AuthenticationRequest;
import no.ntnu.rentalroulette.security.JwtUtil;
import no.ntnu.rentalroulette.security.AccessUserService;


//TODO: Look into this https://stackoverflow.com/questions/27726066/jwt-refresh-token-flow
@RestController
public class AuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AccessUserService userDetailsService;

  @Autowired
  JwtUtil jwtUtil;

  @PostMapping("/authenticate")
  @Operation(
      summary = "Endpoint used to authenticate all users",
      description = "Returns JWT and Refresh Token Cookie"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Tokens are sent in response"
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Your account has been suspended/locked"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized, wrong password, etc."
      ),
  })
  public ResponseEntity<?> createAuthenticationToken(
      @RequestBody AuthenticationRequest authenticationRequest,
      HttpServletResponse response) throws BadCredentialsException {
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

    if (!userDetails.isAccountNonLocked()) {
        return new ResponseEntity<>("Your account is suspended", HttpStatus.FORBIDDEN);
    }

    final String jwtAccessToken = jwtUtil.generateAccessToken(userDetails);
    final String jwtRefreshToken = jwtUtil.generateRefreshToken(userDetails);

    // Set the refresh token as a cookie in the response
    Cookie refreshTokenCookie = new Cookie("refreshToken", jwtRefreshToken);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setSecure(true); // Set to true in production
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
    response.addCookie(refreshTokenCookie);

    // Return only the access token in the response body
    return ResponseEntity.ok(Map.of("accessToken", jwtAccessToken));
  }

  @PostMapping("/refresh-token")
  @Operation(
      summary = "Endpoint used to retreive new JWT based on Refresh Token Cookie",
      description = "Returns new JWT"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Token being sent in response"
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Your account has been suspended/locked"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized, wrong password, etc."
      ),
  })
  public ResponseEntity<?> refreshAccessToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return new ResponseEntity<>("No cookies found", HttpStatus.UNAUTHORIZED);
    }

    String refreshToken = null;
    for (Cookie cookie : cookies) {
      if ("refreshToken".equals(cookie.getName())) {
        refreshToken = cookie.getValue();
        break;
      }
    }

    if (refreshToken == null) {
      return new ResponseEntity<>("Refresh token not found", HttpStatus.UNAUTHORIZED);
    }

    String username = jwtUtil.extractUsername(refreshToken);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (!jwtUtil.validateToken(refreshToken, userDetails)) {
      return new ResponseEntity<>("Invalid refresh token", HttpStatus.FORBIDDEN);
    }

    String newAccessToken = jwtUtil.generateAccessToken(userDetails);

    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
  }
}

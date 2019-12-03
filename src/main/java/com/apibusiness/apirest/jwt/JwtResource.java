package com.apibusiness.apirest.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apibusiness.apirest.model.AuthenticationRequest;
import com.apibusiness.apirest.model.AuthenticationResponse;
import com.apibusiness.apirest.service.UserDetailServiceImpl;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(JwtResource.JWTS)
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class JwtResource {

    public static final String JWTS = "/jwts";
    public static final String AUTHENTICATE = "/authenticate";
    public static final String TOKEN = "/token";

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
    
    @PostMapping(value= AUTHENTICATE)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
    	try{
    		authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    	}catch(BadCredentialsException e) {
    		throw new Exception("Icorrect Username or Password",e);
    	}
    	
    	UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(authenticationRequest.getUsername());
    	
    	List<String> roleList = userDetails.getAuthorities().stream().map
                 (authority -> authority.getAuthority()).collect(Collectors.toList());
    	 
    	String token = jwtService.createToken(userDetails.getUsername(), roleList);
    	 
    	return ResponseEntity.ok(new AuthenticationResponse(token));
    	
    }

    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public String login(@AuthenticationPrincipal User activeUser) {
        List<String> roleList = activeUser.getAuthorities().stream().map
                (authority -> authority.getAuthority()).collect(Collectors.toList());
        return jwtService.createToken(activeUser.getUsername(), roleList);
    }

    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = JWTS)
    public String verify() {
        return "OK. permitido JWT";
    }
}
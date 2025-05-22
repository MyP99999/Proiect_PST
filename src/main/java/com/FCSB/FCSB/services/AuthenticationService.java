package com.FCSB.FCSB.services;


import com.FCSB.FCSB.auth.AuthenticationRequest;
import com.FCSB.FCSB.auth.AuthenticationResponse;
import com.FCSB.FCSB.auth.RegisterRequest;
import com.FCSB.FCSB.entities.Employee;
import com.FCSB.FCSB.entities.Role;
import com.FCSB.FCSB.jwt.JwtService;
import com.FCSB.FCSB.user.UserDetailServiceImp;
import com.FCSB.FCSB.validations.RegisterValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmployeeService userService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])([A-Za-z\\d@#$%^&+=!]{10,})$";

    @Autowired
    private RegisterValidation registerValidation;

    public AuthenticationResponse register(RegisterRequest request) {


        Employee newUser = new Employee();

        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(encodedPassword);

        Role defaultRole = roleService.findRoleById(1); // Fetch the default role
        newUser.setRole(defaultRole); // Set the default role
        // Save the new user to the database
        userService.save(newUser);

        // Authenticate the new user
        UserDetails userDetails = userDetailServiceImp.loadUserByUsername(newUser.getEmail());

        // Generate a JWT token for the new user
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userDetailServiceImp.loadUserByUsername(request.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
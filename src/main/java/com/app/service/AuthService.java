package com.app.service;


import com.app.dto.AuthResponse;
import com.app.dto.LoginRequest;
import com.app.dto.RegisterRequest;
import com.app.entity.User;
import com.app.exception.DuplicateEmailException;
import com.app.exception.InvalidCredentialsException;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.email())){
            throw new DuplicateEmailException("Email already registered: "+request.email());
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password())) // hash it, never store raw
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(()-> new InvalidCredentialsException("Invalid email or password"));

        if(!passwordEncoder.matches(request.password(),user.getPassword())){
            throw new InvalidCredentialsException("Invalid Email or Password");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }


}

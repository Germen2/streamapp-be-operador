package germen.streamapp.operador.service;

import germen.streamapp.operador.DTO.LoginDTO;
import germen.streamapp.operador.DTO.SignUpDTO;
import germen.streamapp.operador.enums.TokenType;
import germen.streamapp.operador.model.JwtToken;
import germen.streamapp.operador.model.Role;
import germen.streamapp.operador.model.TokenResponse;
import germen.streamapp.operador.model.User;
import germen.streamapp.operador.repository.JwtTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private JwtTokenRepository jwtTokenInterface;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public TokenResponse signIn(LoginDTO loginDTO){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
        ));

        User user = userService.getUserByEmail(loginDTO.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken =  jwtService.generateRefreshToken(user);

        jwtService.updateUserToken(user.getId(), jwtToken);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(jwtToken);
        tokenResponse.setRefreshToken(refreshToken);

        return tokenResponse;
    }

    public TokenResponse signUp(SignUpDTO signUpDTO){
        signUpDTO.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Role role = roleService.getRoleByName("USER").orElseThrow(() -> new RuntimeException("No existe rol con el nombre: USER" ));

        User newUser = new User();
        newUser.setFirstName(signUpDTO.getFirstName());
        newUser.setLastName(signUpDTO.getLastName());
        newUser.setEmail(signUpDTO.getEmail());
        newUser.setPassword(signUpDTO.getPassword());
        newUser.setRoles(Collections.singletonList(role));

        User savedUser = userService.saveUser(newUser);
        String token = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, token);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(token);
        tokenResponse.setRefreshToken(refreshToken);

        return tokenResponse;
    }

    private void saveUserToken(User user, String token){
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setTokenType(TokenType.BEARER);
        jwtToken.setExpired(false);
        jwtToken.setRevoked(false);
        jwtToken.setUser(user);

        jwtTokenInterface.save(jwtToken);
    }

    public TokenResponse refreshToken(String refreshToken) {
        String email = jwtService.extractUsername(refreshToken);
        User user = userService.getUserByEmail(email).orElseThrow();

        // Validar que el refresh token sea válido
        if (!jwtService.isTokenValid(refreshToken, user.getEmail())) {
            throw new RuntimeException("Refresh token inválido");
        }

        // Generar nuevos tokens
        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        // Actualizar token en DB
        jwtService.updateUserToken(user.getId(), newAccessToken);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(newAccessToken);
        tokenResponse.setRefreshToken(newRefreshToken);

        return tokenResponse;
    }

    public Boolean isTokenValid(String token){
        String email = jwtService.extractUsername(token);
        return jwtService.isTokenValid(token, email);
    }

    public User getUserFromToken(String token){
        return userService.getUserWithToken(token);
    }
}

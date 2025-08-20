package germen.streamapp.operador.controller;

import germen.streamapp.operador.DTO.LoginDTO;
import germen.streamapp.operador.DTO.SignUpDTO;
import germen.streamapp.operador.model.TokenResponse;
import germen.streamapp.operador.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> signIn(@RequestBody LoginDTO loginDTO){
        TokenResponse tokenResponse = authService.signIn(loginDTO);
        return ResponseEntity.ok(tokenResponse);
    }


    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> signUp(@RequestBody SignUpDTO signUpDTO){
        TokenResponse tokenResponse = authService.signUp(signUpDTO);
        return ResponseEntity.ok(tokenResponse);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(){

        return ResponseEntity.ok(null);
    }
}

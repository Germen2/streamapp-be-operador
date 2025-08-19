package germen.streamapp.operador;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordEncoderTest {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void testPassword() {
        String hash = "$2a$10$UOsP1wvE4y5j6DGoYv5cX.eyEoPhOOjRsMjOWARFs7SMEHIQ2uCi.";
        System.out.println(encoder.matches("1234", hash)); // debería imprimir true
    }
}

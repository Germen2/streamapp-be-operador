package germen.streamapp.operador.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
}

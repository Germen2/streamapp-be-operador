package germen.streamapp.operador.DTO;

import germen.streamapp.operador.model.User;
import lombok.Data;

@Data
public class ValidateTokenDTO {
    private Boolean valid;
    private User user;
}

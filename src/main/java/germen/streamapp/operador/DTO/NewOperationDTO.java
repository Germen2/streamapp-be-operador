package germen.streamapp.operador.DTO;

import germen.streamapp.operador.enums.OperationType;
import lombok.Data;

@Data
public class NewOperationDTO {
    private Long movieId;
    private OperationType operationType;
}

package germen.streamapp.operador.model;


import germen.streamapp.operador.enums.OperationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "movie_id")
    private Long movieId;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private OperationType operationType; // COMPRA o RENTA

    @Column(name = "operation_date")
    private LocalDateTime operationDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
}

package germen.streamapp.operador.repository;

import germen.streamapp.operador.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    // trae solo las operaciones de un usuario
    List<Operation> findByUserId(Long userId);
}

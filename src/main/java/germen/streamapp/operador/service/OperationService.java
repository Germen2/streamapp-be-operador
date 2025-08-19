package germen.streamapp.operador.service;

import germen.streamapp.operador.enums.OperationType;
import germen.streamapp.operador.mapper.OperationMapper;
import germen.streamapp.operador.model.Operation;
import germen.streamapp.operador.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationMapper operationMapper;


    public Optional<Operation> getOperation(Long id){
        return operationRepository.findById(id);
    }

    public List<Operation> getUserOperations(Long userId){
        return operationRepository.findByUserId(userId);
    }

    public Operation saveOperation(Operation operation){

        operation.setOperationDate(LocalDateTime.now());

        if (operation.getOperationType() == OperationType.RENTA) {
            operation.setExpirationDate(operation.getOperationDate().plusDays(3));
        } else {
            // Si es COMPRA → no expira
            operation.setExpirationDate(null);
        }

        return operationRepository.save(operation);
    }

    public Operation updateOperation(Long id, Operation operation){
        return operationRepository.findById(id).map(existingOperation -> {
            operationMapper.updateOperationFromSource(operation, existingOperation);
            return operationRepository.save(existingOperation);
        }).orElseThrow(() -> new RuntimeException("Operacion no encontrada con id " + id));
    }

    public void deleteOperation(Long id){
        operationRepository.deleteById(id);
    }
}

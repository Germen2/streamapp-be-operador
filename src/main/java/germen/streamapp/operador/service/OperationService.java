package germen.streamapp.operador.service;

import germen.streamapp.operador.DTO.NewOperationDTO;
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

    @Autowired
    private JwtService jwtService;


    public Optional<Operation> getOperation(Long id){
        return operationRepository.findById(id);
    }

    private List<Operation> getUserOperationsWithUserId(Long userId){
        return operationRepository.findByUserId(userId);
    }

    public List<Operation> getUserOperations(String token){
        Long userId = jwtService.extractUserId(token);
        String email = jwtService.extractUsername(token);

        if(!jwtService.isTokenValid(token, email)){
            return null;
        }

        return getUserOperationsWithUserId(userId);
    }

    public Operation saveOperation(NewOperationDTO newOperationDTO, String token){

        Operation operation = new Operation();
        operation.setOperationType(newOperationDTO.getOperationType());
        operation.setMovieId(newOperationDTO.getMovieId());
        operation.setOperationDate(LocalDateTime.now());

        Long userId = jwtService.extractUserId(token);
        operation.setUserId(userId);

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

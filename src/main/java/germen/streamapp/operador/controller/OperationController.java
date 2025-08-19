package germen.streamapp.operador.controller;

import germen.streamapp.operador.model.Operation;
import germen.streamapp.operador.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Operation>> getUserOperations(@PathVariable Long userId) {
        List<Operation> operations = operationService.getUserOperations(userId);
        return ResponseEntity.ok(operations);
    }

    // GET /operations/{id} → obtener una categoría por id
    @GetMapping("/{id}")
    public ResponseEntity<Operation> getOperation(@PathVariable Long id) {
        return ResponseEntity.of(operationService.getOperation(id));
    }

    // POST /operations → crear una nueva operacion
    @PostMapping
    public ResponseEntity<Operation> addOperation(@RequestBody Operation operation) {
        Operation saved = operationService.saveOperation(operation);
        return ResponseEntity.status(201).body(saved); // 201 Created
    }

    // DELETE /operations/{id} → eliminar una operacion por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        operationService.deleteOperation(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // PUT /operations/{id} → actualizar una operacion existente
    @PutMapping("/{id}")
    public ResponseEntity<Operation> updateOperation(@PathVariable Long id, @RequestBody Operation operation) {
        Operation updated = operationService.updateOperation(id, operation);
        return ResponseEntity.ok(updated);
    }

}

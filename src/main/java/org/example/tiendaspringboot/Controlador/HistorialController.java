package org.example.tiendaspringboot.Controlador;

import jakarta.validation.Valid;
import org.example.tiendaspringboot.Modelo.Historial;
import org.example.tiendaspringboot.Service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/historiales")
@CacheConfig(cacheNames = {"historiales"})
public class HistorialController {

    private final HistorialService historialService;

    @Autowired
    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    @GetMapping
    public ResponseEntity<List<Historial>> listarTodos() {
        List<Historial> historiales = historialService.listarTodos();
        return new ResponseEntity<>(historiales, HttpStatus.OK);
    }


    @Cacheable
    @GetMapping("/{id}")
    public ResponseEntity<Historial> obtenerPorId(@PathVariable Integer id) {
        return historialService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<String> crear(@Valid @RequestBody Historial historial) {
        try {
            String guardarHistorial= historialService.guardar(historial);
            return ResponseEntity.ok(guardarHistorial);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Historial> actualizar(@PathVariable Integer id, @Valid @RequestBody Historial historial) {
//        return historialService.buscarPorId(id).map(h -> {
//            h.setCliente(historial.getCliente());
//            h.setProducto(historial.getProducto());
//            h.setFechaCompra(historial.getFechaCompra());
//            h.setCantidad(historial.getCantidad());
//            h.setTipo(historial.getTipo());
//            h.setDescripcion(historial.getDescripcion());
//            Historial historialActualizado = historialService.guardar(h);
//            return new ResponseEntity<>(historialActualizado, HttpStatus.OK);
//        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (historialService.buscarPorId(id).isPresent()) {
            historialService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}

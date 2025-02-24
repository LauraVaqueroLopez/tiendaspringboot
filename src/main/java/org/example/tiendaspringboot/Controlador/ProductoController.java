package org.example.tiendaspringboot.Controlador;

import jakarta.validation.Valid;
import org.example.tiendaspringboot.Modelo.Producto;
import org.example.tiendaspringboot.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
@CacheConfig(cacheNames = {"productos"})
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok().body(productos);
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Producto> obtener(@PathVariable Integer id) {
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Producto producto) {

        Optional<Producto> productoExistente = productoService.buscarPorNombre(producto.getNombre());

        if (productoExistente.isPresent()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "El producto con el nombre '" + producto.getNombre() + "' ya existe.");
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            Producto nuevoProducto = productoService.guardar(producto);
            return ResponseEntity.ok().body(nuevoProducto);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @Valid @RequestBody Producto producto) {


        return productoService.buscarPorId(id).map(p ->{
            p.setNombre(producto.getNombre());
            p.setDescripcion(producto.getDescripcion());
            p.setPrecio(producto.getPrecio());
            p.setStock(producto.getStock());
            Producto actualizado = productoService.guardar(p);
            return ResponseEntity.ok(actualizado);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Producto> eliminar(@PathVariable Integer id) {
        if (productoService.buscarPorId(id).isPresent()) {
            productoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

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

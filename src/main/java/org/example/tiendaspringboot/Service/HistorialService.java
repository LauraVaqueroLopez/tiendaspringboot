package org.example.tiendaspringboot.Service;


import org.example.tiendaspringboot.Modelo.Historial;
import org.example.tiendaspringboot.Modelo.Producto;
import org.example.tiendaspringboot.Repositorio.HistorialRepository;
import org.example.tiendaspringboot.Repositorio.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository historialRepository;
    @Autowired
    private ProductosRepository productosRepository;

    public List<Historial> listarTodos() {
        return historialRepository.findAll();
    }

    public Optional<Historial> buscarPorId(Integer id) {
        return historialRepository.findById(id);
    }

    @Transactional
    public String guardar(Historial historial) {
        Optional<Producto>optionalProducto = productosRepository.findById(historial.getProducto().getId());
        Producto producto=optionalProducto.get();
        if (historial.getCantidad()>producto.getStock()){
            return "No hay stock suficiente";
        }
        historialRepository.save(historial);
        producto.setStock(producto.getStock()-historial.getCantidad());
        productosRepository.save(producto);
        return "Registro guardado";
    }

    public void eliminar(Integer id) {
        historialRepository.deleteById(id);
    }
}
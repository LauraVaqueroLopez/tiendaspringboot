package org.example.tiendaspringboot.Service;

import org.example.tiendaspringboot.Modelo.Producto;
import org.example.tiendaspringboot.Repositorio.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductosRepository productosRepository;

    public ProductoService(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    public List<Producto> getAll() {
        return productosRepository.findAll();
    }

    public Optional<Producto> getById(int id) {
        return productosRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return productosRepository.save(producto);
    }

    public void eliminar(int id) {
        productosRepository.deleteById(id);
    }
}

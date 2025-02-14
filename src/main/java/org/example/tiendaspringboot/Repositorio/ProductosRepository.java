package org.example.tiendaspringboot.Repositorio;

import org.example.tiendaspringboot.Modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductosRepository extends JpaRepository<Producto, Integer> {

    Optional<Producto> findByNombre(String nombre);

}

package org.example.tiendaspringboot.Service;


import org.example.tiendaspringboot.Modelo.Historial;
import org.example.tiendaspringboot.Repositorio.HistorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    public List<Historial> listarTodos() {
        return historialRepository.findAll();
    }

    public Optional<Historial> buscarPorId(Integer id) {
        return historialRepository.findById(id);
    }

    public Historial guardar(Historial historial) {
        return historialRepository.save(historial);
    }

    public void eliminar(Integer id) {
        historialRepository.deleteById(id);
    }
}
package org.example.tiendaspringboot.Service;

import org.example.tiendaspringboot.Modelo.Cliente;

import org.example.tiendaspringboot.Repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public class ClienteService {

    @Service
    public class ClienteServicio {

        @Autowired
        private ClienteRepository clienteRepository;

        public List<Cliente> listarTodos() {
            return clienteRepository.findAll();
        }

        public Optional<Cliente> buscarPorId(Integer id) {
            return clienteRepository.findById(id);
        }

        public Cliente guardar(Cliente cliente) {
            return clienteRepository.save(cliente);
        }

        public void eliminar(Integer id) {
            clienteRepository.deleteById(id);
        }
    }
}
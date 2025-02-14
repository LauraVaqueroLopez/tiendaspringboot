package org.example.tiendaspringboot.Service;

import org.example.tiendaspringboot.Modelo.Cliente;
import org.example.tiendaspringboot.Repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Integer id, Cliente clienteActualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteActualizado.getNombre());
                    cliente.setApellido(clienteActualizado.getApellido());
                    cliente.setNickname(clienteActualizado.getNickname());
                    cliente.setPassword(clienteActualizado.getPassword());
                    cliente.setTelefono(clienteActualizado.getTelefono());
                    cliente.setDomicilio(clienteActualizado.getDomicilio());
                    return clienteRepository.save(cliente);
                }).orElseThrow(() -> new RuntimeException("Cliente no encontrado con id " + id));
    }

    public void eliminar(Integer id) {
        clienteRepository.deleteById(id);
    }
}

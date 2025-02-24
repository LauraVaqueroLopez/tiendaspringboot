package org.example.tiendaspringboot.Service;


import org.example.tiendaspringboot.Modelo.Cliente;
import org.example.tiendaspringboot.Modelo.Historial;
import org.example.tiendaspringboot.Modelo.Producto;
import org.example.tiendaspringboot.Repositorio.ClienteRepository;
import org.example.tiendaspringboot.Repositorio.HistorialRepository;
import org.example.tiendaspringboot.Repositorio.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository historialRepository;
    @Autowired
    private ProductosRepository productosRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Historial> listarTodos() {
        return historialRepository.findAll();
    }

    public Optional<Historial> buscarPorId(Integer id) {
        return historialRepository.findById(id);
    }

    @Transactional
    public String guardar(Historial historial) {
        Optional<Producto> optionalProducto = productosRepository.findById(historial.getProducto().getId());
        Optional<Cliente> optionalCliente = clienteRepository.findById(historial.getCliente().getId());

        if (optionalProducto.isEmpty() || optionalCliente.isEmpty()) {
            return "Producto o Cliente no encontrado";
        }

        Producto producto = optionalProducto.get();
        Cliente cliente = optionalCliente.get();

        List<Historial> historialCliente = historialRepository.findAll();

        int cantidadComprada = 0;
        int cantidadDevuelta = 0;

        for (Historial h : historialCliente) {
            h.getFechaCompra().plusDays(30).isAfter(LocalDate.now());
            if (h.getCliente().getId().equals(cliente.getId()) && h.getProducto().getId().equals(producto.getId())) {
                if ("COMPRA".equalsIgnoreCase(h.getTipo())) {
                    cantidadComprada += h.getCantidad();
                } else if ("DEVOLUCION".equalsIgnoreCase(h.getTipo())) {
                    cantidadDevuelta += h.getCantidad();
                }
            }
        }

        if ("COMPRA".equalsIgnoreCase(historial.getTipo())) {
            if (historial.getCantidad() > producto.getStock()) {
                return "No hay stock suficiente";
            }
            producto.setStock(producto.getStock() - historial.getCantidad());

        } else if ("DEVOLUCION".equalsIgnoreCase(historial.getTipo())) {
            if (cantidadDevuelta + historial.getCantidad() > cantidadComprada) {
                return "No puedes devolver más productos de los que compraste";
            }
            producto.setStock(producto.getStock() + historial.getCantidad());
        } else {
            return "Tipo de operación no válida (Debe ser COMPRA o DEVOLUCION)";
        }
        historialRepository.save(historial);
        productosRepository.save(producto);

        return "Registro guardado correctamente";
    }


    public void eliminar(Integer id) {
        historialRepository.deleteById(id);
    }
}
package org.example.tiendaspringboot.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100, unique = true) // Restricción de unicidad
    @NotBlank(message = "El campo nombre no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El nombre del producto solo debe contener caracteres alfanuméricos")
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    @NotBlank(message = "El campo descripción no puede estar vacío")
    private String descripcion;

    @NotNull
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    @NotBlank(message = "El campo precio no puede estar vacío")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "El precio debe ser positivo")
    private BigDecimal precio;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "producto")
    private Set<Historial> historials = new LinkedHashSet<>();

    // Método para verificar las condiciones del precio y modificar la descripción
    public void actualizarDescripcionConLiteral() {
        if (precio.compareTo(new BigDecimal("10")) < 0) {
            // Si el precio es menor a 10, agregar "producto de oferta"
            this.descripcion = this.descripcion + " (producto de oferta)";
        } else if (precio.compareTo(new BigDecimal("200")) > 0) {
            // Si el precio es mayor a 200, agregar "producto de calidad"
            this.descripcion = this.descripcion + " (producto de calidad)";
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
        actualizarDescripcionConLiteral(); // Actualizar la descripción al establecer el precio
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<Historial> getHistorials() {
        return historials;
    }

    public void setHistorials(Set<Historial> historials) {
        this.historials = historials;
    }
}

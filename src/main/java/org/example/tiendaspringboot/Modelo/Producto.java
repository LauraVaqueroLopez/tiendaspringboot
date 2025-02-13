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
    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "el campo nombre no puede estar vacio")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message ="El nombre del producto solo debe contener caracteres alfanumericos")
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    @NotBlank(message = "el campo descripcion no puede estar vacio")
    private String descripcion;

    @NotNull
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    @NotBlank(message = "el campo precio no puede estar vacio")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message ="El precio debe ser positivo")
    private BigDecimal precio;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "producto")
    private Set<Historial> historials = new LinkedHashSet<>();

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
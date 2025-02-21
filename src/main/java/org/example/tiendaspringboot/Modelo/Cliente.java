package org.example.tiendaspringboot.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 50)
    @NotBlank(message = "el campo nombre no puede estar vacio")
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúÑñ]+$", message ="El nombre del cliente solo debe contener caracteres alfabeticos")
    private String nombre;

    @Size(max = 50)
    @NotNull
    @Column(name = "apellido", nullable = false, length = 50)
    @NotBlank(message = "el campo apellido no puede estar vacio")
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúÑñ]+$", message ="El apellido del cliente solo debe contener caracteres alfabeticos")
    private String apellido;

    @Size(max = 50)
    @NotNull
    @Column(name = "nickname", nullable = false, length = 50)
    @NotBlank(message = "el campo nickname no puede estar vacio")
    private String nickname;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    @NotBlank(message = "el campo password no puede estar vacio")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$", message ="la password debe contener entre 4 y 12 caracteres alfanumericos")
    private String password;

    @Size(max = 15)
    @Column(name = "telefono", length = 15)
    @NotBlank(message = "el campo telefono no puede estar vacio")
    @Pattern(regexp = "^[69]{8}$", message ="El telefono debe tener 9 digitos que empiecen por 6 o 9.")
    private String telefono;

    @Size(max = 100)
    @Column(name = "domicilio", length = 100)
    @NotBlank(message = "el campo domicilio no puede estar vacio")
    private String domicilio;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnoreProperties({"cliente"})
    private Set<Historial> historials = new LinkedHashSet<>();

    public Cliente(){

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Set<Historial> getHistorials() {
        return historials;
    }

    public void setHistorials(Set<Historial> historials) {
        this.historials = historials;
    }

}
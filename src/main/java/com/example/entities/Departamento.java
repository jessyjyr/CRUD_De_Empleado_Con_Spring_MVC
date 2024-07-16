package com.example.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departamentos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Departamento  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;

    //Las relaciones son bidireccionales, quién posee la relación:
    //Para eso usamos el mappedBy.
    //Relacion con Empleados: un Dpto tiene muchos empleados.

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
    private List<Empleado> empleados;

}

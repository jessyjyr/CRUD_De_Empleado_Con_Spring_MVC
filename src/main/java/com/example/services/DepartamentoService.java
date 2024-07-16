package com.example.services;

import java.util.List;

import com.example.entities.Departamento;

public interface DepartamentoService {

    public List<Departamento> getDepartamentos();
    public Departamento getDepartamento(int idDepartamento);
    public void persistirDepartamento(Departamento departamento);
}

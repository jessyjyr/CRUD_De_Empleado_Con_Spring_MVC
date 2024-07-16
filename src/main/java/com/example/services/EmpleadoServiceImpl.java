package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.dao.EmpleadoDao;
import com.example.entities.Empleado;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    //Se necesita inyectar el objeto DAO, es decir, una Inyección de Dependencia
    // DI (Dependency Injection) de EmpleadoDao, porque los metodos de la capa 
    // de la capa de servicios se van a implementar a partir de los metodos de la capa DAO.
    //¿Cómo se inyecta, una dependencia?
    //Rpta: 
    //VARIANTE Nº1. De forma automática, utilizando la anotación @Autowired
    //@Autowired
    //private EmpleadoDao empleadoDao;

    //VARIANTE Nº2. Por constructor, que es la variante recomendada actualmente.
    private final EmpleadoDao empleadoDao;

    
    @Override
    public List<Empleado> getEmpleados() {
       return empleadoDao.findAll();
    }

    @Override
    public Empleado getEmpleado(int idEmpleado) {
    
       Optional<Empleado> optionalEmpleado = empleadoDao.findById(idEmpleado);

       if(optionalEmpleado.isPresent()) 
           return optionalEmpleado.get();
       else
           return null;
        
       }
        
    

    @Override
    public void persistirUpdateEmpleado(Empleado empleado) {
      empleadoDao.save(empleado);
    }

    @Override
    public void deleteEmpleado(int idEmpleado) {
        empleadoDao.deleteById(idEmpleado);
    }

}

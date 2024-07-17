package com.example.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
@RequestMapping(value = "/empleado", method = {RequestMethod.POST, RequestMethod.GET})
@RequiredArgsConstructor

public class EmpleadoController {

    private static final Logger LOG = Logger.getLogger("EmpleadoControll");
    private final EmpleadoService empleadoService; 
    private final DepartamentoService departamentoService;

//Metodo donde se delega la petición de mostrar todos los empleados, de la tabla empleados
//Devuelve un string, que es el nombre de la vista, que se mostrar al cliente, en el navegador

@GetMapping("/all")
    public String dameTodosLosEmpleados(Model model) {

    List<Empleado> empleados = empleadoService.getEmpleados();    

        model.addAttribute("empleados", empleados);
        return "listadoDeEmpleados"; 
    }

//Metodo que muestre el formulario de alta de empleado
    @GetMapping("/frmAltaEmpleado")
    public String formularioAltaEmpleado(Model model) {

//Hay que enviar a la vista un objeto empleado vacío para que se vincule con los controles del formulario

Empleado empleado = new Empleado();

//Tambien hay que enviar los departamentos

List<Departamento> departamentos = departamentoService.getDepartamentos();

model.addAttribute("empleado", empleado);
model.addAttribute("departamentos", departamentos);

        return "altaModificacionEmpleado";
    }

//Metodo que recibe por POST los datos de los controles del formulario de alta de empleado
@PostMapping("/persistirEmpleado")
public String persistir(@ModelAttribute Empleado empleado,
    @RequestParam(name = "imagen", required = false) MultipartFile archivoDeImagen) {
        if(!archivoDeImagen.isEmpty()) {

    //La ruta relativa de la carpeta donde se va a almacenar la foto
    Path rutaRelativa = Paths.get("src\\main\\resources\\static\\imagenes\\");
    
    //Necesitamos La ruta absoluta 
    String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();

    //Y, finalmente la ruta completa 
    Path rutaCompleta = Paths.get(rutaAbsoluta + "\\" + 
    archivoDeImagen.getOriginalFilename());

    //Manejar la imagen (archivoDeImagen)
    try {
        byte[] archivoDeImagenEnBytes = archivoDeImagen.getBytes();
        Files.write(rutaCompleta, archivoDeImagenEnBytes);

    //Establecer (setter) el nombre de la imagen recibida ala propiedad foto del empleado
    empleado.setFoto(archivoDeImagen.getOriginalFilename());
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    }

    empleadoService.persistirUpdateEmpleado(empleado);

    return "redirect:/empleado/all";

}
//Metodo que muestra el formulario de actualizacion de un empleado
@GetMapping("/frmActualizar/{id}")

public String formularioActualizacionEmpleado(@PathVariable(name = "id") int idEmpleado,
Model model) {

//Con el id del empleado, solicitamos al servicio de empleados que nos traiga 
//el empleado correspondiente 

Empleado empleado = empleadoService.getEmpleado(idEmpleado);
List<Departamento> departamentos = departamentoService.getDepartamentos();

model.addAttribute("empleado", empleado);
model.addAttribute("departamentos", departamentos);

    return "altaModificacionEmpleado";
}

//Metodo para eliminar un empleado

    @GetMapping("/eliminarEmpleado/{id}")
    public String deleteEmpleado(@PathVariable(name = "id") int idEmpleado) {
        empleadoService.deleteEmpleado(idEmpleado);
        return "redirect:/empleado/all";


}
}

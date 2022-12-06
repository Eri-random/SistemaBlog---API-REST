package com.sistema.blog.controller;

import com.sistema.blog.DTO.PublicacionDTO;
import com.sistema.blog.DTO.PublicacionRespuesta;
import com.sistema.blog.service.PublicacionService;
import com.sistema.blog.util.AppConstantes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public PublicacionRespuesta listarPublicaciones(@RequestParam(value = "pageNumber", defaultValue = AppConstantes.NUMERO_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordernarPor,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir){

        return publicacionService.obtenerTodasLasPublicaciones(numeroDePagina,medidaDePagina, ordernarPor, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id")Long id){
        return ResponseEntity.ok(publicacionService.obtenerPublicacionPorId(id));
    }

    @PostMapping
    public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
        return new ResponseEntity<>(publicacionService.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@PathVariable(value = "id") Long id, @Valid @RequestBody PublicacionDTO publicacionRequest){
        PublicacionDTO publicacionRespuesta = publicacionService.actualizarPublicacion(publicacionRequest,id);
        return new ResponseEntity<>(publicacionRespuesta,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable(value = "id") Long id){
        publicacionService.EliminarPublicacion(id);
        return new ResponseEntity<>("Publicacion Eliminada con exito",HttpStatus.OK);
    }
}

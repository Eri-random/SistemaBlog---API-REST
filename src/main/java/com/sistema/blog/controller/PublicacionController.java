package com.sistema.blog.controller;

import com.sistema.blog.DTO.PublicacionDTO;
import com.sistema.blog.service.PublicacionService;
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

    @PostMapping
    public ResponseEntity<PublicacionDTO> guardarPublicacion(@RequestBody PublicacionDTO publicacionDTO){
        return new ResponseEntity<>(publicacionService.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public List<PublicacionDTO> listarPublicaciones(){
        return publicacionService.obtenerTodasLasPublicaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id")Long id){
        return ResponseEntity.ok(publicacionService.obtenerPublicacionPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@PathVariable(value = "id") Long id, @RequestBody PublicacionDTO publicacionRequest){
        PublicacionDTO publicacionRespuesta = publicacionService.actualizarPublicacion(publicacionRequest,id);
        return new ResponseEntity<>(publicacionRespuesta,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable(value = "id") Long id){
        publicacionService.EliminarPublicacion(id);
        return new ResponseEntity<>("Publicacion Eliminada con exito",HttpStatus.OK);
    }
}

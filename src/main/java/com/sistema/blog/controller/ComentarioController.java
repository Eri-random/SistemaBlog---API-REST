package com.sistema.blog.controller;

import com.sistema.blog.DTO.ComentarioDTO;
import com.sistema.blog.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacionId(@PathVariable(value = "publicacionId") Long publicacionId){
        return comentarioService.obtenerComentariosPorPublicacionId(publicacionId);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable(value = "publicacionId") Long publicacionId,
                                                                @PathVariable(value = "id") Long id){

        ComentarioDTO comentarioDTO = comentarioService.obtenerComentarioPorId(publicacionId, id);
        return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
    }

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(value = "publicacionId") Long publicacionId,
                                                           @Valid @RequestBody ComentarioDTO comentarioDTO){

        return new ResponseEntity<>(comentarioService.crearComentario(publicacionId,comentarioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable(value = "publicacionId") Long publicacionId,
                                                           @PathVariable(value = "comentarioId") Long comentarioId,
                                                           @Valid @RequestBody ComentarioDTO comentarioDTO) {

        ComentarioDTO comentarioActualizado = comentarioService.actualizarComentario(publicacionId,comentarioId,comentarioDTO);
        return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);

    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<String> eliminarComentario(@PathVariable(value = "publicacionId") Long publicacionId,
                                                     @PathVariable(value = "comentarioId") Long comentarioId){

        comentarioService.eliminarComentario(publicacionId,comentarioId);
        return new ResponseEntity<>("Comentario eliminado con exito", HttpStatus.OK);
    }

}

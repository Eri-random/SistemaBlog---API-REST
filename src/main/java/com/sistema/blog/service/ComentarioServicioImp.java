package com.sistema.blog.service;

import com.sistema.blog.DTO.ComentarioDTO;
import com.sistema.blog.DTO.PublicacionDTO;
import com.sistema.blog.exception.BlogAppException;
import com.sistema.blog.exception.ResourceNotFoundException;
import com.sistema.blog.model.Comentario;
import com.sistema.blog.model.Publicacion;
import com.sistema.blog.repository.ComentarioRepository;
import com.sistema.blog.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServicioImp implements ComentarioService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario = mapearEntidad(comentarioDTO);

        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() ->new ResourceNotFoundException("Publicacion","PublicacionId", publicacionId));

        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = comentarioRepository.save(comentario);
        return mapearDTO(nuevoComentario);

    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId) {
        List<Comentario> comentarios = comentarioRepository.findByPublicacionId(publicacionId);

        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() ->new ResourceNotFoundException("Publicacion","PublicacionId", publicacionId));

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario","ComentarioId", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicacion");
        }

        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudDeComentario) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() ->new ResourceNotFoundException("Publicacion","PublicacionId", publicacionId));

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario","ComentarioId", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicacion");
        }

        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());

        Comentario comentarioActualizado = comentarioRepository.save(comentario);
        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "PublicacionId", publicacionId));

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario","ComentarioId", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicacion");
        }

        comentarioRepository.delete(comentario);
    }


    //Convertir a DTO
    private ComentarioDTO mapearDTO(Comentario comentario){

        ComentarioDTO comentarioDTO = new ModelMapper().map(comentario, ComentarioDTO.class);

        return comentarioDTO;
    }

    //Convertir a Entidad
    private Comentario mapearEntidad(ComentarioDTO comentarioDTO){

        Comentario comentario = new ModelMapper().map(comentarioDTO,Comentario.class);

        return comentario;
    }

}

package com.sistema.blog.service;

import com.sistema.blog.DTO.PublicacionDTO;
import com.sistema.blog.exception.ResourceNotFoundException;
import com.sistema.blog.model.Publicacion;
import com.sistema.blog.repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicacionServicioImp implements PublicacionService{

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        //Convertimos de DTO a entidad
        Publicacion publicacion = mapearEntidad(publicacionDTO);

        Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);

        //Convertimos de entidad a DTO
        PublicacionDTO publicacionResponse = mapearDTO(nuevaPublicacion);

        return publicacionResponse;
    }

    @Override
    public List<PublicacionDTO> obtenerTodasLasPublicaciones() {
        List<Publicacion> publicaciones = publicacionRepository.findAll();
        return publicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());

    }

    @Override
    public PublicacionDTO obtenerPublicacionPorId(Long id) {
        Publicacion publicacion = publicacionRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("publicacion","id",id));

        return mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id) {
        Publicacion publicacion = publicacionRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("publicacion","id",id));

        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        Publicacion publicacionActualizada = publicacionRepository.save(publicacion);

        return mapearDTO(publicacionActualizada);

    }

    @Override
    public void EliminarPublicacion(Long id) {
        Publicacion publicacion = publicacionRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("publicacion","id",id));

        publicacionRepository.delete(publicacion);
    }


    //Convertir a DTO
    private PublicacionDTO mapearDTO(Publicacion publicacion){

        PublicacionDTO publicacionDTO = new PublicacionDTO();

        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setTitulo(publicacion.getTitulo());
        publicacionDTO.setDescripcion(publicacion.getDescripcion());
        publicacionDTO.setContenido(publicacion.getContenido());

        return publicacionDTO;
    }

    //Convertir a Entidad
    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){

        Publicacion publicacion = new Publicacion();

        publicacion.setId(publicacionDTO.getId());
        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        return publicacion;
    }


}

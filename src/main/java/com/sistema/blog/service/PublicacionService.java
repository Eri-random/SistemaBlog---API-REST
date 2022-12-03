package com.sistema.blog.service;

import com.sistema.blog.DTO.PublicacionDTO;

import java.util.List;

public interface PublicacionService {

    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);

    public List<PublicacionDTO> obtenerTodasLasPublicaciones();

    public PublicacionDTO obtenerPublicacionPorId(Long id);

    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id);

    public void EliminarPublicacion(Long id);
}

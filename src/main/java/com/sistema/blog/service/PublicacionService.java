package com.sistema.blog.service;

import com.sistema.blog.DTO.PublicacionDTO;
import com.sistema.blog.DTO.PublicacionRespuesta;


public interface PublicacionService {

    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);

    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);

    public PublicacionDTO obtenerPublicacionPorId(Long id);

    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id);

    public void EliminarPublicacion(Long id);
}

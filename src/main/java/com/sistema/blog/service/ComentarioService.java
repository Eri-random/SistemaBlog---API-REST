package com.sistema.blog.service;

import com.sistema.blog.DTO.ComentarioDTO;

import java.util.List;

public interface ComentarioService {

    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO);

    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId);

    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId);

    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudDeComentario);

    public void eliminarComentario(Long publicacionId, Long comentarioId);
}

package com.sistema.blog.repository;

import com.sistema.blog.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario,Long> {

    public List<Comentario> findByPublicacionId(Long id);

}

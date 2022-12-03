package com.sistema.blog.repository;

import com.sistema.blog.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {

}

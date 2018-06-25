package edu.uv.twcam.login.model;
import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface UsuarioDao extends CrudRepository<Usuario,String>{
   public Usuario findByUser(String username);
}

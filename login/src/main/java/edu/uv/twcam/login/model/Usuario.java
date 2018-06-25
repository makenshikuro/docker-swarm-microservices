package edu.uv.twcam.login.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String user;
   private String password;
   private String rol;

   public Usuario(){}

   public Usuario(String user, String password, String rol){
      this.user = user;
      this.password = password;
      this.rol = rol;
   }
    
   public long getId() {
      return id;
   }
   public void setId(long id) {
      this.id = id;
   }
   public String getUser() {
      return user;
   }
   public String getPassword() {
      return password;
   }
   public String getRol() {
      return rol;
   }
   public void setUser(String user) {
      this.user = user;
   }
   public void setPassword(String password) {
      this.password = password;
   }
   public void setRol(String rol) {
      this.rol = rol;
   }
}

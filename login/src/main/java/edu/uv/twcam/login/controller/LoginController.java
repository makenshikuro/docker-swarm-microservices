package edu.uv.twcam.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.uv.twcam.login.model.Usuario;
import edu.uv.twcam.login.model.UsuarioDao;
import edu.uv.twcam.login.config.JWTCreator;

@RestController
class LoginController {
   private final Logger log = LoggerFactory.getLogger(LoginController.class);
    
   @Autowired
   private Gson gson;

   @Autowired
   private UsuarioDao users;

   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;


   @Autowired
   private JWTCreator jwt;

   @RequestMapping(value = "/login", method = RequestMethod.POST)
   public String login(@RequestBody String u, HttpServletResponse resp, HttpServletRequest req) {
      String msg="";
        
      Usuario aux = gson.fromJson(u, Usuario.class);
      Usuario userdb = users.findByUser(aux.getUser());
      boolean success=false;
      if (userdb != null) {
         if (bCryptPasswordEncoder.matches(aux.getPassword(), userdb.getPassword())) {
            success=true;
            msg=jwt.getJWT(userdb);
            log.debug("/login endpoint. User: " + aux.getUser() + " logged successfully");        
         }
      }
      if (!success){
         resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
         msg="Not logged";
         log.debug("/login endpoint. User: " + aux.getUser() + " provided invalid credentials");        
         log.debug("Incorrect login from : " + req.getRemoteAddr());
         log.debug("Incorrect login using agent : " + req.getHeader("User-Agent"));
      }
      return msg;
   }

   @RequestMapping(value = "/register", method = RequestMethod.POST)
   public String register(@RequestBody String u, HttpServletResponse resp) {
      String msg ="";
      Usuario aux = gson.fromJson(u, Usuario.class);

      // Check user and password
      Usuario userdb = users.findByUser(aux.getUser());
      if ( userdb == null ){
         aux.setPassword(bCryptPasswordEncoder.encode(aux.getPassword()));
         aux.setRol("user");
         users.save(aux);
         msg = jwt.getJWT(aux);
         log.debug("/register endpoint. New user: " + aux.getUser());  
      }else{
         resp.setStatus(HttpServletResponse.SC_CONFLICT);
         msg = "User already registered. Use /login endpoint";
         log.debug("/register endpoint. User: " + aux.getUser() + " already registered");
      }
      return msg;
   }

}

package edu.uv.twcam.login.config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import edu.uv.twcam.login.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTCreator{

   // Obtain from application.properties or environment (default value: 3600)
   @Value("${expiration.token.time:3600}")
   private long expiration_time;

   @Value("${secret.token:provideoneatruntime}")
   private String secret;

   /**
    * @param user logged or registered user to generate JWT
    */
   public String getJWT(Usuario user) {
      String token = Jwts.builder().setSubject(user.getUser()).setIssuer("Fotos")
         .setExpiration(new Date(System.currentTimeMillis() + expiration_time))
         .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
      return token;
   }

}

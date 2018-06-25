package edu.uv.twcam.fotos.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import edu.uv.twcam.fotos.Foto;
import edu.uv.twcam.fotos.FotoRepository;

@Service
public class FotosService {
	private static final Logger logger = LoggerFactory.getLogger(FotosService.class);

	

	@Value("${SWIFT_User}")
    private String swiftUser;

    @Value("${SWIFT_Pass}")
    private String swiftPass;
   
    @Value("${SWIFT_URL}")
    private String swiftUrl;
	   
	@Autowired
	private FotoRepository repository;
	
	
	   
	public String upload(InputStream file, String titulo, String description, String user,String ext) {
		
		
				String url = uploadFile(file,user,titulo,ext);

			
				Foto foto = new Foto();
				foto.setId(user+"-"+titulo);
				foto.setDescripcion(description);
				foto.setTitulo(titulo);
				foto.setUser(user);
				foto.setUrl(url);
				
				
				repository.save(foto);
	
		
	      return url;
	   } 
	
	
	public String download(String user) {

				
		Gson gson = new Gson();
		
		String json = gson.toJson(repository.findByUser(user));

		return json;
	}
	
	public String reset() {
		
		repository.deleteAll();
		
		return "Todo Borrado";
	}
	
	public String count(String user) {
	
		Account account = getSwiftAccount();
		

		System.out.println("nombred: "+ user);
		Container container = account.getContainer(user);
		
		int value = container.getCount();
		
		return "El Usuario "+user+" tiene "+value+" imagenes";
	}
	
	private String uploadFile(InputStream file, String user, String titulo,String ext) {
		
		Account account = getSwiftAccount();
		
		Container container = account.getContainer(user);
		
		if(!container.exists()) {
			container.create();
		    container.makePublic();
		}
		
		
		
		
		StoredObject object = container.getObject(titulo.replaceAll(" ", "")+"."+ext);
	    object.uploadObject(file);
	    System.out.println("Public URL: "+object.getPublicURL());
		
		
		return object.getPublicURL();
	}
	
	private Account getSwiftAccount() {
		System.out.println("SwiftConncectionFactory() called");

	      AccountConfig config = new AccountConfig();
	      config.setPassword(swiftPass);
	      config.setUsername(swiftUser);
	      config.setAuthUrl(swiftUrl);
	      config.setAuthenticationMethod(AuthenticationMethod.BASIC);
	      
	      System.out.println(" Account creada ");
	      return new AccountFactory(config).createAccount();
	}
}

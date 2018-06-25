package edu.uv.twcam.fotos.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.uv.twcam.fotos.controller.FotosController;
import edu.uv.twcam.fotos.service.FotosService;
import edu.uv.twcam.fotos.config.JWTChecker;

@RestController
public class FotosController {
	
	private static final Logger logger = LoggerFactory.getLogger(FotosController.class);

	@Autowired
	private FotosService srv;
	
	
	
	@RequestMapping(value="/fotos", produces = {"text/html"}, method = RequestMethod.GET)
	public String download(HttpServletRequest req) throws Exception {

		String value = "hola download";		
			value = srv.download((String)req.getAttribute("user"));
      return value;
   }
	@RequestMapping(value="/fotos", consumes = {"multipart/form-data"},produces = {"text/html"}, method = RequestMethod.POST)
	public String upload(
			@RequestParam("file") MultipartFile file, 
			@RequestParam("title") String titulo,
			@RequestParam("description") String descripcion, 
			HttpServletRequest req) throws Exception {
	
		String user = null;
		String ext =  file.getOriginalFilename().split("\\.")[1];
		srv.upload(file.getInputStream(), titulo, descripcion, (String)req.getAttribute("user"), ext);

		
      return "La imagen '"+ titulo+ "' fue guardada";
   }
	
	@RequestMapping(value="/reset", produces = {"text/html"}, method = RequestMethod.GET)
	public String reset(HttpServletRequest req) {

		String value = srv.reset();
      return value;
   }
	
	@RequestMapping(value="/count", produces = {"text/html"}, method = RequestMethod.GET)
	public String count(HttpServletRequest req) throws Exception {
		String res = "";
		

		res = srv.count((String)req.getAttribute("user"));
		
		
      return res;
   }
	
}

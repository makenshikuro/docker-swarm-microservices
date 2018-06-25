package edu.uv.twcam.counters.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uv.twcam.counters.service.CountersService;

@RestController
public class CountersController{
   private static final Logger logger = LoggerFactory.getLogger(CountersController.class);

   @Autowired
   private CountersService srv;

	

   @RequestMapping(value="/increment/{key}", produces = {"text/html"})
	public String increment(@PathVariable String key, HttpServletRequest req) throws Exception {

		
		String value = null;
		
		value = srv.increment(key);
		
		
		logger.debug("increment " + key + " called - ");
      return value;
   }

   @RequestMapping(value="/get/{key}", produces = {"text/html"})
	public String get(@PathVariable String key, HttpServletRequest req) throws Exception {

		
		String value = null;
		
		value = srv.get(key);
		
		
      logger.debug("get "+ key +" called");
      
      return value;
   }

   @RequestMapping(value="/decrement/{key}", produces = {"text/html"})
	public String decrement(@PathVariable String key, HttpServletRequest req) throws Exception {

		
		String value = null;
		value = srv.decrement(key);
		
      logger.debug("decrement "+ key +" called");
      return value;
   }


}

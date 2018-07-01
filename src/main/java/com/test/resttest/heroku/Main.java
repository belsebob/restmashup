package com.test.resttest.heroku;

import javax.ws.rs.core.MediaType;

import com.restmashup.rest.InfoConsumer;
import com.restmashup.rest.InfoService;

/**
 * This class launches the web application in an embedded Jetty container. This is the entry point to your application. The Java
 * command that is used for launching should fire this main method.
 */
public class Main {

    public static void main(String[] args) throws Exception{
      InfoService infoService = new InfoService();
    
      
      System.out.println("main");

	
      infoService.SetupServer();
   
      
    }
    
}

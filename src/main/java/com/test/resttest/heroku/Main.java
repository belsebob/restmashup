package com.test.resttest.heroku;

import javax.ws.rs.core.MediaType;

import com.restmashup.rest.ArtistService;
import com.restmashup.rest.RunServer;

/**
 * This class launches the web application in an embedded Jetty container. This is the entry point to your application. The Java
 * command that is used for launching should fire this main method.
 */
public class Main {

    public static void main(String[] args) throws Exception{
      RunServer runServer = new RunServer(); 
      System.out.println("main");
      runServer.SetupServer();
   
    }
    
}

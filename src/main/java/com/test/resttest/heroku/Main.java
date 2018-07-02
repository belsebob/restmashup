package com.test.resttest.heroku;

import com.restmashup.rest.RunServer;

public class Main {

    public static void main(String[] args) throws Exception{
      RunServer runServer = new RunServer(); 
      System.out.println("main");
      runServer.SetupServer();
   
    }
    
}

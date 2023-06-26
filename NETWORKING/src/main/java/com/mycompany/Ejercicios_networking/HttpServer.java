/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Ejercicios_networking;

import com.mycompany.Ejercicios_networking.HttpServerController;
import java.net.*;
import java.io.*;
import java.nio.file.Files;

/**
 * Ejercicio en el cual se hace un test de la lectura de archivos desde el navegador
 *  tipos aceptados .html, .js,.css,.png,.jpg
 */
public class HttpServer {
    private static HttpServer _instance = new HttpServer();
    
    /**
     * Obtiene la instancia única del servidor HTTP.
     *
     * @return Instancia única del servidor HTTP.
     */
    public static HttpServer get_instance() {
        return _instance;
    }

    
    /**
     * Punto de entrada servidor HTTP.
     *
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws URISyntaxException Si ocurre un error en la sintaxis de la URI.
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean flags = true;

        while (flags) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            HttpServerController httpServerController = new HttpServerController();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            String path = "";
            Boolean primeraLinea = true;
            while ((inputLine = in.readLine()) != null) {
                if(primeraLinea){
                    path = inputLine.split(" ")[1];
                    URI resourse  = new URI(path);
                    System.out.println("Path : " + resourse.getPath());
                    primeraLinea = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            httpServerController.writingFile(clientSocket, path);
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
    private static int getPort(){
        if(System.getenv("PORT") !=null) return Integer.parseInt(System.getenv("PORT")) ;
        return 35000;
    }
}
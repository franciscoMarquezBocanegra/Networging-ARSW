/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Ejercicios_networking;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class URLReader {
    private static String console;
    private String reader;
    private URLConnection urlConnection;
    private URL url;

        /**
     * El método main es el punto de entrada del programa.
     * Solicita al usuario que ingrese una URL, crea un objeto URLReader y lee el contenido de la URL.
     * @throws Exception si ocurre algún error durante la ejecución.
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        console = scanner.nextLine();
        URLReader urlReader = new URLReader(console);
    }

    /**
     * Constructor con el parametro de la consola
     * @param console
     * @throws IOException
     */
    public URLReader(String console) throws IOException {
        checkingString(console);
    }

    /**
     * Verifica la cadena de entrada y crea una página web utilizando la URL correspondiente.
     * Si la cadena está vacía, se asigna una URL predeterminada "http://www.google.com"
     * @param console La cadena de entrada proporcionada por el usuario.
     * @throws IOException Si ocurre algún error al crear la página web.
     */
    public void checkingString(String console) throws IOException {
        if(console.isEmpty()){
            console = "http://www.google.com";
            url = new URL(console);
        }else{
            url = new URL(console);
        }
        createPage(url);
    }

    /**
     * Se crea la conexión con la pagina ingresada
     * @param url
     * @throws IOException
     */
    public void createPage(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        lectura(urlConnection);
    }

    
    /**
     * Lee el contenido de una URL utilizando la conexión proporcionada y escribe el contenido en un archivo HTML llamado "index.html".
     * @param urlConnection La conexión a la URL desde la que se leerá el contenido.
     */
    public void lectura(URLConnection urlConnection){
        //foundingHeader(urlConnection);
        System.out.println("Entra a lectura");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine = null;
            String htmlFile = "";
            while ((inputLine = reader.readLine()) != null) {
                //System.out.println(inputLine);
                htmlFile+=inputLine;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("index.html"));
            System.out.println("ESTE ES EL HTML \n" + htmlFile);
            writer.write(htmlFile);
            writer.close();
        } catch (IOException x) {
            System.err.println(x);
        }
    }
    
}
  
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Ejercicios_networking;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class HttpServerController {
    private List<String> tipoTexto;
    private List<String> tipoImg;

    private String file, fileType, outputLine;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * Constructor que se encarga de llenar las listas con los respectivos tipos de los archivos
     * EJ : .png, .html,.jss
     */
    public HttpServerController() {
        fillingLists();
    }


    /**
     * Llenando las listas
     */
    private void fillingLists() {
        tipoTexto = new ArrayList<String>();
        tipoImg = new ArrayList<String>();
        tipoTexto.add(".html");
        tipoTexto.add(".css");
        tipoTexto.add(".js");
        tipoImg.add(".jpg");
        tipoImg.add(".png");
    }

    /**
     * Escribe el contenido de un archivo en el socket del cliente.
     * @param clientSocket El socket del cliente.
     * @param path La ruta del archivo a escribir.
     * @throws IOException Si ocurre algún error durante la escritura en el socket.
     */
    public void writingFile(Socket clientSocket, String path) throws IOException {
        file = path;
        this.clientSocket = clientSocket;
        boolean flag = true;
        StringBuffer stringBuffer = new StringBuffer();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        for (String l : tipoTexto) {
            if (file.contains(l)) {
                fileType = file.split("\\.")[1];
                try {
                    creatingText(clientSocket);
                } catch (Exception e) {
                    stringBuffer = error404(stringBuffer);
                    System.out.println(e);
                }
                out.println();
                out.println(stringBuffer.toString());
            }
        }
        for (String i : tipoImg) {
            if (file.contains(i)) {
                fileType = file.split("\\.")[1];
                try{
                    creatingImg();
                }catch (Exception e){
                    stringBuffer = error404(stringBuffer);
                    System.out.println(e);
                }
            }
        }
    }

     /**
     * Crea y envía el contenido de un archivo de texto al socket del cliente.
     * @param clientSocket El socket del cliente.
     * @throws IOException Si ocurre algún error durante la lectura del archivo o la escritura en el socket.
     */
    public void creatingText(Socket clientSocket) throws IOException {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        outputLine = "HTTP/1.1 200 OK\r\n";
        outputLine += "Content-Type: text/" + fileType + "\r\n";
        outputLine += "\r\n";
        outputLine += new String(Files.readAllBytes(Paths.get("resources" + file)), StandardCharsets.UTF_8);
        out.println(outputLine);
        out.close();
        clientSocket.close();
    }

    /**
     * Crea y envía el contenido de un archivo de imagen al socket del cliente.
     */
    public void creatingImg() {
        try {
            DataOutputStream binary = new DataOutputStream(clientSocket.getOutputStream());
            File image = new File("resources" + file);
            FileInputStream filein = new FileInputStream(image);
            byte[] imageData = new byte[(int) image.length()];
            filein.read(imageData);
            filein.close();
            // Enviar la respuesta HTTP al cliente
            binary.writeBytes("HTTP/1.0 200 OK\r\n");
            binary.writeBytes("Content-Type: image/" + fileType + "\r\n");
            binary.writeBytes("Content-Length: " + imageData.length);
            binary.writeBytes("\r\n\r\n");
            binary.write(imageData);
            binary.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Error 404 lanzado si no se encuentra ninguna pagina.
     * @param stringBuffer
     * @return
     */
    public StringBuffer error404(StringBuffer stringBuffer) {

        outputLine = "HTTP/1.1 404 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Title of the document</title>\n" + "</head>"
                + "<body>"
                + "ERROR 404"
                + "</body>"
                + "</html>";
        stringBuffer = new StringBuffer();
        stringBuffer.append(outputLine);
        return stringBuffer;
    }

}
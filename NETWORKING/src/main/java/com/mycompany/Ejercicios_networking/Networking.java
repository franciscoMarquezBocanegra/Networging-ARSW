
package com.mycompany.Ejercicios_networking;

import java.net.MalformedURLException;
import java.net.URL;

/**

La clase Networking demuestra cómo utilizar la clase URL de Java para trabajar con URLs.

Proporciona información sobre diferentes componentes de una URL, como el puerto, el protocolo, el host, la autoridad, la ruta, la consulta, el archivo y la referencia.
*/
public class Networking {

    public static void main(String args[]){
        try {
            URL url = new URL("https://bodytech.com.co");
            System.out.println("Puerto "+ url.getPort());
            System.out.println("Protocolo "+ url.getProtocol());
            System.out.println("Host "+url.getHost());
            System.out.println("Authority "+url.getAuthority());
            System.out.println("Path "+url.getPath());
            System.out.println("Query "+url.getQuery());
            System.out.println("File" + url.getFile());
            System.out.println("Ref :" + url.getRef());

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
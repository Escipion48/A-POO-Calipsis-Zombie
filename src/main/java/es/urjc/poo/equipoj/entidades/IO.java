package es.urjc.poo.equipoj.entidades;

import java.io.FileWriter;
import java.io.IOException;

public class IO {


    public void escribir(String texto, String ruta) {
        try(FileWriter fw = new FileWriter("save"+ruta+".txt")) {
            fw.write(texto);
            System.out.println("Guardado exitoso");
        }
        catch(IOException e){
            System.out.println("Error en el guardado" + e.getMessage());
        }
    }

    //public Juego leer(String ruta){



    //}
    
    
    public Posicion posicionFromString(String texto) {
        String [] valores = texto.replace("posicion=", "").split("/");
        return new Posicion(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]));
    }
}

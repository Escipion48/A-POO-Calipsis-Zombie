package es.urjc.poo.equipoj.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.urjc.poo.equipoj.entidades.*;

import java.io.FileReader;
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

    public Casilla casillaFromString(String texto) {
        String [] valores = texto.replace("Casilla{", "").replace(" explorada=", "").replace("}","").split(",");
        boolean buscada;
        if(valores[1].toString().equals("false")){
            buscada = false;
        }
        else{
            buscada = true;
        }
        return new Casilla(posicionFromString(valores[0]),buscada);
    }

    public Zombie zombieFromString(String texto) {
        String [] valores = texto.replace("Zombie: ", "").replace("Tipo:","").split(" ");
        System.out.println(valores[1]);
        if(valores[3].equals("Normal")){
            return new Normal(Long.parseLong(valores[0]),tipoFromString(valores[2]),posicionFromString(valores[1]));
        }
        else if(valores[3].equals("Toxico")){
            return new Toxico(Long.parseLong(valores[0]),tipoFromString(valores[2]),posicionFromString(valores[1]));
        }
        else if(valores[3].equals("Berserker")){
            return new Berserker(Long.parseLong(valores[0]),tipoFromString(valores[2]),posicionFromString(valores[1]));
        }
        else{
            return new Normal(0,TipoZombie.CAMINANTE,new Posicion(0,0)); //Zombie con identificador 0 se producirá con una lectura erronea.
        }
    }

    public TipoZombie tipoFromString(String texto) {
        if(texto.equals("CAMINANTE")){
            return TipoZombie.CAMINANTE;
        }
        else if(texto.equals("CORREDOR")){
            return TipoZombie.CORREDOR;
        }
        else if (texto.equals("ABOMINACION")) {
            return TipoZombie.ABOMINACION;
        }
        return null; //Retornara un nulo si ha habido un error de lectura
    }








    ///Cargado y guardado con JSON ////////////////////////////////////////////////////////////////////////////////////////////////


    //Utilizo TypeAdapter que están programados ya que gson no es capaz de diferenciar entre las herencias de base, es decir,
    // necesita que se programen aparte para que sea capaz de leer y escribir los datos correctamente
    public void escribirJSON(Juego juego, String ruta) {


    Gson gson = new GsonBuilder().registerTypeAdapter(Zombie.class, new ZombieTypeAdapter()).registerTypeAdapter(Equipo.class, new EquipoTypeAdapter()).create();
    try(FileWriter fileWriter = new FileWriter("Save"+ruta+".json")){
        gson.toJson(juego,fileWriter);
        System.out.println("Guardado exitoso");
    }
    catch(IOException e){
        System.out.println("Error en el escribirJSON" + e.getMessage());
    }
    }


    public Juego leerJSON(String ruta) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Zombie.class, new ZombieTypeAdapter()).registerTypeAdapter(Equipo.class, new EquipoTypeAdapter()).create();
        try(FileReader fileReader = new FileReader("Save"+ruta+".json")){
            return gson.fromJson(fileReader,Juego.class);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package es.urjc.poo.equipoj.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.urjc.poo.equipoj.entidades.*;

import java.io.*;

public class IO {
    ///Cargado y guardado con JSON ////////////////////////////////////////////////////////////////////////////////////////////////
    //Utilizo TypeAdapter que están programados ya que gson no es capaz de diferenciar entre las herencias de base, es decir,
    // necesita que se programen aparte para que sea capaz de leer y escribir los datos correctamente
    static public void escribirJSON(Juego juego, String fichero) {
    Gson gson = new GsonBuilder().registerTypeAdapter(Zombie.class, new ZombieTypeAdapter()).registerTypeAdapter(Equipo.class, new EquipoTypeAdapter()).setExclusionStrategies(new exclusionOyenteSuperviviente()).create();
    try(FileWriter fileWriter = new FileWriter(fichero)){
        gson.toJson(juego,fileWriter);
        System.out.println("Guardado exitoso");
    }
    catch(IOException e){
        System.out.println("Error en el escribirJSON" + e.getMessage());
    }
    }
    static public Juego leerJSON(String fichero) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Zombie.class, new ZombieTypeAdapter()).registerTypeAdapter(Equipo.class, new EquipoTypeAdapter()).setExclusionStrategies(new exclusionOyenteSuperviviente()).create();
        try(FileReader fileReader = new FileReader(fichero)){
            return gson.fromJson(fileReader,Juego.class);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    ///Cargado y guardado Binario////////////////////////////////////////////

    public static void escribirBinario(Juego juego, String fichero) {
        try (FileOutputStream fileOut = new FileOutputStream(fichero);
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {

            objOut.writeObject(juego);
            System.out.println("Guardado exitoso");

        } catch (IOException e) {
            System.out.println("Error en el escribirBinario" + e.getMessage());
        }
    }

    public static Juego leerBinario(String fichero) {
        try (FileInputStream fileIn = new FileInputStream(fichero);
             ObjectInputStream objIn = new ObjectInputStream(fileIn)) {

            return  (Juego) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

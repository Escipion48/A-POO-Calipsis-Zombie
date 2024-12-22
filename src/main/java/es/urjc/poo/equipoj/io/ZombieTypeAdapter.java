package es.urjc.poo.equipoj.io;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import es.urjc.poo.equipoj.entidades.*;

import java.io.IOException;

public class ZombieTypeAdapter extends TypeAdapter<Zombie> {

    @Override
    public void write(JsonWriter jsonWriter, Zombie zombie) throws IOException {
    jsonWriter.beginObject();


    //campo auxiliar y necesario para almacenar la clase de zombie, ya que si no lo utilizara no guararia la subclase del zombie
    jsonWriter.name("Clase").value(zombie.getClass().getSimpleName());


    //Guardamos el resto de valores
    jsonWriter.name("identificador").value(zombie.getIdentificador());
    jsonWriter.name("tipo").value(zombie.getTipo().toString());

    jsonWriter.name("posicion");
    jsonWriter.beginObject();
    jsonWriter.name("x").value(zombie.getPosicion().getPosicionX());
    jsonWriter.name("y").value(zombie.getPosicion().getPosicionY());
    jsonWriter.endObject();

    jsonWriter.endObject();
    }

    @Override
    public Zombie read(JsonReader jsonReader) throws IOException {
        Zombie zombie = null;
        String clase = null;
        long identificador = 0;
        TipoZombie tipo = null;
        Posicion posicion = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();

            switch (name) {
                case "Clase":
                    clase = jsonReader.nextString();  // Leemos el nombre de la clase
                    break;
                case "identificador":
                    identificador = jsonReader.nextLong();
                    break;
                case "tipo":
                    tipo = TipoZombie.valueOf(jsonReader.nextString());
                    break;
                case "posicion":
                    jsonReader.beginObject();
                    int x = 0, y = 0;
                    while (jsonReader.hasNext()) {
                        String posName = jsonReader.nextName();
                        if ("x".equals(posName)) {
                            x = jsonReader.nextInt();
                        } else if ("y".equals(posName)) {
                            y = jsonReader.nextInt();
                        }
                    }
                    jsonReader.endObject();
                    posicion = new Posicion(x, y);
                    break;
            }
        }

        jsonReader.endObject();

        // Crear el zombie adecuado basado en el campo "Clase"
        if ("Normal".equals(clase)) {
            zombie = new Normal(identificador, tipo, posicion);
        } else if ("Toxico".equals(clase)) {
            zombie = new Toxico(identificador, tipo, posicion);
        } else if ("Berserker".equals(clase)) {
            zombie = new Berserker(identificador, tipo, posicion);
        }

        return zombie;
    }
}

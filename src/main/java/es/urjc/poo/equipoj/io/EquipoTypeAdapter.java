package es.urjc.poo.equipoj.io;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import es.urjc.poo.equipoj.entidades.Arma;
import es.urjc.poo.equipoj.entidades.Equipo;
import es.urjc.poo.equipoj.entidades.Provision;

import java.io.IOException;

public class EquipoTypeAdapter extends TypeAdapter<Equipo> {
    @Override
    public void write(JsonWriter jsonWriter, Equipo equipo) throws IOException {

        //Antes comprobamos si es un nulo o no(caso en el que el espacio del inventario este vacio)
        if(equipo == null) {
            jsonWriter.nullValue();
            return;
        }

        jsonWriter.beginObject();

        jsonWriter.name("clase").value(equipo.getClass().getSimpleName());
        jsonWriter.name("nombre").value(equipo.getNombre());

        //En caso de que sea un Arma
        if(equipo instanceof Arma){
        jsonWriter.name("potencia").value(((Arma) equipo).getPotencia());
        jsonWriter.name("alcance").value(((Arma) equipo).getAlcance());
        jsonWriter.name("numeroDados").value(((Arma) equipo).getNumeroDados());
        jsonWriter.name("valorExito").value(((Arma) equipo).getValorExito());
        }

        //En caso de que sea una provision
        else if(equipo instanceof Provision){
        jsonWriter.name("kcal").value(((Provision) equipo).getKcal());
        jsonWriter.name("Fecha").beginArray();
        for(int i = 0; i<3;i++){
            jsonWriter.value(((Provision) equipo).getCaducidad(i));
        }
        jsonWriter.endArray();
        jsonWriter.name("tipo").value(((Provision) equipo).getTipo());
        }

        //Se cierra la escritura del objeto
        jsonWriter.endObject();
    }

    @Override
    public Equipo read(JsonReader jsonReader) throws IOException {

        //Antes de nada comprobamos si el inventario esta vacío, en tal caso será un null
        //Para eso realizamos esta comprobación y nos saltamos el resto del código.
        if(jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull(); //Consume el null
            return null; //Retorna un null y lo almacena en el inventario
        }
        //Variables Generales
        String clase = null;
        String nombre = null;

        //Variables de la clase arma
        int potencia = 0;
        int alcance = 0;
        int numeroDados = 0;
        int valorExito = 0;

        //variables de la clase provision
        int kcal = 0;
        int[] fechaCaducidad = new int[3];
        Boolean tipo = false;


        //Guardamos los objetos comunos de ambas clases y vemos si es Provision u Arma y invocamos a un private dependiendo de la clase
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            switch (name) {
                case "clase":
                    clase = jsonReader.nextString();
                    break;
                case "nombre":
                    nombre = jsonReader.nextString();
                    break;
                case "potencia":
                    potencia = jsonReader.nextInt();
                    break;
                case "alcance":
                    alcance = jsonReader.nextInt();
                    break;
                case "numeroDados":
                    numeroDados = jsonReader.nextInt();
                    break;
                case "valorExito":
                    valorExito = jsonReader.nextInt();
                    break;
                case "kcal":
                    kcal = jsonReader.nextInt();
                    break;
                case "Fecha":
                    jsonReader.beginArray();
                    for (int i = 0; i < 3; i++) {
                        fechaCaducidad[i] = jsonReader.nextInt();
                    }
                    jsonReader.endArray();
                    break;
                case "tipo":
                    tipo = jsonReader.nextBoolean();
                    break;
            }
        }
        jsonReader.endObject();

        //Selector según la clase
        if ("Arma".equals(clase)) {
        return new Arma(nombre, potencia, alcance, numeroDados, valorExito);
        } else if ("Provision".equals(clase)) {
        return new Provision(nombre, kcal, fechaCaducidad, tipo);
        }

        return null; // EN caso de que no sea ni un tipo ni otro devolvemos un null
    }
}

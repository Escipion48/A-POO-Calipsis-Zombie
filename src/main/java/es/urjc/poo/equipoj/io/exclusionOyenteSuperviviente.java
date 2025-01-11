package es.urjc.poo.equipoj.io;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import es.urjc.poo.equipoj.entidades.*;

import java.io.IOException;

public class exclusionOyenteSuperviviente implements ExclusionStrategy {

    //Utilizaremos este metodo para que el Gson se salte los campos que empiecen por
    //javax.swing , es decir para que no tenga en cuenta el campo de superviviente donde tenemos el oyente
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getName().equals("support");
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}

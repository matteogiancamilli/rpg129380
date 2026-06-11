package it.unicam.cs.mpgc.rpg129380.model.oggetti;

import com.google.gson.*;
import java.lang.reflect.Type;

public class OggettoAdapter implements JsonDeserializer<OggettoDati> {

    @Override
    public OggettoDati deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Estrae il nodo contenente il nome completo della classe Java
        JsonElement classeElement = jsonObject.get("classeJava");
        if (classeElement == null) {
            throw new JsonParseException("Campo 'classeJava' mancante nella configurazione JSON dell'oggetto");
        }

        String className = classeElement.getAsString();

        try {
            // Sfrutta la Reflection di Java per caricare dinamicamente la classe a runtime
            Class<?> clazz = Class.forName(className);

            // Richiede a GSON di mappare i restanti campi generici o specifici sulla classe individuata
            return context.deserialize(jsonObject, clazz);

        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Errore di Reflection: impossibile trovare la classe specificata -> " + className, e);
        }
    }
}
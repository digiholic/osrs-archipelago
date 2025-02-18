package dev.koifysh.archipelago.events;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

public class RetrievedEvent implements Event {

    public HashMap<String, Object> data;
    private final int requestID;
    private final JsonObject jsonValue;

    private final Gson gson;

    public RetrievedEvent(HashMap<String, Object> keys, JsonObject jsonValue, int requestID, Gson gson) {
        data = keys;
        this.jsonValue = jsonValue;
        this.requestID = requestID;
        this.gson = gson;
    }

    public int getInt(String key) {
        return (Integer) data.get(key);
    }

    public float getFloat(String key) {
        return (Float) data.get(key);
    }

    public double getDouble(String key) {
        return (Double) data.get(key);
    }

    public String getString(String key) {
        return (String) data.get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean) data.get(key);
    }

    public Object getObject(String key) {
        return data.get(key);
    }

    public boolean containsKey(String key) {
        return data.containsKey(key);
    }

    public <T> T getValueAsObject(String key, Class<T> classOfT) {
        Object value = gson.fromJson(jsonValue.get(key), classOfT);
        return Primitives.wrap(classOfT).cast(value);
    }

    public <T> T getValueAsObject(String key, TypeToken<T> typeOfT) {
        return jsonValue == null ? null : gson.fromJson(new JsonTreeReader(jsonValue.get(key)), typeOfT.getType());
    }

    public int getRequestID() {
        return requestID;
    }
}
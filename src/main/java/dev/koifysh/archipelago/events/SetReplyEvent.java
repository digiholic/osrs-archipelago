package dev.koifysh.archipelago.events;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.reflect.TypeToken;

public class SetReplyEvent implements Event {
    @SerializedName("key")
    public String key;
    @SerializedName("value")
    public Object value;
    @SerializedName("original_value")
    public Object original_value;

    private final int requestID;

    private final JsonElement jsonValue;
    private final Gson gson;

    public SetReplyEvent(String key, Object value, Object original_value, JsonElement jsonValue, int requestID, Gson gson) {
        this.key = key;
        this.value = value;
        this.original_value = original_value;
        this.jsonValue = jsonValue;
        this.requestID = requestID;
        this.gson = gson;
    }

    public <T> T getValueAsObject(Class<T> classOfT) {
        Object value = gson.fromJson(jsonValue, classOfT);
        return Primitives.wrap(classOfT).cast(value);
    }

    public <T> T getValueAsObject(TypeToken<T> typeOfT) {
        return jsonValue == null ? null : gson.fromJson(new JsonTreeReader(jsonValue), typeOfT.getType());
    }

    public int getRequestID() {
        return requestID;
    }
}

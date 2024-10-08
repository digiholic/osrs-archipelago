package dev.koifysh.archipelago.events;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.Primitives;
import dev.koifysh.archipelago.network.ConnectionResult;

public class ConnectionResultEvent implements Event {

    private final int team;
    private final int slot;
    private final String seedName;
    private final ConnectionResult result;
    private final JsonElement slot_data;
    private final Gson gson;

    public ConnectionResultEvent(ConnectionResult result, Gson gson) {
        this(result,0,0,null,null, gson);
    }

    public ConnectionResultEvent(ConnectionResult result, int team, int slot, String seedName, JsonElement slot_data, Gson gson) {
        this.result = result;
        this.team = team;
        this.slot = slot;
        this.seedName = seedName;
        this.slot_data = slot_data;
        this.gson = gson;
    }

    public int getTeam() {
        return team;
    }

    public int getSlot() {
        return slot;
    }

    public String getSeedName() {
        return seedName;
    }

    public ConnectionResult getResult() {
        return result;
    }

    public <T> T getSlotData(Class<T> classOfT) {
        Object data = gson.fromJson(slot_data,classOfT);
        return Primitives.wrap(classOfT).cast(data);
    }
}

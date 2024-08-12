package gg.archipelago;

import java.io.Serializable;

public class DataPackage implements Serializable {
    public long lastItemReceivedIndex = -1;
    public long characterHash = 0;
}

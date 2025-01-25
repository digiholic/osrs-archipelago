package gg.archipelago;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class DataPackage {
    public long lastItemReceivedIndex = -1;
    public String slotName = "";
    public String seed = "";
    public List<Long> claimedCarePacks = new ArrayList<Long>();
}

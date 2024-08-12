package gg.archipelago.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@Data
public class LocationData{
    private String locationName;
    private String category;
    private String[] regionsRequired;
    private String[] skillsRequired;
    private String[] itemsRequired;
    private String qpRequired;
    private String pluginTaskType;
    private List<String> pluginTaskArgs = new ArrayList<String>();

    public LocationData(List<String> csvData){
        locationName = csvData.get(0);
        category = csvData.get(1);
        regionsRequired = csvData.get(2).split(",");
        skillsRequired = csvData.get(3).split(",");
        itemsRequired = csvData.get(4).split(",");
        qpRequired = csvData.get(5);
        pluginTaskType = csvData.get(6);
        if (csvData.size() > 7)
            pluginTaskArgs = csvData.subList(7, csvData.size());
    }
}
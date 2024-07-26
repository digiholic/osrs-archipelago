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
    private String qpRequired;
    private String pluginTaskType;
    private List<String> pluginTaskArgs = new ArrayList<String>();

    public LocationData(List<String> csvData){
        locationName = csvData.get(0);
        category = csvData.get(1);
        regionsRequired = csvData.get(2).split(",");
        skillsRequired = csvData.get(3).split(",");
        qpRequired = csvData.get(4);
        pluginTaskType = csvData.get(5);
        if (csvData.size() > 6)
            pluginTaskArgs = csvData.subList(6, csvData.size()-1);
    }
}
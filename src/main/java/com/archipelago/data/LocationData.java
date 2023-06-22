package com.archipelago.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@Data
@AllArgsConstructor
public class LocationData {
    public Long id;
    public String name;
    public int icon_id;
    public int icon_file;
    public boolean display_in_panel;
}
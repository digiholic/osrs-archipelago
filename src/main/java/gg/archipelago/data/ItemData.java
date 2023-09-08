package gg.archipelago.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemData {
    public long id;
    public String name;
    public int icon_id;
    public int icon_file;
}

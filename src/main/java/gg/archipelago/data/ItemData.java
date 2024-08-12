package gg.archipelago.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.runelite.api.SpriteID;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemData {
    public long id;
    public String name;
    public int icon_id;
    public int icon_file;
    public String itemType;
}

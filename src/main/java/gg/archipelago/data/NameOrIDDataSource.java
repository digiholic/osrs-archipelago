package gg.archipelago.data;

import java.util.regex.Pattern;

/**
 * Class for containing data parsed from the CSV document that can be either an ID or a name, for legacy
 * compatibility purposes.
 *
 * Create it with the raw string from the CSV. If it's a number, it'll populate that value.
 */
public class NameOrIDDataSource {
    public boolean isID;
    public int idValue;
    public String nameValue;
    public NameOrIDDataSource(String data){
        if (isNumeric(data)){
            isID = true;
            idValue = Integer.parseInt(data);
        } else {
            isID = false;
            nameValue = data;
        }
    }
    public NameOrIDDataSource(int data){
        isID = true;
        idValue = data;
    }

    private final Pattern integerPattern = Pattern.compile("^[+-]?[1-9][0-9]*|0$ or ^[+-]?[1-9]\\d*|0$");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return integerPattern.matcher(strNum).matches();
    }
}

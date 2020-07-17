package GraphicalView;

import java.util.ArrayList;

public class CategoryInTable {
    private String name;
    private ArrayList<String> specialProperties;

    public CategoryInTable(String name, ArrayList<String> specialProperties) {
        this.name = name;
        this.specialProperties = specialProperties;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSpecialProperties() {
        return specialProperties;
    }
}

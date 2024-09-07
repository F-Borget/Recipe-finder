package Model;

public class Selection {
    private int id_selection;
    private String name_selection;
    private String description_selection;

    public Selection(int id_selection, String name_selection, String description_selection) {
        this.id_selection = id_selection;
        this.name_selection = name_selection;
        this.description_selection = description_selection;
    }

    public int getId_selection() {
        return id_selection;
    }

    public void setId_selection(int id_selection) {
        this.id_selection = id_selection;
    }

    public String getName_selection() {
        return name_selection;
    }

    public void setName_selection(String name_selection) {
        this.name_selection = name_selection;
    }

    public String getDescription_selection() {
        return description_selection;
    }

    public void setDescription_selection(String description_selection) {
        this.description_selection = description_selection;
    }

    @Override
    public String toString() {
        return "Selection{" +
                "id_selection=" + id_selection +
                ", name_selection='" + name_selection + '\'' +
                ", description_selection='" + description_selection + '\'' +
                '}';
    }
}

package Model;

public class EquipmentAPI {
    private int id;
    private String name;
    private String image;
    private TemperatureAPI temperature;

    // Constructors, Getters, and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public TemperatureAPI getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureAPI temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "EquipmentAPI{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", temperature=" + temperature +
                '}';
    }
}

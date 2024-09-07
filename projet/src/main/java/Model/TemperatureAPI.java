package Model;

public class TemperatureAPI {
    private double number;
    private String unit;

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "TemperatureAPI{" +
                "number=" + number +
                ", unit='" + unit + '\'' +
                '}';
    }
}


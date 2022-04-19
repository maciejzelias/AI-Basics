import java.util.Vector;

public class Centroid {
    Vector<Double> values;

    public Centroid(Vector<Double> doubleVector) {
        this.values = doubleVector;
    }

    public void setValues(Vector<Double> values) {
        this.values = values;
    }

    public String toString() {
        return values.toString();
    }

    public Vector<Double> getValues() {
        return values;
    }
}
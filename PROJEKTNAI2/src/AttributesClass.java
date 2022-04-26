import java.util.Vector;

public class AttributesClass {
    Vector<Double> values = new Vector<>();
    String decisionValue;

    public AttributesClass(Vector<Double> doublesVector, String decisionValue) {
        values = doublesVector;
        this.decisionValue = decisionValue;
    }

    public Vector<Double> getValues() {
        return values;
    }

    public String getDecisionValue() {
        return decisionValue;
    }
}

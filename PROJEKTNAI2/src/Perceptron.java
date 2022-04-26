import java.util.Vector;

public class Perceptron {
    Vector<Double> weights;
    Double thetha;
    Double alpha;
    String decision;

    Perceptron(int length, double alpha) {
        weights = new Vector<>();
        //randomizing Weights and Theta
        thetha = (Math.random() * 2) + 1;
        for (int i = 0; i < length; i++) {
            weights.add((Math.random() * 10) -5);
            while (weights.get(i) == 0.00) {
                weights.set(i, (Math.random() * 2) - 1);
            }
        }
        this.alpha = alpha;
        this.decision = "NULL";
    }

    public int getExit(Vector<Double> entries) {
        double net = 0.00;
        for (int i = 0; i < entries.size() ; i++) {
            net += (entries.get(i) * weights.get(i));
        }
        if (net >= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public Double getNet(Vector<Double> entries) {
        double net = thetha;
        for (int i = 0; i < entries.size(); i++) {
            net += (entries.get(i) * weights.get(i));
        }
        return net;
    }

    public void trainPercepton(int result, Vector<Double> entries) {
        int exit;
        while (((exit = getExit(entries)) != result)) {
            for (int i = 0; i < entries.size(); i++) {
                weights.set(i, (weights.get(i) + (alpha * entries.get(i) * (result - exit))));
            }
            thetha = weights.get(weights.size()-1);
        }
    }

    public Vector<Double> getWeights() {
        return weights;
    }


    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Double getThetha() {
        return thetha;
    }
}

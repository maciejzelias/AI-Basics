import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        /**
         * Interacting with user
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj liczbe k : ");
        int k = scanner.nextInt();

        /**
         * Putting training data to vector
         */
        File file = new File("iris_training.txt");
        Vector<Point> allPoints = new Vector<>();
        Vector<Point> centroids = new Vector<>();
        Map<Integer, Vector<Point>> Groups = new TreeMap<>();

        for (int i = 1; i <= k; i++) {
            Groups.put(i, new Vector<Point>());
        }

        try {
            String line;
            Scanner fileScanner = new Scanner(file);
            for (int b = 1; b < 121; b++) {
                line = fileScanner.nextLine();
                line = line.replaceAll(",", ".");
                String[] attributes = line.split("\\s+");
                Vector<Double> normalAttributes = new Vector<>();
                for (int i = 1; i < attributes.length - 1; i++) {
                    normalAttributes.add(Double.parseDouble(attributes[i]));
                }
                allPoints.add(new Point(normalAttributes, k));
                Groups.get(allPoints.get(b - 1).getGroupNumber()).add(allPoints.get(b - 1));

            }
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        }

        for (Map.Entry<Integer, Vector<Point>> entry : Groups.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().toString());
        }

    }

    public static Double countingDistance(Vector<Double> vector, Vector<Double> vector2) {
        double value = 0.0;
        if (vector.size() != vector2.size()) {
            System.out.println("DANE TESTOWE I TRENINGOWE SIE ROZNIA");
            return null;
        }
        for (int i = 0; i < vector.size(); i++) {
            value += Math.pow((vector.get(i) - vector2.get(i)), 2);
        }
        return Math.sqrt(value);
    }

    public static String randomDecision(Vector<String> vector) {
        if (vector.size() < 1) {
            return null;
        } else {
            int randomIndex = (int) (Math.random() * vector.size());
            return vector.get(randomIndex);
        }
    }

    public static int indexOfSmallestValue(Vector<Double> vec) {
        Double min = 100.0;
        int minimalIndex = 0;
        for (int i = 0; i < vec.size(); i++) {
            if (vec.get(i) < min && vec.get(i) != -1.00) {
                min = vec.get(i);
                minimalIndex = i;
            }
        }
        return minimalIndex;
    }
}

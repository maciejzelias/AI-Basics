import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        /**
         * Putting training data to vector of
         * AttributesClass objects
         */
        File file = new File("iris_training.txt");
        Vector<AttributesClass> trainedValues = new Vector<>();
        try {
            String line;
            Scanner fileScanner = new Scanner(file);
            for (int b = 1; b < 121; b++) {
                line = fileScanner.nextLine();
                line = line.replaceAll(",", ".");
                String[] attributes = line.split("\\s+");
                String decisionAtribute = attributes[attributes.length - 1];
                Vector<Double> normalAttributes = new Vector<>();
                for (int i = 1; i < attributes.length - 1; i++) {
                    normalAttributes.add(Double.parseDouble(attributes[i]));
                }
                trainedValues.add(new AttributesClass(normalAttributes, decisionAtribute));
            }
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        }
        /**
         * Interacting with user
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj liczbe k : ");
        int k = scanner.nextInt();

        /**
         * Counting positivly classifed lines from testing file
         * and accuracy of experiment
         */
        int wellClasified = 0;
        int howMany = 0;
        try {
            String line;
            Scanner testingScanner = new Scanner(new File("iris_test.txt"));
            while (testingScanner.hasNextLine()) {
                line = testingScanner.nextLine();
                line = line.replaceAll(",", ".");
                String[] attributes = line.split("\\s+");
                String decisionAtribute = attributes[attributes.length - 1];
                Vector<Double> normalAttributes = new Vector<>();
                for (int i = 1; i < attributes.length - 1; i++) {
                    normalAttributes.add(Double.parseDouble(attributes[i]));
                }
                Vector<Double> distances = new Vector<>();
                for (AttributesClass ac : trainedValues) {
                    distances.add(countingDistance(normalAttributes, ac.getValues()));
                }
                if (checkingClassification(distances, decisionAtribute, trainedValues, k)) {
                    wellClasified++;
                }
                howMany++;
            }

            System.out.println("Liczba prawidlowo zaklasyfkiowanych przykladow : " + wellClasified);
            System.out.println("Dokladnosc eksperymentu wynosi : " + Double.valueOf(wellClasified) / Double.valueOf(howMany) * 100 + " %");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        String line;
        while (true) {
            System.out.println("PODAJ WEKTOR ATRYBUTOW W FORMACIE [A.BC D.EF G.HI...]");
            try {
                line = br.readLine();
                String[] attributes = line.split("\\s+");
                Vector<Double> normalAttributes = new Vector<>();
                for (String attribute : attributes) {
                    normalAttributes.add(Double.parseDouble(attribute));
                }
                if (normalAttributes.size() != trainedValues.get(0).getValues().size()) {
                    System.out.println("BLEDNA ILOSC ZMIENNYCH");
                } else {
                    Vector<Double> distances = new Vector<>();
                    for (AttributesClass ac : trainedValues) {
                        distances.add(countingDistance(normalAttributes, ac.getValues()));
                    }
                    System.out.println("Klasyfikacja dla podanego wektora :  " + creatingClassification(distances, trainedValues, k));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public static String creatingClassification(Vector<Double> distanceVector, Vector<AttributesClass> trainedVal, int k) {
        int[] indexes = new int[k];
        Map<String, Integer> occurencesMap = new TreeMap<>();
        Vector<String> random = new Vector<>();
        for (int i = 0; i < k; i++) {
            indexes[i] = indexOfSmallestValue(distanceVector);
            distanceVector.set(indexes[i], -1.00);
            String decisionAttribute = trainedVal.get(indexes[i]).getDecisionValue();
            occurencesMap.put(decisionAttribute, occurencesMap.getOrDefault(decisionAttribute, 0) + 1);
        }
        int maxValueInMap = Collections.max(occurencesMap.values());
        for (Map.Entry<String, Integer> entry : occurencesMap.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                random.add(entry.getKey());
            }
        }
        return randomDecision(random);
    }

    public static boolean checkingClassification(Vector<Double> distanceVector, String decision, Vector<AttributesClass> trainedVal, int k) {
        int[] indexes = new int[k];
        Map<String, Integer> occurencesMap = new TreeMap<>();
        Vector<String> random = new Vector<>();
        for (int i = 0; i < k; i++) {
            indexes[i] = indexOfSmallestValue(distanceVector);
            distanceVector.set(indexes[i], -1.00);
            String decisionAttribute = trainedVal.get(indexes[i]).getDecisionValue();
            occurencesMap.put(decisionAttribute, occurencesMap.getOrDefault(decisionAttribute, 0) + 1);
        }
        int maxValueInMap = Collections.max(occurencesMap.values());
        for (Map.Entry<String, Integer> entry : occurencesMap.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                random.add(entry.getKey());
            }
        }
        if (occurencesMap.get(decision) == null) {
            return false;
        } else if (decision.equals(randomDecision(random))) {
            return true;
        }
        return false;
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

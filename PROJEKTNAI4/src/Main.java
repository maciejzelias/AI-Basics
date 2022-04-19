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
        if (k > 120) {
            System.out.println("ZBYT DUZA LICZBA K");
            System.exit(0);
        }

        /**
         * Putting training data to vector
         */
        File file = new File("iris_training.txt");
        Vector<Point> allPoints = new Vector<>();
        Vector<Centroid> centroids = new Vector<>();
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
                if (b <= k) {
                    Point newPoint = new Point(normalAttributes);
                    newPoint.setGroupNumber(b);
                    allPoints.add(newPoint);
                } else {
                    allPoints.add(new Point(normalAttributes, k));
                }
                Groups.get(allPoints.get(b - 1).getGroupNumber()).add(allPoints.get(b - 1));
            }
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        }


        for (Map.Entry<Integer, Vector<Point>> entry : Groups.entrySet()) {
        //    System.out.println("[" + entry.getKey() + "]    " + entry.getValue());
            centroids.add(new Centroid(getCentroids(entry.getValue())));
        }

        int changes = 1;
        while (changes >= 1) {
            changes = 0;
            for (Point point : allPoints) {
                int newGroup = getNewGroup(point.getValues(), centroids);
                if (point.getGroupNumber() != newGroup) {
                    Groups.get(point.getGroupNumber()).remove(point);
                    Groups.get(newGroup).add(point);
                    point.setGroupNumber(newGroup);
                    changes++;
                }
            }
            System.out.println("======================================================");
            Double suma = 0.0;
            for (Map.Entry<Integer, Vector<Point>> entry : Groups.entrySet()) {
                centroids.get(entry.getKey() - 1).setValues(getCentroids(entry.getValue()));
                System.out.println("GRUPA " + entry.getKey() + " :" + Sum(entry.getValue(), centroids.get(entry.getKey() - 1)));
                suma += Sum(entry.getValue(), centroids.get(entry.getKey() - 1));
            }
            System.out.println("RAZEM : " + suma);
        }

        System.out.println("===================");
        System.out.println("===================");
        System.out.println("===================");
        for (Map.Entry<Integer, Vector<Point>> entry : Groups.entrySet()) {
            System.out.println("GRUPA  " + entry.getKey() + " : " + entry.getValue());
        }


    }

    public static Double Sum(Vector<Point> points, Centroid centroid) {
        double sum = 0.0;
        for (Point point : points) {
            sum += countingDistanceSquare(point.getValues(), centroid.getValues());
        }
        return sum;
    }

    public static Vector<Double> getCentroids(Vector<Point> points) {
        Vector<Double> centroid = new Vector<>();
        double a = 0;
        double b = 0;
        double c = 0;
        double d = 0;
        double size = Double.valueOf(points.size());
        for (Point point : points) {
            a += point.getValues().get(0);
            b += point.getValues().get(1);
            c += point.getValues().get(2);
            d += point.getValues().get(3);
        }
        centroid.add(a / size);
        centroid.add(b / size);
        centroid.add(c / size);
        centroid.add(d / size);

        return centroid;
    }

    public static int getNewGroup(Vector<Double> pointVector, Vector<Centroid> centroidVector) {
        int index = 0;
        double min = 10000;
        for (int i = 0; i < centroidVector.size(); i++) {
            if (countingDistance(pointVector, centroidVector.get(i).getValues()) < min) {
                min = countingDistance(pointVector, centroidVector.get(i).getValues());
                index = i;
            }
        }
        return index + 1;
    }

    public static Double countingDistanceSquare(Vector<Double> vector, Vector<Double> vector2) {
        double value = 0.0;
        for (int i = 0; i < vector.size(); i++) {
            value += Math.pow((vector.get(i) - vector2.get(i)), 2);
        }
        return value;
    }

    public static Double countingDistance(Vector<Double> vector, Vector<Double> vector2) {
        double value = 0.0;
        for (int i = 0; i < vector.size(); i++) {
            value += Math.pow((vector.get(i) - vector2.get(i)), 2);
        }
        return Math.sqrt(value);
    }
}

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
                normalAttributes.add(-1.00);
                trainedValues.add(new AttributesClass(normalAttributes, decisionAtribute));
            }
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        }

        /**
         * training perceptions
         */
        Double alpha = 0.5;
        int length = trainedValues.get(0).getValues().size();
        Perceptron setosaPercepton = new Perceptron(length, alpha);
        setosaPercepton.setDecision("Iris-setosa");
        Perceptron versicolorPercepton = new Perceptron(length, alpha);
        versicolorPercepton.setDecision("Iris-versicolor");
        Perceptron virginicaPercepton = new Perceptron(length, alpha);
        virginicaPercepton.setDecision("Iris-virginica");
        int b = 1;

        while (b < 6) {
            for (AttributesClass ac : trainedValues) {
                if (ac.getDecisionValue().equals("Iris-setosa")) {
                    setosaPercepton.trainPercepton(1, ac.getValues());
                    //               versicolorPercepton.trainPercepton(0, ac.getValues());
                    //              virginicaPercepton.trainPercepton(0, ac.getValues());
                }
                if (ac.getDecisionValue().equals("Iris-versicolor")) {
                    setosaPercepton.trainPercepton(0, ac.getValues());
                    //               versicolorPercepton.trainPercepton(1, ac.getValues());
                    //               virginicaPercepton.trainPercepton(0, ac.getValues());
                }
                if (ac.getDecisionValue().equals("Iris-virginica")) {
                    setosaPercepton.trainPercepton(0, ac.getValues());
                    //               versicolorPercepton.trainPercepton(0, ac.getValues());
                    //               virginicaPercepton.trainPercepton(1, ac.getValues());
                }
            }
            b++;
        }

        System.out.println("setosa : " + setosaPercepton.getWeights());
        //   System.out.println("versi : " + versicolorPercepton.getWeights() + " " + versicolorPercepton.getThetha());
        //   System.out.println("virginica : " + virginicaPercepton.getWeights() + " " + virginicaPercepton.getThetha());


        /**
         * Counting positivly classifed lines from testing file
         * and accuracy of experiment
         */
        int setosaResult = 2;
        int versicolorResult = 2, virginicaResult = 2;
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
                normalAttributes.add(-1.00);
                setosaResult = setosaPercepton.getExit(normalAttributes);
                versicolorResult = versicolorPercepton.getExit(normalAttributes);
                virginicaResult = virginicaPercepton.getExit(normalAttributes);

                System.out.println(setosaResult /*+ " " + versicolorResult + " " + virginicaResult*/);
                String finalDecision;
                /*if (setosaResult + versicolorResult + virginicaResult > 1) {
                    if (setosaResult + versicolorResult + virginicaResult > 2) {
                        finalDecision = "BLAD";
                        System.out.println("KAZDY PERCEPTRON DAL WYJSCIE 1 !!!");
                    } else {
                        Vector<String> decisions = new Vector<>();
                        if (setosaResult == 1) {
                            decisions.add(setosaPercepton.getDecision());
                        }
                        if (versicolorResult == 1) {
                            decisions.add(versicolorPercepton.getDecision());
                        }
                        if (virginicaResult == 1) {
                            decisions.add(virginicaPercepton.getDecision());
                        }
                        int random = (int) (Math.random() * 100) + 1;
                        if (random >= 51) {
                            finalDecision = decisions.get(1);
                        } else {
                            finalDecision = decisions.get(0);
                        }
                    }
                } else {
                    if (setosaResult == 1) {
                        finalDecision = setosaPercepton.getDecision();
                    } else if (versicolorResult == 1) {
                        finalDecision = versicolorPercepton.getDecision();
                    } else if (virginicaResult == 1) {
                        finalDecision = virginicaPercepton.getDecision();
                    } else {
                        finalDecision = "zaden perceptron nie zaklasyfikowal kwiatka";
                    }
                }*/

                if (setosaResult == 1) {
                    finalDecision = setosaPercepton.getDecision();
                    if (finalDecision.equals(decisionAtribute)) {
                        wellClasified++;
                    }
                } else {
                    if (!decisionAtribute.equals(setosaPercepton.getDecision())) {
                        wellClasified++;
                    }
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
                normalAttributes.add(-1.00);
                String finalDecision = "123";
                if (normalAttributes.size() != trainedValues.get(0).getValues().size()) {
                    System.out.println("BLEDNA ILOSC ZMIENNYCH");
                } else {
                    setosaResult = setosaPercepton.getExit(normalAttributes);
                    if (setosaResult == 1) {
                        System.out.println("DECISION : " + setosaPercepton.getDecision());
                    } else {
                        int random = (int) (Math.random() * 100) + 1;
                        if (random >= 51) {
                            finalDecision = virginicaPercepton.getDecision();
                        } else {
                            finalDecision = versicolorPercepton.getDecision();
                        }
                        System.out.println("DECISION : NO-SETOSA,   WYLOSOWANO : " + finalDecision);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

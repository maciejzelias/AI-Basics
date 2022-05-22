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

        Set<Double> firstAttribute = new HashSet<>();
        Set<Double> secondAttribute = new HashSet<>();
        Set<Double> thirdAttribute = new HashSet<>();
        Set<Double> fourthAttribute = new HashSet<>();

        for (AttributesClass attributesClass : trainedValues) {
            firstAttribute.add(attributesClass.getValues().get(0));
            secondAttribute.add(attributesClass.getValues().get(1));
            thirdAttribute.add(attributesClass.getValues().get(2));
            fourthAttribute.add(attributesClass.getValues().get(3));
        }
        Vector<Double> wektorDoWygladzania = new Vector<>();
        wektorDoWygladzania.add((double) firstAttribute.size());
        wektorDoWygladzania.add((double) secondAttribute.size());
        wektorDoWygladzania.add((double) thirdAttribute.size());
        wektorDoWygladzania.add((double) fourthAttribute.size());

        /**
         * Counting positivly classifed lines from testing file
         * and accuracy of experiment
         */
        try {
            int wellClassifiedSetosa = 0;
            int wellClassifiedVersicolor = 0;
            int wellClassifiedVirginica = 0;
            int unwellClassifiedSetosaVersi = 0;
            int unwellClassifiedSetosaVirgi = 0;
            int unwellClassifiedVersicolorSeto = 0;
            int unwellClassifiedVersicolorVirgi = 0;
            int unwellClassifiedVirginicaSeto = 0;
            int unwellClassifiedVirginicaVersi = 0;
            String line;
            Scanner testingScanner = new Scanner(new File("iris_test.txt"));
            while (testingScanner.hasNextLine()) {
                int wygladzanieSetosa = 0;
                int wygladzanieVersicolor = 0;
                int wygladzanieVirginica = 0;
                line = testingScanner.nextLine();
                line = line.replaceAll(",", ".");
                String[] attributes = line.split("\\s+");
                String decisionAtribute = attributes[attributes.length - 1];
                Vector<Double> normalAttributes = new Vector<>();
                for (int i = 1; i < attributes.length - 1; i++) {
                    normalAttributes.add(Double.parseDouble(attributes[i]));
                }
                Double[] setosa = new Double[5];
                Arrays.fill(setosa, 0.00);
                int setosaIlosc = 0;
                Double[] versicolor = new Double[5];
                Arrays.fill(versicolor, 0.00);
                int versicolorIlosc = 0;
                Double[] virginica = new Double[5];
                Arrays.fill(virginica, 0.00);
                int virginicaIlosc = 0;
                for (AttributesClass attributesClass : trainedValues) {
                    if (attributesClass.getDecisionValue().equals("Iris-setosa")) {
                        setosaIlosc++;
                        setosa[4]++;
                        for (int i = 0; i < 4; i++) {
                            if (normalAttributes.get(i).equals(attributesClass.getValues().get(i))) {
                                setosa[i]++;
                            }
                        }
                    } else if (attributesClass.getDecisionValue().equals("Iris-versicolor")) {
                        versicolorIlosc++;
                        versicolor[4]++;
                        for (int i = 0; i < 4; i++) {
                            if (normalAttributes.get(i).equals(attributesClass.getValues().get(i))) {
                                versicolor[i]++;
                            }
                        }
                    } else if (attributesClass.getDecisionValue().equals("Iris-virginica")) {
                        virginicaIlosc++;
                        virginica[4]++;
                        for (int i = 0; i < 4; i++) {
                            if (normalAttributes.get(i).equals(attributesClass.getValues().get(i))) {
                                virginica[i]++;
                            }
                        }
                    }
                    firstAttribute.add(attributesClass.getValues().get(0));
                    secondAttribute.add(attributesClass.getValues().get(1));
                    thirdAttribute.add(attributesClass.getValues().get(2));
                    fourthAttribute.add(attributesClass.getValues().get(3));
                }
                Double prawdopodobienstwoSetosa = (setosa[0] / setosaIlosc) * (setosa[1] / setosaIlosc) * (setosa[2] / setosaIlosc) * (setosa[3] / setosaIlosc) * (setosa[4] / (setosaIlosc + versicolorIlosc + virginicaIlosc));
                Double prawdopodobienstwoVersicolor = (versicolor[0] / versicolorIlosc) * (versicolor[1] / versicolorIlosc) * (versicolor[2] / versicolorIlosc) * (versicolor[3] / versicolorIlosc) * (versicolor[4] / (setosaIlosc + versicolorIlosc + virginicaIlosc));
                Double prawdopodobienstwoVirginica = (virginica[0] / virginicaIlosc) * (virginica[1] / virginicaIlosc) * (virginica[2] / virginicaIlosc) * (virginica[3] / virginicaIlosc) * (virginica[4] / (setosaIlosc + versicolorIlosc + virginicaIlosc));

                System.out.println("FAKTYCZNA DECYZJA : " + decisionAtribute);
                System.out.println(Arrays.toString(setosa));
                System.out.println(Arrays.toString(versicolor));
                System.out.println(Arrays.toString(virginica));
                System.out.println("PRZED WYGLADZANIEM : \n" + prawdopodobienstwoSetosa + " - Prawdopodobienstwo setosa \n" + prawdopodobienstwoVersicolor
                        + " - Prawdopodobienstwo versicolor \n" + prawdopodobienstwoVirginica + " - Prawdopodobienstwo virginica \n");


                Double[] copySetosa = setosa.clone();
                Double[] copyVersicolor = versicolor.clone();
                Double[] copyVirginica = virginica.clone();

                for (int i = 0; i < 4; i++) {
                    setosa[i] = setosa[i] / setosaIlosc;
                    versicolor[i] = versicolor[i] / versicolorIlosc;
                    virginica[i] = virginica[i] / virginicaIlosc;
                    if (setosa[i].equals(0.0)) {
                        setosa[i] = 1.00 / (setosaIlosc + wektorDoWygladzania.get(i));
                        wygladzanieSetosa++;
                    }
                    if (versicolor[i].equals(0.0)) {
                        versicolor[i] = 1.00 / (versicolorIlosc + wektorDoWygladzania.get(i));
                        wygladzanieVersicolor++;
                    }
                    if (virginica[i].equals(0.0)) {
                        virginica[i] = 1.00 / (virginicaIlosc + wektorDoWygladzania.get(i));
                        wygladzanieVirginica++;
                    }
                }
                setosa[4] = setosa[4] / (setosaIlosc + versicolorIlosc + virginicaIlosc);
                versicolor[4] = versicolor[4] / (setosaIlosc + versicolorIlosc + virginicaIlosc);
                virginica[4] = virginica[4] / (setosaIlosc + versicolorIlosc + virginicaIlosc);
                if (wygladzanieSetosa == 0) {
                    setosa[0] = (copySetosa[0] + 1) / (Double.valueOf(setosaIlosc) + wektorDoWygladzania.get(0));
                }
                if (wygladzanieVersicolor == 0) {
                    versicolor[0] = (copyVersicolor[0] + 1) / (Double.valueOf(versicolorIlosc) + wektorDoWygladzania.get(0));
                }
                if (wygladzanieVirginica == 0) {
                    virginica[0] = (copyVirginica[0] + 1) / (Double.valueOf(versicolorIlosc) + wektorDoWygladzania.get(0));
                }

                prawdopodobienstwoSetosa = setosa[0] * setosa[1] * setosa[2] * setosa[3] * setosa[4];
                prawdopodobienstwoVersicolor = versicolor[0] * versicolor[1] * versicolor[2] * versicolor[3] * versicolor[4];
                prawdopodobienstwoVirginica = virginica[0] * virginica[1] * virginica[2] * virginica[3] * virginica[4];

                System.out.println(Arrays.toString(setosa));
                System.out.println(Arrays.toString(versicolor));
                System.out.println(Arrays.toString(virginica));
                System.out.println("PO WYGLADZANIU : \n" + prawdopodobienstwoSetosa + " - Prawdopodobienstwo setosa \n" + prawdopodobienstwoVersicolor
                        + " - Prawdopodobienstwo versicolor \n" + prawdopodobienstwoVirginica + " - Prawdopodobienstwo virginica \n");
                Double decision = Math.max(Math.max(prawdopodobienstwoSetosa, prawdopodobienstwoVersicolor), prawdopodobienstwoVirginica);
                if (prawdopodobienstwoSetosa.equals(decision)) {
                    System.out.println("DECYZJA : Iris-setosa");
                    if (decisionAtribute.equals("Iris-setosa"))
                        wellClassifiedSetosa++;
                    else if (decisionAtribute.equals("Iris-versicolor")) {
                        unwellClassifiedSetosaVersi++;
                    } else if (decisionAtribute.equals("Iris-virginica")) {
                        unwellClassifiedSetosaVirgi++;
                    }
                }
                if (prawdopodobienstwoVersicolor.equals(decision)) {
                    System.out.println("DECYZJA : Iris-versicolor");
                    if (decisionAtribute.equals("Iris-versicolor"))
                        wellClassifiedVersicolor++;
                    else if (decisionAtribute.equals("Iris-setosa")) {
                        unwellClassifiedVersicolorSeto++;
                    } else if (decisionAtribute.equals("Iris-virginica")) {
                        unwellClassifiedVersicolorVirgi++;
                    }
                }
                if (prawdopodobienstwoVirginica.equals(decision)) {
                    System.out.println("DECYZJA : Iris-virginica");
                    if (decisionAtribute.equals("Iris-virginica"))
                        wellClassifiedVirginica++;
                    else if (decisionAtribute.equals("Iris-setosa")) {
                        unwellClassifiedVirginicaSeto++;
                    } else if (decisionAtribute.equals("Iris-versicolor")) {
                        unwellClassifiedVirginicaVersi++;
                    }
                }
                System.out.println("======================================================================");
            }

            System.out.println("ZAKLASYFIKOWANO JAKO : ");
            System.out.println("            SETOSA   VERSICOLOR  VIRGINICA\n");
            System.out.println("SETOSA      " + wellClassifiedSetosa + "          " + unwellClassifiedVersicolorSeto + "          " + unwellClassifiedVirginicaSeto + " \n");
            System.out.println("VERSICOLOR  " + unwellClassifiedSetosaVersi + "          " + wellClassifiedVersicolor + "          " + unwellClassifiedVirginicaVersi + "\n");
            System.out.println("VIRGINICA   " + unwellClassifiedSetosaVirgi + "          " + unwellClassifiedVersicolorVirgi + "          " + wellClassifiedVirginica + "\n");

            Double accuracy = (wellClassifiedSetosa + wellClassifiedVirginica + wellClassifiedVersicolor) / 30.00 * 100.00;

            Double precisionSetosa = Double.valueOf(wellClassifiedSetosa) / (wellClassifiedSetosa + unwellClassifiedSetosaVersi + unwellClassifiedSetosaVirgi) * 100.00;
            Double pelnoscSetosa = Double.valueOf(wellClassifiedSetosa) / (unwellClassifiedVersicolorSeto + wellClassifiedSetosa + unwellClassifiedVirginicaSeto) * 100.00;
            Double fMiaraSetosa = (2 * precisionSetosa * pelnoscSetosa) / (precisionSetosa + pelnoscSetosa);

            Double precisionVirginica = Double.valueOf(wellClassifiedVirginica) / (wellClassifiedVirginica + unwellClassifiedVirginicaSeto + unwellClassifiedVirginicaVersi) * 100.00;
            Double pelnoscVirginica = Double.valueOf(wellClassifiedVirginica) / (wellClassifiedVirginica + unwellClassifiedVersicolorVirgi + unwellClassifiedSetosaVirgi) * 100.00;
            Double fMiaraVirginica = (2 * precisionVirginica * pelnoscVirginica) / (precisionVirginica + pelnoscVirginica);

            Double precisionVersicolor = Double.valueOf(wellClassifiedVersicolor) / (wellClassifiedVersicolor + unwellClassifiedVersicolorSeto + unwellClassifiedVersicolorVirgi) * 100.00;
            Double pelnoscVeriscolor = Double.valueOf(wellClassifiedVersicolor) / (wellClassifiedVersicolor + unwellClassifiedVirginicaVersi + unwellClassifiedSetosaVersi) * 100.00;
            Double fMiaraVersicolor = (2 * precisionVersicolor * pelnoscVeriscolor) / (pelnoscVeriscolor + precisionVersicolor);


            System.out.println("DOKLADNOSC : " + accuracy + " %");
            System.out.println("precyzja setosa : " + precisionSetosa + " %");
            System.out.println("pelnosc setosa : " + pelnoscSetosa + " %");
            System.out.println("F miara setosa : " + fMiaraSetosa + " %");
            System.out.println("precyzja virginica : " + precisionVirginica + " %");
            System.out.println("pelnosc virginica : " + pelnoscVirginica + " %");
            System.out.println("F miara virginica : " + fMiaraVirginica + " %");
            System.out.println("precyzja versicolor : " + precisionVersicolor + " %");
            System.out.println("pelnosc versicolor : " + pelnoscVeriscolor + " %");
            System.out.println("F miara versicolor : " + fMiaraVersicolor + " %");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    }
}

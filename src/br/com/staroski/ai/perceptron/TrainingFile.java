package br.com.staroski.ai.perceptron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class TrainingFile {

    public static List<Pattern> load(File file, String separator, int inputs, int outputs) throws IOException {
        return load(new FileInputStream(file), separator, inputs, outputs);
    }

    public static List<Pattern> load(InputStream input, String separator, int inputs, int outputs) throws IOException {
        List<Pattern> patterns = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String text = null;
        while ((text = reader.readLine()) != null) {
            double[] values = new double[inputs + outputs];
            String[] line = text.split(separator);
            for (int i = 0; i < inputs; i++) {
                values[i] = Double.parseDouble(line[i]);
            }
            for (int i = 0; i < outputs; i++) {
                values[inputs + i] = Double.parseDouble(line[inputs + i]);
            }
            patterns.add(Pattern.train(inputs, outputs).values(values));
        }
        return patterns;
    }

    public static List<Pattern> load(String filePath, String separator, int inputs, int outputs) throws IOException {
        return load(new File(filePath), separator, inputs, outputs);
    }

    private TrainingFile() {}
}

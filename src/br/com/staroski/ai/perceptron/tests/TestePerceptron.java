package br.com.staroski.ai.perceptron.tests;

import java.io.IOException;
import java.util.List;

import br.com.staroski.ai.perceptron.TrainingFile;
import br.com.staroski.ai.perceptron.Network;
import br.com.staroski.ai.perceptron.Pattern;

public class TestePerceptron {

    public static void main(String[] args) {
        try {
            new TestePerceptron().executar();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void executar() throws IOException {
        int inputNeurons = 2;
        int middleNeurons = 3;
        int outputNeurons = 1;
        double assertivity = 0.9999;
        String csvFile = System.getProperty("user.dir") + "/src/patterns.csv";
        String separator = "\\,";
        List<Pattern> patterns = TrainingFile.load(csvFile, separator, inputNeurons, outputNeurons);
        Network network = new Network(inputNeurons, middleNeurons, outputNeurons);
        System.out.println("treinando...");
        int iterations = network.train(patterns, assertivity);
        System.out.println("treinamento concluido em " + iterations + " iteracoes");
        test(network);
    }

    private void test(Network net) {
        test(net, 0, 0);
        test(net, 0, 1);
        test(net, 1, 0);
        test(net, 1, 1);
    }

    private void test(Network net, int a, int b) {
        int inputs = net.sizeOf(0);
        int c = 0;
        System.out.printf("%d xor %d = %.3f\n", a, b, net.activate(Pattern.test(inputs).values(a, b)).get(c).value());
    }
}

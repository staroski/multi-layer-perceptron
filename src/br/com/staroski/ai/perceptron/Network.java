package br.com.staroski.ai.perceptron;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Network implements Iterable<Layer> {

    private final int[] sizes;
    private final Layer[] layers;

    public Network(int layer1, int layer2, int... layerN) {
        int count = 2 + layerN.length;
        layers = new Layer[count];
        sizes = new int[count];
        sizes[0] = layer1;
        sizes[1] = layer2;
        System.arraycopy(layerN, 0, sizes, 2, layerN.length);
        initialize();
    }

    public Layer activate(Pattern pattern) {
        for (int i = 0; i < inputs().size(); i++) {
            inputs().get(i).apply(pattern.inputs[i]);
        }
        for (int i = 1; i < layers.length; i++) {
            for (Neuron neuron : layers[i]) {
                neuron.activationFunction();
            }
        }
        return outputs();
    }

    public Layer get(int layer) {
        return layers[layer];
    }

    public void initialize() {
        Random random = new Random();
        layers[0] = new Layer(sizes[0]);
        for (int i = 1; i < sizes.length; i++) {
            layers[i] = (new Layer(sizes[i], layers[i - 1], random));
        }
    }

    public Layer inputs() {
        return layers[0];
    }

    @Override
    public Iterator<Layer> iterator() {
        return Arrays.asList(layers).iterator();
    }

    public Layer outputs() {
        return layers[layers.length - 1];
    }

    public int size() {
        return layers.length;
    }

    public int sizeOf(int layer) {
        return sizes[layer];
    }

    public double train(List<Pattern> padroes) {
        double error = 0;
        for (Pattern padrao : padroes) {
            activate(padrao);
            for (int i = 0; i < outputs().size(); i++) {
                double delta = padrao.outputs[i] - outputs().get(i).value();
                outputs().get(i).colectError(delta);
                error += Math.pow(delta, 2);
            }
            adjustWeights();
        }
        return error;
    }

    public int train(List<Pattern> patterns, double assertivity) {
        double tolerancy = 1 - assertivity;
        double error;
        int iterations = 0;
        do {
            error = train(patterns);
            iterations++;
        } while (error > tolerancy);
        return iterations;
    }

    private void adjustWeights() {
        for (int i = layers.length - 1; i > 0; i--) {
            for (Neuron neuron : layers[i]) {
                neuron.adjustWeights();
            }
        }
    }
}

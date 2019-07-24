package br.com.staroski.ai.perceptron;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Layer implements Iterable<Neuron> {

    private final Neuron[] neurons;

    public Layer(int size) {
        neurons = new Neuron[size];
        for (int i = 0; i < size; i++) {
            neurons[i] = new Neuron();
        }
    }

    public Layer(int size, Layer layer, Random random) {
        neurons = new Neuron[size];
        for (int i = 0; i < size; i++) {
            neurons[i] = new Neuron(layer, random);
        }
    }

    public Neuron get(int index) {
        return neurons[index];
    }

    @Override
    public Iterator<Neuron> iterator() {
        return Arrays.asList(neurons).iterator();
    }

    public Neuron[] neurons() {
        int size = size();
        Neuron[] copy = new Neuron[size];
        System.arraycopy(neurons, 0, copy, 0, size);
        return copy;
    }

    public int size() {
        return neurons.length;
    }
}
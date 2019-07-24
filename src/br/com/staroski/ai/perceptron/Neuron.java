package br.com.staroski.ai.perceptron;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Neuron {

    private double bias; // viés
    private double error; // soma do erro
    private double inputs; // soma das entradas
    private double steepness = 5; // inclinação da curva sigmóide
    private double learning = 0.01; // taxa de aprendizado
    private double output = Double.MIN_VALUE; // valor inicial da saída do neuron
    private List<Synapsis> weights; // pesos sinápticos das entradas

    public Neuron() {}

    public Neuron(Layer inputs, Random random) {
        weights = new LinkedList<Synapsis>();
        for (Neuron input : inputs) {
            Synapsis weight = new Synapsis();
            weight.input = input;
            weight.output = random.nextDouble();
            weights.add(weight);
        }
    }

    public void activationFunction() {
        error = 0;
        inputs = 0;
        for (Synapsis weight : weights) {
            inputs += weight.output * weight.input.value();
        }
    }

    public void adjustWeights() {
        for (Synapsis weight : weights) {
            weight.output += error * derivationFunction() * learning * weight.input.value();
        }
        bias += error * derivationFunction() * learning;
    }

    public void apply(double value) {
        output = value;
    }

    public double bias() {
        return bias;
    }

    public void colectError(double delta) {
        if (weights != null) {
            error += delta;
            for (Synapsis weight : weights) {
                weight.input.colectError(error * weight.output);
            }
        }
    }

    public List<Synapsis> inputs() {
        return weights;
    }

    public double value() {
        if (output != Double.MIN_VALUE) {
            return output;
        }
        return 1 / (1 + Math.exp(-steepness * (inputs + bias)));
    }

    private double derivationFunction() {
        double activation = value();
        return activation * (1 - activation);
    }

}
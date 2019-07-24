package br.com.staroski.ai.perceptron;

public class Pattern {

    private static final class Builder implements PatternBuilder {

        private final int inputs;
        private final int outputs;

        private Builder(int inputs, int outputs) {
            this.inputs = inputs;
            this.outputs = outputs;
        }

        @Override
        public Pattern values(double... values) {
            return new Pattern(inputs, outputs, values);
        }
    }

    public static PatternBuilder test(int inputs) {
        return new Builder(inputs, 0);
    }

    public static PatternBuilder train(int inputs, int outputs) {
        return new Builder(inputs, outputs);
    }

    public final double[] inputs;
    public final double[] outputs;

    private Pattern(int inputs, int outputs, double... values) {
        if (inputs + outputs != values.length) {
            throw new IllegalArgumentException("Input does not match network configuration");
        }
        this.inputs = new double[inputs];
        for (int i = 0; i < inputs; i++) {
            this.inputs[i] = values[i];
        }
        this.outputs = new double[outputs];
        for (int i = 0; i < outputs; i++) {
            this.outputs[i] = values[inputs + i];
        }
    }
}
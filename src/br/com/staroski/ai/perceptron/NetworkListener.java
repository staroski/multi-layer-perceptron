package br.com.staroski.ai.perceptron;

public interface NetworkListener {

    void onError(Network net, double error);

    void onTrained(Network net, int iterations);

}

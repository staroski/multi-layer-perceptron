package br.com.staroski.ai.perceptron;

import java.util.ArrayList;
import java.util.Random;

public class Camada extends ArrayList<Neuronio> {

	private static final long serialVersionUID = 1;

	public Camada(int tamanho) {
		for (int i = 0; i < tamanho; i++)
			add(new Neuronio());
	}

	public Camada(int tamanho, Camada camada, Random rnd) {
		for (int i = 0; i < tamanho; i++)
			add(new Neuronio(camada, rnd));
	}
}
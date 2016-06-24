package br.com.staroski.ai.perceptron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rede extends ArrayList<Camada> {

	private static final long serialVersionUID = 1;

	private int[] dimensoes;

	public Rede(int camada1, int camada2, int... camadaN) {
		int tamanho = 1 + 1 + camadaN.length;
		this.dimensoes = new int[tamanho];
		dimensoes[0] = camada1;
		dimensoes[1] = camada2;
		System.arraycopy(camadaN, 0, dimensoes, 2, camadaN.length);
		inicializar();
	}

	public Camada ativar(Padrao padrao) {
		for (int i = 0; i < entradas().size(); i++) {
			entradas().get(i).setSaida(padrao.entradas[i]);
		}
		for (int i = 1; i < size(); i++) {
			for (Neuronio neuron : get(i)) {
				neuron.funcaoAtivacao();
			}
		}
		return saidas();
	}

	public Camada entradas() {
		return get(0);
	}

	public int getDimensao(int camada) {
		return dimensoes[camada];
	}

	public void inicializar() {
		clear();
		add(new Camada(dimensoes[0]));
		for (int i = 1; i < dimensoes.length; i++) {
			add(new Camada(dimensoes[i], get(i - 1), new Random()));
		}
	}

	public Camada saidas() {
		return get(size() - 1);
	}

	public double treinar(List<Padrao> padroes) {
		double erro = 0;
		for (Padrao padrao : padroes) {
			ativar(padrao);
			for (int i = 0; i < saidas().size(); i++) {
				double delta = padrao.saidas[i] - saidas().get(i).getValorSaida();
				saidas().get(i).coletarErro(delta);
				erro += Math.pow(delta, 2);
			}
			ajustarPesos();
		}
		return erro;
	}

	private void ajustarPesos() {
		for (int i = size() - 1; i > 0; i--) {
			for (Neuronio neuron : get(i)) {
				neuron.ajustarPesos();
			}
		}
	}
}

package br.com.staroski.ai.perceptron;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Padrao {

	public static List<Padrao> carregar(String csvFile, int dimensaoEntrada, int dimensaoSaida) {
		return carregar(new File(csvFile), dimensaoEntrada, dimensaoSaida);
	}

	public static List<Padrao> carregar(File csvFile, int dimensaoEntrada, int dimensaoSaida) {
		return carregar(csvFile.toPath(), dimensaoEntrada, dimensaoSaida);
	}

	public static List<Padrao> carregar(Path csvFilePath, int dimensaoEntrada, int dimensaoSaida) {
		List<Padrao> padroes = new ArrayList<Padrao>();
		try {
			List<String> linhas = Files.readAllLines(csvFilePath, Charset.defaultCharset());
			for (String valores : linhas) {
				padroes.add(new Padrao(valores, dimensaoEntrada, dimensaoSaida));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return padroes;
	}

	public final double[] entradas;
	public final double[] saidas;

	public Padrao(String textoCsv, int dimensaoEntrada) {
		this(textoCsv, dimensaoEntrada, 0);
	}

	public Padrao(String textoCsv, int dimensaoEntrada, int dimensaoSaida) {
		String[] line = textoCsv.split(",");
		if (line.length != dimensaoEntrada + dimensaoSaida) {
			throw new IllegalArgumentException("Input does not match network configuration");
		}
		entradas = new double[dimensaoEntrada];
		for (int i = 0; i < dimensaoEntrada; i++) {
			entradas[i] = Double.parseDouble(line[i]);
		}
		saidas = new double[dimensaoSaida];
		for (int i = 0; i < dimensaoSaida; i++) {
			saidas[i] = Double.parseDouble(line[i + dimensaoEntrada]);
		}
	}

	public int saidaMaxima() {
		int item = -1;
		double max = Double.MIN_VALUE;
		for (int i = 0; i < saidas.length; i++) {
			if (saidas[i] > max) {
				max = saidas[i];
				item = i;
			}
		}
		return item;
	}
}
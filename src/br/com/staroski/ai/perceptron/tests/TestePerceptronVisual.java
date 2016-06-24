package br.com.staroski.ai.perceptron.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.staroski.ai.perceptron.CSV;
import br.com.staroski.ai.perceptron.Neuronio;
import br.com.staroski.ai.perceptron.Padrao;
import br.com.staroski.ai.perceptron.Rede;
import br.com.staroski.ai.perceptron.Sinapse;

public class TestePerceptronVisual extends JFrame {

	private static final long serialVersionUID = 1;

	private int iteration;
	private List<Padrao> patterns;
	private Rede network;
	private Color[] brushes = new Color[] { Color.RED, Color.GREEN, Color.BLUE };

	public static void main(String... args) {
		try {
			TestePerceptronVisual graph = new TestePerceptronVisual();
			graph.setDefaultCloseOperation(EXIT_ON_CLOSE);
			graph.setSize(400, 400);
			graph.setVisible(true);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public TestePerceptronVisual() throws IOException {
		String file = System.getProperty("user.dir") + "/src/iris_data.csv"; // Replace with your input file location.
		patterns = CSV.carregar(file, 4, 3);
		network = new Rede(4, 4, 2, 3);

		add(BorderLayout.CENTER, new JPanel() {

			private static final long serialVersionUID = 1;

			@Override
			protected void processKeyEvent(KeyEvent e) {
				super.processKeyEvent(e);
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					network.inicializar();
					iteration = 0;
				}
			}

			@Override
			protected void paintComponent(Graphics g) {
				double error = network.treinar(patterns);
				UpdatePlotArea((Graphics2D) g, getBounds(), error);
				iteration++;
				invalidate();
				repaint(0);
			}
		});
	}

	private void UpdatePlotArea(Graphics2D g, Rectangle bounds, double error) {
		setTitle("Iteration: " + iteration + "  Error: " + error);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, bounds.width, bounds.height);
		for (float[] point : pontos2D(network, patterns)) {
			g.setColor(brushes[(int) point[2]]);
			g.fillRect((int) point[0] * (bounds.width - 5), (int) point[1] * (bounds.height - 5), 5, 5);
		}
		for (double[] line : hiperPlanos(network)) {
			double a = -line[0] / line[1];
			double c = -line[2] / line[1];
			Point left = new Point(0, (int) (c * bounds.height));
			Point right = new Point(bounds.width, (int) ((a + c) * bounds.height));
			g.setColor(Color.GRAY);
			g.drawLine(left.x, left.y, right.x, right.y);
		}
	}

	private static List<float[]> pontos2D(Rede rede, List<Padrao> padroes) {
		int penultimate = rede.size() - 2;
		if (rede.get(penultimate).size() != 2) {
			throw new IllegalArgumentException("Penultimate layer must be 2D for graphing.");
		}
		List<float[]> points = new ArrayList<float[]>();
		for (int i = 0; i < padroes.size(); i++) {
			rede.ativar(padroes.get(i));
			float[] point = new float[3];
			point[0] = (float) rede.get(penultimate).get(0).getValorSaida();
			point[1] = (float) rede.get(penultimate).get(1).getValorSaida();
			if (rede.saidas().size() > 1) {
				point[2] = padroes.get(i).saidaMaxima();
			} else {
				point[2] = (padroes.get(i).saidas[0] >= 0.5) ? 1 : 0;
			}
			points.add(point);
		}
		return points;
	}

	private static List<double[]> hiperPlanos(Rede rede) {
		List<double[]> lines = new ArrayList<double[]>();
		for (Neuronio n : rede.saidas()) {
			lines.add(hiperPlano(n));
		}
		return lines;
	}

	private static double[] hiperPlano(Neuronio neuron) {
		double[] linha = new double[3];
		List<Sinapse> pesos = neuron.getEntradas();
		linha[0] = pesos.get(0).valor;
		linha[1] = pesos.get(1).valor;
		linha[2] = neuron.getBias();
		return linha;
	}
}

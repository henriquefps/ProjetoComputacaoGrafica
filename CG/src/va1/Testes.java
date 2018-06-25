package va1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import control.JanelaParteDois;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Testes extends Application {

	public static void imprimeMatriz(double m[][], int x, int y) {
		for (int i = 0; i < x; i++) {
			System.out.print("[");
			for (int j = 0; j < y; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println("]");
		}
		System.out.println("---------------------");
	}

	public static void Questao1(){
		System.out.println("1A - Mutiplicacao de Matrizes");
		double mA[][] = new double[2][3];
		mA[0][0] = 1.5;
		mA[0][1] = 2.5;
		mA[0][2] = 3.5;
		mA[1][0] = 4.5;
		mA[1][1] = 5.5;
		mA[1][2] = 6.5;

		double mB[][] = new double[3][2];
		mB[0][0] = 7.5;
		mB[0][1] = 8.5;
		mB[1][0] = 9.5;
		mB[1][1] = 10.5;
		mB[2][0] = 11.5;
		mB[2][1] = 12.5;

		double mC[][] = BibOps.matrixMulti(mA, mB, 2, 3, 3, 2);
		imprimeMatriz(mC, 2, 2);

		System.out.println();

		System.out.println("1B - Subtracao de pontos");
		double p[][] = BibOps.subtPontos3D(new Ponto3D(3.5, 1.5, 2.0), new Ponto3D(1.0, 2.0, 1.5));
		imprimeMatriz(p, 2, 1);

		System.out.println();

		System.out.println("1C - Produto Escalar");
		double vA[][] = new double[3][1];
		double vB[][] = new double[3][1];

		vA[0][0] = 3.5;
		vA[1][0] = 1.5;
		vA[2][0] = 2.0;

		vB[0][0] = 1.0;
		vB[1][0] = 2.0;
		vB[2][0] = 1.5;

		double vC = BibOps.produtoEscalar3D(vA, vB);
		System.out.println(vC);

		System.out.println();

		System.out.println("1D - Produto Vetorial");
		double vA2[][] = new double[3][1];
		double vB2[][] = new double[3][1];

		vA2[0][0] = 3.5;
		vA2[1][0] = 1.5;
		vA2[2][0] = 2.0;

		vB2[0][0] = 1.0;
		vB2[1][0] = 2.0;
		vB2[2][0] = 1.5;

		double vC2[][] = BibOps.produtoVetorial3D(vA2, vB2);
		imprimeMatriz(vC2, 3, 1);

		System.out.println();

		System.out.println("1E - Norma do Vetor");
		double vA3[][] = new double[3][1];

		vA3[0][0] = 3.5;
		vA3[1][0] = 1.5;
		vA3[2][0] = 2.0;

		System.out.println("Norma = " + BibOps.normaVetor3D(vA3));
		System.out.println("---------------------");

		System.out.println();

		System.out.println("1F - Vetor Normalizado");
		imprimeMatriz(BibOps.normalizaVetor3D(vA3), 3, 1);

		System.out.println();

		System.out.println("1G - Coordenada Baricentrica");

		Ponto2D ponto = BibOps.coordBaricentricas2D(new Ponto2D(-0.25, 0.75), new Ponto2D(-1, 1), new Ponto2D(0, -1),
				new Ponto2D(1, 1));
		System.out.println("P = (" + ponto.x + ", " + ponto.y + ")");
		System.out.println("---------------------");

		System.out.println();

		System.out.println("1H - Coordenada Cartesiana");
		Ponto2D p2 = BibOps.cartesianaDaBaricentrica(new Ponto2D(-1, 1), new Ponto2D(0, -1), new Ponto2D(1, 1), 0.5,
				0.25, 0.25);
		System.out.println("P = (" + p2.x + ", " + p2.y + ")");
		System.out.println("---------------------");
		
	}
	public static void main(String[] args) throws IOException {
		
		//Questao1();
		
		// ################ Questao 2 ####################
		// Coloque o nome do arquivo de pontos sem extensão
		// getPontosArquivo("vaso");
		// launch(args);

		getPontosArquivo("calice2");
		
		camera = new CameraVirtual();
		camera.N = new double[][] { { 0 }, { 1 }, { -1 } };
		camera.V = new double[][] { { 0 }, { -1 }, { -1 } };
		camera.d = 5;
		camera.hx = 1.5;
		camera.hy = 1.5;
		camera.C = new Ponto3D(0, -500, 500);
		camera.ortogonalizarV();

		imprimeMatriz(camera.U, 3, 1);
		imprimeMatriz(camera.V, 3, 1);
		imprimeMatriz(camera.N, 3, 1);
		imprimeMatriz(camera.retorneBaseOrtonormal(), 3, 3);
		
		
		//imprimeMatriz(camera.N, 3, 1);

		atualizarCoordVista();
		launch(args);

	}
	
	public static void atualizarCoordVista() {
		for (int i = 0; i < t.length; i++) {
			BibOps.atualizaTrianguloParaCoordVista(t[i], camera);
		}
	}
	
	public static CameraVirtual camera;
	static Ponto3D p[];
	static Triangulo t[];
	public static int xmax = 500, ymax = 500;

	public static void getPontosArquivo(String arquivoSemExtensao) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(arquivoSemExtensao + ".byu"));
		String linha = reader.readLine();
		String qnt[] = linha.split(" ");
		p = new Ponto3D[Integer.parseInt(qnt[0])];
		t = new Triangulo[Integer.parseInt(qnt[1])];
		for (int i = 0; i < p.length; i++) {
			linha = reader.readLine();
			qnt = linha.split(" ");
			p[i] = new Ponto3D();
			p[i].x = Double.parseDouble(qnt[0]);
			p[i].y = Double.parseDouble(qnt[1]);
			p[i].z = Double.parseDouble(qnt[2]);
		}
		for (int i = 0; i < t.length; i++) {
			linha = reader.readLine();
			qnt = linha.split(" ");
			int a, b, c;
			a = Integer.parseInt(qnt[0]);
			b = Integer.parseInt(qnt[1]);
			c = Integer.parseInt(qnt[2]);
			t[i] = new Triangulo(p[a - 1], p[b - 1], p[c - 1]);
		}
		reader.close();
	}

	public static void malhaPontos(GraphicsContext gc, int xmax, int ymax) {
		gc.setFill(Color.BLUEVIOLET); // Cor do fundo
		gc.fillRect(0, 0, xmax, ymax); // Pinta o fundo
		double maxX = Testes.p[0].x, maxY = Testes.p[0].y;
		double minX = Testes.p[0].x, minY = Testes.p[0].y;
		for (int i = 1; i < Testes.p.length; i++) {
			if (Testes.p[i].x > maxX) {
				maxX = Testes.p[i].x;
			}
			if (Testes.p[i].y > maxY) {
				maxY = Testes.p[i].y;
			}

			if (Testes.p[i].x < minX) {
				minX = Testes.p[i].x;
			}
			if (Testes.p[i].y < minY) {
				minY = Testes.p[i].y;
			}
		}
		for (int i = 0; i < Testes.p.length; i++) {
			double ponto[] = { Testes.p[i].x, Testes.p[i].y };
			ponto[0] = ((ponto[0] - minX) / (maxX - minX)) * (xmax - 11) + 1;
			ponto[1] = ((ponto[1] - minY) / (maxY - minY)) * (ymax - 11) + 1;
			gc.setFill(Color.GOLD); // Cor do Ponto
			gc.fillRect(ponto[0], ponto[1], 3, 3); // Tamanho do Ponto
		}
	}

	public static void malhaTriangulos(GraphicsContext gc, int xmax, int ymax) {
		gc.setFill(Color.BLACK); // Cor do fundo
		gc.fillRect(0, 0, xmax, ymax); // Pinta o fundo
		double maxX = t[0].a.x, maxY = t[0].a.y;
		double minX = t[0].a.x, minY = t[0].a.y;
		for (int i = 0; i < t.length; i++) {
			maxX = Math.max(maxX, t[i].a.x);
			maxX = Math.max(maxX, t[i].b.x);
			maxX = Math.max(maxX, t[i].c.x);

			maxY = Math.max(maxY, t[i].a.y);
			maxY = Math.max(maxY, t[i].b.y);
			maxY = Math.max(maxY, t[i].c.y);

			minX = Math.min(minX, t[i].a.x);
			minX = Math.min(minX, t[i].b.x);
			minX = Math.min(minX, t[i].c.x);

			minY = Math.min(minY, t[i].a.y);
			minY = Math.min(minY, t[i].b.y);
			minY = Math.min(minY, t[i].c.y);

		}
		for (int i = 0; i < t.length; i++) {
			double pontoA[] = new double[2];
			pontoA[0] = (camera.d * t[i].a.x/t[i].a.z)/camera.hx;
			pontoA[1] = (camera.d * t[i].a.y/t[i].a.z)/camera.hy;
			pontoA[0] = Math.abs((pontoA[0] + camera.hx)/(2 * camera.hx) * xmax + 0.5);
			pontoA[1] = Math.abs(ymax - (pontoA[1] + camera.hy)/(2 * camera.hy) * ymax + 0.5);

			double pontoB[] = new double[2];
			pontoB[0] = (camera.d * t[i].b.x/t[i].b.z)/camera.hx;
			pontoB[1] = (camera.d * t[i].b.y/t[i].b.z)/camera.hy;
			pontoB[0] = Math.abs((pontoB[0] + camera.hx)/(2 * camera.hx) * xmax + 0.5);
			pontoB[1] = Math.abs(ymax - (pontoB[1] + camera.hy)/(2 * camera.hy) * ymax + 0.5);

			double pontoC[] = new double[2];
			pontoC[0] = (camera.d * t[i].c.x/t[i].c.z)/camera.hx;
			pontoC[1] = (camera.d * t[i].c.y/t[i].c.z)/camera.hy;
			pontoC[0] = Math.abs((pontoC[0] + camera.hx)/(2 * camera.hx) * xmax + 0.5);
			pontoC[1] = Math.abs(ymax - (pontoC[1] + camera.hy)/(2 * camera.hy) * ymax + 0.5);
			
			gc.setFill(Color.WHITE); // Cor do Ponto
			gc.fillRect(pontoA[0], pontoA[1], 1, 1); // Tamanho do Ponto
			gc.fillRect(pontoB[0], pontoB[1], 1, 1); // Tamanho do Ponto
			gc.fillRect(pontoC[0], pontoC[1], 1, 1); // Tamanho do Ponto
			
			BibOps.scanLine(new Ponto2D(pontoA[0], pontoA[1]), new Ponto2D(pontoB[0], pontoB[1]), new Ponto2D(pontoC[0], pontoC[1]), gc);
			
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Teste");
		BorderPane pane = FXMLLoader.load(this.getClass().getResource("janelaProjetoP2.fxml"));
		primaryStage.setScene(new Scene(pane));
		primaryStage.setResizable(false);
		primaryStage.show();

	}

}

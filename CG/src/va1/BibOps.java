package va1;

import java.io.IOException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BibOps {

	public static double[][] matrixMulti(double[] A[], double[] B[], int x1, int y1, int x2, int y2) {
		double[] C[] = new double[x1][y2];

		for (int i = 0; i < x1; i++) {
			for (int j = 0; j < y2; j++) {
				C[i][j] = 0;
			}
		}
		if (y1 == x2) {
			for (int i = 0; i < x1; i++) {
				for (int j = 0; j < y2; j++) {
					for (int j2 = 0; j2 < x2; j2++) {
						C[i][j] += A[i][j2] * B[j2][j];
					}
				}
			}
		} else {
			System.err.println("Matrizes incompatíveis para operação.");
		}
		return C;
	}

	public static double[][] subtPontos3D(Ponto3D q, Ponto3D p) {
		double vetor[][] = new double[3][1];

		vetor[0][0] = q.x - p.x;
		vetor[1][0] = q.y - p.y;
		vetor[2][0] = q.z - p.z;

		return vetor;
	}

	public static double produtoEscalar3D(double[] A[], double[] B[]) {
		double v = 0;
		for (int i = 0; i < 3; i++) {
			v += A[i][0] * B[i][0];
		}
		return v;
	}

	public static double[][] produtoVetorial3D(double[] A[], double[] B[]) {
		double[] vetor[] = new double[3][1];
		double[] matrizAux[] = new double[3][3];

		for (int i = 0; i < 3; i++) {
			matrizAux[0][i] = 1;
			matrizAux[1][i] = A[i][0];
			matrizAux[2][i] = B[i][0];
		}

		vetor[0][0] = matrizAux[0][0] * matrizAux[1][1] * matrizAux[2][2]
				- (matrizAux[0][0] * matrizAux[1][2] * matrizAux[2][1]);
		vetor[1][0] = matrizAux[0][1] * matrizAux[1][2] * matrizAux[2][0]
				- (matrizAux[0][1] * matrizAux[1][0] * matrizAux[2][2]);
		vetor[2][0] = matrizAux[0][2] * matrizAux[1][0] * matrizAux[2][1]
				- (matrizAux[0][2] * matrizAux[1][1] * matrizAux[2][0]);

		return vetor;
	}

	public static double normaVetor3D(double[] A[]) {
		return Math.sqrt(Math.pow(A[0][0], 2) + Math.pow(A[1][0], 2) + Math.pow(A[2][0], 2));
	}

	public static double[][] normalizaVetor3D(double[] A[]) {
		double[] vet[] = new double[3][1];
		double norma = normaVetor3D(A);
		for (int i = 0; i < 3; i++) {
			vet[i][0] = A[i][0] / norma;
		}

		return vet;
	}

	public static Ponto2D coordBaricentricas2D(Ponto2D alvo, Ponto2D a, Ponto2D b, Ponto2D c) {
		double[] matrizT[] = new double[2][2];
		double[] resposta[];

		matrizT[0][0] = a.x - c.x;
		matrizT[0][1] = b.x - c.x;
		matrizT[1][0] = a.y - c.y;
		matrizT[1][1] = b.y - c.y;

		double[] vet[] = new double[2][1];
		vet[0][0] = alvo.x - c.x;
		vet[1][0] = alvo.y - c.y;

		double det = matrizT[0][0] * matrizT[1][1] - matrizT[0][1] * matrizT[1][0];

		double M_Inversa[][] = inversa(matrizT, det);
		resposta = matrixMulti(M_Inversa, vet, 2, 2, 2, 1);

		Ponto2D p = new Ponto2D(resposta[0][0], resposta[1][0]);
		return p;
	}

	public static Ponto2D cartesianaDaBaricentrica(Ponto2D p1, Ponto2D p2, Ponto2D p3, double alfa, double beta,
			double gama) {

		double x = alfa * p1.x + beta * p2.x + gama * p3.x;
		double y = alfa * p1.y + beta * p2.y + gama * p3.y;

		return new Ponto2D(x, y);
	}

	public static double[][] inversa(double m[][], double determinante) {
		double inversa[][] = new double[m.length][m[0].length];

		double a = m[0][0];
		double b = m[0][1];
		double c = m[1][0];
		double d = m[1][1];

		inversa[0][0] = d / determinante;
		inversa[0][1] = (-b) / determinante;
		inversa[1][0] = (-c) / determinante;
		inversa[1][1] = a / determinante;

		return inversa;
	}

	public static Ponto3D coordenadasDeVista(CameraVirtual camera, Ponto3D ponto) {
		double p_c[][] = subtPontos3D(ponto, camera.C);
		return pontoDeVetor3x1(matrixMulti(camera.retorneBaseOrtonormal(), p_c, 3, 3, 3, 1));
	}

	public static Ponto3D pontoDeVetor3x1(double v[][]) {
		return new Ponto3D(v[0][0], v[1][0], v[2][0]);
	}

	public static void atualizaTrianguloParaCoordVista(Triangulo t, CameraVirtual camera) {
		t.a = BibOps.coordenadasDeVista(camera, t.a);
		t.b = BibOps.coordenadasDeVista(camera, t.b);
		t.c = BibOps.coordenadasDeVista(camera, t.c);
	}

	public static void scanLine(Ponto2D a, Ponto2D b, Ponto2D c, GraphicsContext gc) {
		double ymin = Math.min(a.y, b.y);
		ymin = Math.min(ymin, c.y);

		Ponto2D inicio = null, meio = null, fim = null;
		if (ymin == a.y) {
			inicio = a;
			if (b.y <= c.y) {
				meio = b;
				fim = c;
			} else {
				meio = c;
				fim = b;
			}
		} else if (ymin == b.y) {
			inicio = b;
			if (a.y <= c.y) {
				meio = a;
				fim = c;
			} else {
				meio = c;
				fim = a;
			}
		} else if(ymin == c.y){
			inicio = c;
			if (b.y <= a.y) {
				meio = b;
				fim = a;
			} else { 
				meio = a;
				fim = b;
			}
		} else {
			System.err.println("ymin não corresponde a nada");
		}
		
		if ((int)meio.y == (int)fim.y /*|| (int)meio.y == ((int)fim.y + 1) || (int)meio.y == ((int)fim.y - 1)*/) {
			//System.out.println("Base Chata");
			//analisarOrdem(inicio, meio, fim);
			//gc.setFill(Color.RED);
			preencherTrianguloSuperior(gc, inicio, meio, fim);
		} else if ((int)inicio.y == (int)meio.y/* || (int)inicio.y == ((int)meio.y + 1) || (int)inicio.y == ((int)meio.y - 1)*/) {
			//System.out.println("Topo Chato");
			//analisarOrdem(inicio, meio, fim);
			//gc.setFill(Color.GREEN);
			preencherTrianguloInferior(gc, inicio, meio, fim);
		} else {
			//System.out.println("Normal");
			//analisarOrdem(inicio, meio, fim);
			//gc.setFill(Color.BLUE);
			//Ponto2D v4 = new Ponto2D((double)(inicio.x + ((double)(meio.y - inicio.y) / (double)(fim.y - inicio.y)) * (fim.x - inicio.x)), meio.y);
			preencherTrianguloSuperior(gc, inicio, meio, fim);
			preencherTrianguloInferior(gc, inicio, meio, fim);
		}

	}

	public static void preencherTrianguloSuperior(GraphicsContext gc, Ponto2D inicio, Ponto2D meio, Ponto2D fim) {
		double coefRetaMin, coefRetaMax;
		if (meio.x < fim.x) {
			coefRetaMin = (meio.x - inicio.x) / (meio.y - inicio.y);
			coefRetaMax = (fim.x - inicio.x) / (fim.y - inicio.y);
		} else {
			coefRetaMin = (fim.x - inicio.x) / (fim.y - inicio.y);
			coefRetaMax = (meio.x - inicio.x) / (meio.y - inicio.y);
		}
		
		double xmax = inicio.x;
		double xmin = inicio.x;
		for (double scanlineY = inicio.y; scanlineY <= meio.y; scanlineY++) {
			//gc.setFill(Color.RED);
			gc.fillRect(xmin-1, scanlineY, Math.max(xmax+1, xmin-1) - Math.min(xmax+1, xmin-1), 2);
			xmin += coefRetaMin;
			xmax += coefRetaMax;
		}
	}

	public static void preencherTrianguloInferior(GraphicsContext gc, Ponto2D inicio, Ponto2D meio, Ponto2D fim) {
		double coefRetaMin, coefRetaMax;
		if (meio.x < inicio.x) {
			coefRetaMin = (meio.x - fim.x) / (meio.y - fim.y);
			coefRetaMax = (inicio.x - fim.x) / (inicio.y - fim.y);
		} else {
			coefRetaMin = (inicio.x - fim.x) / (inicio.y - fim.y);
			coefRetaMax = (meio.x - fim.x) / (meio.y - fim.y);
		}
		
		double xmax = fim.x;
		double xmin = fim.x;
		for (double scanlineY = fim.y; scanlineY >= meio.y; scanlineY--) {
			//gc.setFill(Color.BLUE);
			gc.fillRect(xmin-1, scanlineY, Math.max(xmax+1, xmin-1) - Math.min(xmax+1, xmin-1), 2);
			xmin -= coefRetaMin;
			xmax -= coefRetaMax;
		}
		xmin -= coefRetaMin;
		xmax -= coefRetaMax;
		
		
	}
	
	private static void analisarOrdem(Ponto2D inicio, Ponto2D meio, Ponto2D fim){
		if (inicio.y <= meio.y && meio.y <= fim.y) {
			System.out.println("ordem ok\ny1 = " + inicio.y + "\ny2 = " + meio.y + "\ny3 = " + fim.y);
		} else {
			System.err.println("Problema na ordem\ny1 = " + inicio.y + "\ny2 = " + meio.y + "\ny3 = " + fim.y);
			return;
		}
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
		double maxX = Testes.t[0].a.x, maxY = Testes.t[0].a.y;
		double minX = Testes.t[0].a.x, minY = Testes.t[0].a.y;
		for (int i = 0; i < Testes.t.length; i++) {
			maxX = Math.max(maxX, Testes.t[i].a.x);
			maxX = Math.max(maxX, Testes.t[i].b.x);
			maxX = Math.max(maxX, Testes.t[i].c.x);

			maxY = Math.max(maxY, Testes.t[i].a.y);
			maxY = Math.max(maxY, Testes.t[i].b.y);
			maxY = Math.max(maxY, Testes.t[i].c.y);

			minX = Math.min(minX, Testes.t[i].a.x);
			minX = Math.min(minX, Testes.t[i].b.x);
			minX = Math.min(minX, Testes.t[i].c.x);

			minY = Math.min(minY, Testes.t[i].a.y);
			minY = Math.min(minY, Testes.t[i].b.y);
			minY = Math.min(minY, Testes.t[i].c.y);

		}
		for (int i = 0; i < Testes.t.length; i++) {
			double pontoA[] = new double[2];
			pontoA[0] = (Testes.camera.d * Testes.t[i].a.x/Testes.t[i].a.z)/Testes.camera.hx;
			pontoA[1] = (Testes.camera.d * Testes.t[i].a.y/Testes.t[i].a.z)/Testes.camera.hy;
			pontoA[0] = Math.abs((pontoA[0] + Testes.camera.hx)/(2 * Testes.camera.hx) * xmax + 0.5);
			pontoA[1] = Math.abs(ymax - (pontoA[1] + Testes.camera.hy)/(2 * Testes.camera.hy) * ymax + 0.5);

			double pontoB[] = new double[2];
			pontoB[0] = (Testes.camera.d * Testes.t[i].b.x/Testes.t[i].b.z)/Testes.camera.hx;
			pontoB[1] = (Testes.camera.d * Testes.t[i].b.y/Testes.t[i].b.z)/Testes.camera.hy;
			pontoB[0] = Math.abs((pontoB[0] + Testes.camera.hx)/(2 * Testes.camera.hx) * xmax + 0.5);
			pontoB[1] = Math.abs(ymax - (pontoB[1] + Testes.camera.hy)/(2 * Testes.camera.hy) * ymax + 0.5);

			double pontoC[] = new double[2];
			pontoC[0] = (Testes.camera.d * Testes.t[i].c.x/Testes.t[i].c.z)/Testes.camera.hx;
			pontoC[1] = (Testes.camera.d * Testes.t[i].c.y/Testes.t[i].c.z)/Testes.camera.hy;
			pontoC[0] = Math.abs((pontoC[0] + Testes.camera.hx)/(2 * Testes.camera.hx) * xmax + 0.5);
			pontoC[1] = Math.abs(ymax - (pontoC[1] + Testes.camera.hy)/(2 * Testes.camera.hy) * ymax + 0.5);
			
			gc.setFill(Color.WHITE); // Cor do Ponto
			gc.fillRect(pontoA[0], pontoA[1], 1, 1); // Tamanho do Ponto
			gc.fillRect(pontoB[0], pontoB[1], 1, 1); // Tamanho do Ponto
			gc.fillRect(pontoC[0], pontoC[1], 1, 1); // Tamanho do Ponto
			
			BibOps.scanLine(new Ponto2D(pontoA[0], pontoA[1]), new Ponto2D(pontoB[0], pontoB[1]), new Ponto2D(pontoC[0], pontoC[1]), gc);
			
		}
		
	}
	public static void atualizarCoordVista(String arquivoSemExtensao) {
		try {
			Testes.getPontosArquivo(arquivoSemExtensao);
			for (int i = 0; i < Testes.t.length; i++) {
				BibOps.atualizaTrianguloParaCoordVista(Testes.t[i], Testes.camera);
			}
		} catch (IOException e) {e.printStackTrace();}
	}
}

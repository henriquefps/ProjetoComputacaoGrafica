package va1;

public class CameraVirtual {
	double U[][] = new double[3][1];
	double V[][] = new double[3][1];
	double N[][] = new double[3][1];

	double d, hx, hy;
	Ponto3D C;

	public void ortogonalizarV() {
		double aux = BibOps.produtoEscalar3D(V, N) / BibOps.produtoEscalar3D(N, N);

		double vaux[][] = new double[3][1];
		vaux[0][0] = aux * N[0][0];
		vaux[1][0] = aux * N[1][0];
		vaux[2][0] = aux * N[2][0];

		V[0][0] = V[0][0] - vaux[0][0];
		V[1][0] = V[1][0] - vaux[1][0];
		V[2][0] = V[2][0] - vaux[2][0];

		N = BibOps.normalizaVetor3D(N);
		V = BibOps.normalizaVetor3D(V);

		calcularU();
	}

	private void calcularU() {
		U = BibOps.produtoVetorial3D(N, V);
	}

	public double[][] retorneBaseOrtonormal() {
		double r[][] = null;
		if (U != null) {
			double aux[][] = { { U[0][0], U[1][0], U[2][0] }, { V[0][0], V[1][0], V[2][0] },
					{ N[0][0], N[1][0], N[2][0] } };
			r = aux;
		}
		return r;
	}

}

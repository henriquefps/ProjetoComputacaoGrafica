package projeto;

public class Triangulo {
	public Ponto3D vistaA, vistaB, vistaC, baricentro;
	public double[][] normal;
	public Ponto2D telaA, telaB, telaC;
	public Ponto3D original1, original2, original3;
	public int indiceX, indiceY, indiceZ;
	public Triangulo(Ponto3D a, Ponto3D b, Ponto3D c){
		this.vistaA = a;
		this.vistaA.vertice = "a";
		this.vistaB = b;
		this.vistaB.vertice = "b";
		this.vistaC = c;
		this.vistaC.vertice = "c";
	}
	
	public void setBaricentro() {
		if(vistaA != null) {
			baricentro = new Ponto3D((vistaA.x + vistaB.x + vistaC.x)/3, (vistaA.y + vistaB.y + vistaC.y)/3, (vistaA.z + vistaB.z + vistaC.z)/3);
		}
	}
	public void setOriginais() {
		original1 = vistaA;
		original2 = vistaB;
		original3 = vistaC;
	}
}

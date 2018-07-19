package va1;

public class Triangulo {
	public Ponto3D a, b, c, baricentro;
	public double[][] normal;
	public Ponto2D telaA, telaB, telaC;
	public Ponto3D original1, original2, original3;
	public Triangulo(Ponto3D a, Ponto3D b, Ponto3D c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public void setBaricentro() {
		if(a != null) {
			baricentro = new Ponto3D((a.x + b.x + c.x)/3, (a.y + b.y + c.y)/3, (a.z + b.z + c.z)/3);
		}
	}
	public void setOriginais() {
		original1 = a;
		original2 = b;
		original3 = c;
	}
	
}

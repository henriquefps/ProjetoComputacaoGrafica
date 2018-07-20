package va1;

public class Iluminacao {
	public double Ka, Ks, Eta;
	public Ponto3D Iamb, Il, Pl, Kd, Od;
	public Iluminacao(double ka, double ks, double eta, Ponto3D iamb, Ponto3D il, Ponto3D pl, Ponto3D kd, Ponto3D od) {
		super();
		Ka = ka;
		Ks = ks;
		Eta = eta;
		Iamb = iamb;
		Il = il;
		Pl = pl;
		Kd = kd;
		Od = od;
	}
	 
	
	public Iluminacao() {
		
	}

	public double getKa() {
		return Ka;
	}

	public void setKa(double ka) {
		Ka = ka;
	}

	public double getKs() {
		return Ks;
	}

	public void setKs(double ks) {
		Ks = ks;
	}

	public double getEta() {
		return Eta;
	}

	public void setEta(double eta) {
		Eta = eta;
	}

	public Ponto3D getIamb() {
		return Iamb;
	}

	public void setIamb(Ponto3D iamb) {
		Iamb = iamb;
	}

	public Ponto3D getIl() {
		return Il;
	}

	public void setIl(Ponto3D il) {
		Il = il;
	}

	public Ponto3D getPl() {
		return Pl;
	}

	public void setPl(Ponto3D pl) {
		Pl = pl;
	}

	public Ponto3D getKd() {
		return Kd;
	}

	public void setKd(Ponto3D kd) {
		Kd = kd;
	}

	public Ponto3D getOd() {
		return Od;
	}

	public void setOd(Ponto3D od) {
		Od = od;
	}
	
	public void atualizarParametros(double Ka, double Ks, double eta, Ponto3D pl, Ponto3D il, Ponto3D iamb, Ponto3D Kd, Ponto3D od) {
		this.Ka = Ka;
		this.Ks = Ks;
		this.Eta = eta;
		this.Pl = pl;
		this.Il = il;
		this.Iamb = iamb;
		this.Kd = Kd;
		this.Od = od;
	}
	
	
	
}

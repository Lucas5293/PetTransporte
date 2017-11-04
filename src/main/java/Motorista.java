

public class Motorista {
	private long id;
	private String nome;
	private String petshop;
	private int raio;
	private float latitude;
	private float longitude;
	
	
	public Motorista(long id, String nome, String petshop, int raio, float latitude, float longitude) {
		super();
		this.id = id;
		this.nome = nome;
		this.petshop = petshop;
		this.raio = raio;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	


	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public String getNome() {
		return nome;
	}




	public void setNome(String nome) {
		this.nome = nome;
	}




	public String getPetshop() {
		return petshop;
	}




	public void setPetshop(String petshop) {
		this.petshop = petshop;
	}




	public int getRaio() {
		return raio;
	}




	public void setRaio(int raio) {
		this.raio = raio;
	}




	public float getLatitude() {
		return latitude;
	}




	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}




	public float getLongitude() {
		return longitude;
	}




	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}




	public String toString() {
		return 	"Id: " + getId() + 
				"\nNome: "+getNome()+
				"\nPet Shop: "+getPetshop()+
				"\nRaio de pesquisa (km): "+getRaio()+
				"\nLatitude: "+getLatitude()+
				"\nLongitude: "+getLongitude();
	}
	
	
	
	
	
	
}

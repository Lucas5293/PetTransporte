
public class Cachorro {
	private long id;
	private String nome;
	private String especie;
	private String raca;
	private int servico;
	private float latitude;
	private float longitude;
	
	public Cachorro(long id, String nome, String especie, String raca, int servico, float latitude, float longitude) {
		super();
		this.id = id;
		this.nome = nome;
		this.especie = especie;
		this.raca = raca;
		this.latitude = latitude;
		this.longitude = longitude;
		this.servico = servico;
	}
	
	public int getServico() {
		return servico;
	}

	public void setServico(int servico) {
		this.servico = servico;
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

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
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
				"\nEspecie: "+getEspecie()+
				"\nRaça: "+getRaca()+
				"\nServico: "+getServico()+
				"\nLatitude: "+getLatitude()+
				"\nLongitude: "+getLongitude();
	}
	
	
	
}

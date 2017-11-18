
import java.util.ArrayList;

import com.pengrad.telegrambot.model.Update;

public class Model implements Subject{
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	public ArrayList<Cachorro> cachorros = new ArrayList<Cachorro>();
	public ArrayList<Motorista> motoristas = new ArrayList<Motorista>();
	
	private BuscaPet buscaPet = new BuscaPet(this);	
	
	private Calculo calculo=new Calculo();	
	
	private static Model uniqueInstance;
	
	private Model(){}
	
	public static Model getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}
	
	public void registerObserver(Observer observer){
		observers.add(observer);
	}
	
	public void notifyObservers(long chatId,String data){
		for(Observer observer:observers){
			observer.update(chatId, data);
		}
	}
	public void notifyObservers(long chatId,float lat, float lon){
		for(Observer observer:observers){
			observer.updateLocation(chatId, lat, lon);
		}
	}
	
	
	public Calculo getCalculo() {
		return calculo;
	}
	
	public void addCachorro(Cachorro cachorro){
		this.cachorros.add(cachorro);
		System.out.println("\nNovo dog: \n"+cachorro.toString());
	}
	
	public void addMotorista(Motorista motorista){
		this.motoristas.add(motorista);
		System.out.println("\nNovo motorista: \n"+motorista.toString());
	}
	public void removeCachorro(Cachorro cachorro){
		this.cachorros.remove(cachorro);
	}
	
	public void removeMotorista(Motorista motorista){
		this.motoristas.remove(motorista);
	}
	
	public Cachorro searchCachorroIdGet(Update update) {
		for(Cachorro cachorro: cachorros)
			if(cachorro.getId() == update.message().chat().id())
				return cachorro;
		return null;
	}
	
	public void searchCachorroId(Update update){
		long cachorrosData = -1;
		for(Cachorro cachorro: cachorros)
			if(cachorro.getId() == update.message().chat().id())
				cachorrosData = cachorro.getId();
		
		if(cachorrosData != -1)
			this.notifyObservers(update.message().chat().id(), String.valueOf(cachorrosData));
		else 
			this.notifyObservers(update.message().chat().id(), "Cachorro não encontrado");
	}
	
	public ArrayList<Cachorro> searchCachorroDist(Update update, Motorista motorista) {
		int index=1;
		ArrayList<Cachorro> retorno = new ArrayList<>();
		
		if (searchMotoristaIdGet(update)==null)
			return null;
		
		for(Cachorro cachorro: cachorros) {
			if (getCalculo().distanciaEntrePontos(cachorro.getLatitude(), cachorro.getLongitude(),
			motorista.getLatitude(), motorista.getLongitude())<=motorista.getRaio()) {
					Object descri [] = cachorro.toStringWithLocation();
					this.notifyObservers(update.message().chat().id(),index+" "+descri[0]);
					this.notifyObservers(update.message().chat().id(), (float) descri[1], (float) descri[2]);
					index+=1;
					retorno.add(cachorro);
			}
		}
		if (index==1)
			this.notifyObservers(update.message().chat().id(),"Não foram encontrados pets em seu raio de pesquisa");
		return retorno;
	}
	
	public Motorista searchMotoristaIdGet(Update update) {
		for(Motorista motorista: motoristas)
			if(motorista.getId() == update.message().chat().id())
				return motorista;
		return null;
	}
	
	public void searchMotoristaId(Update update){
		long motoristasData = -1;
		for(Motorista motorista: motoristas)
			if(motorista.getId() == update.message().chat().id()) 
				motoristasData = motorista.getId();
		
		if(motoristasData != -1)
			this.notifyObservers(update.message().chat().id(), String.valueOf(motoristasData));
		else 
			this.notifyObservers(update.message().chat().id(), "Motorista not found");
	}
	
	public BuscaPet getBuscaPet() {
		return buscaPet;
	}
	


}

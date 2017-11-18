
import java.util.ArrayList;

import com.pengrad.telegrambot.model.Update;

public class Model implements Subject{
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	private ArrayList<Cachorro> cachorros = new ArrayList<Cachorro>();
	private ArrayList<Motorista> motoristas = new ArrayList<Motorista>();
	
	private BuscaPet buscaPet = new BuscaPet();	
	
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
		for(Cachorro cachorro: cachorros) {
			if (getCalculo().distanciaEntrePontos(cachorro.getLatitude(), cachorro.getLongitude(),
			motorista.getLatitude(), motorista.getLongitude())<=motorista.getRaio()) {
					this.notifyObservers(update.message().chat().id(),index+" "+cachorro.toString());
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

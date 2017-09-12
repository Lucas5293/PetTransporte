import java.util.ArrayList;

import com.pengrad.telegrambot.model.Update;

public class Model implements Subject{
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	private ArrayList<Cachorro> cachorros = new ArrayList<Cachorro>();
	private ArrayList<Motorista> motoristas = new ArrayList<Motorista>();
	
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
	
	public void notifyObservers(long chatId,String cachorrosData){
		for(Observer observer:observers){
			observer.update(chatId, cachorrosData);
		}
	}
	
	public void addCachorro(Cachorro cachorro){
		this.cachorros.add(cachorro);
		System.out.println("\nNovo dog: \n"+cachorro.toString());
	}
	
	public void addMotorista(Motorista motorista){
		this.motoristas.add(motorista);
		System.out.println("\nNovo motorista: \n"+motorista.toString());
	}
	
	public void searchCachorro(Object objects[], Update update){
		long cachorrosData = -1;
		for(Cachorro cachorro: cachorros){
			if(cachorro.getNome().equals((String) objects[0])){
				cachorrosData = cachorro.getId();
			}
		}
		
		if(cachorrosData != -1){
			this.notifyObservers(update.message().chat().id(), String.valueOf(cachorrosData));
		} else {
			this.notifyObservers(update.message().chat().id(), "Cachorro não encontrado");
		}
		
	}
	
	public void searchMotorista(Object objects[], Update update){
		long motoristasData = -1;
		for(Motorista motorista: motoristas){
			if(motorista.getNome().equals((String) objects[0])) 
				motoristasData = motorista.getId();
		}
		
		if(motoristasData != -1){
			this.notifyObservers(update.message().chat().id(), String.valueOf(motoristasData));
		} else {
			this.notifyObservers(update.message().chat().id(), "Motorista not found");
		}
	}

}

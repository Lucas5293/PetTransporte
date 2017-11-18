import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendLocation;
import com.pengrad.telegrambot.request.SendMessage;

public class BuscaPet {
	private Map<Long, ArrayList<Cachorro>> disponiveis = new HashMap<>();
	private ArrayList<long []> busca = new ArrayList<>();
	private Model model;
	
	public BuscaPet(Model model) {
		this.model = model;
	}
	
	public void setDisponiveis(long id, ArrayList<Cachorro> caes){
		disponiveis.put(id, caes);
	}
	
	public void addBusca(long motorista, long cachorro) {
		long[]  t = {motorista, cachorro}; 
		busca.add(t);
	}
	
	public long cancelaBuscaMotorista(long motorista) {
		for(long [] c : busca) {
			if (c[0]==motorista) {
				busca.remove(c);
				return c[1];
			}
		}
		return -1;
	}
	public long cancelaBuscaCachorro(long cachorro) {
		for(long [] c : busca) {
			if (c[1]==cachorro) {
				busca.remove(c);
				return c[0];
			}
		}
		return -1;
	}
	
	public ArrayList<Cachorro> getDisponiveis(long id){
		if (disponiveis.containsKey(id))
			return disponiveis.get(id);
		else
			return null;
	}
	
	public void atualizaLocalizacao(long motorista, float lat, float lon, View view) {
		for(long [] c : busca) {
			if (c[0]==motorista) {
				view.bot.execute(new SendMessage(c[1],"O motorista mandou uma atualização da localização:"));
				view.bot.execute(new SendLocation(c[1], lat, lon));
			}
		}
	}
	
}

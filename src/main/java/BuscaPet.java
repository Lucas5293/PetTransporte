import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuscaPet {
	private Map<Long, ArrayList<Cachorro>> disponiveis = new HashMap<>();
	
	public void setDisponiveis(long id, ArrayList<Cachorro> caes){
		disponiveis.put(id, caes);
	}
	public ArrayList<Cachorro> getDisponiveis(long id){
		if (disponiveis.containsKey(id))
			return disponiveis.get(id);
		else
			return null;
	}
	
}

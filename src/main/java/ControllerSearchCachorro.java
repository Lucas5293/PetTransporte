import com.pengrad.telegrambot.model.Update;

public class ControllerSearchCachorro implements ControllerSearch{
	
	
	private Model model;
	private View view;
	
	public ControllerSearchCachorro(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	
	public void searchId(Update update){
		view.sendTypingMessage(update);
		model.searchCachorroId(update);
	}
	public void searchDist(Update update) {
		view.sendTypingMessage(update);
		view.sendTypingMessage(update);
		Motorista motorista = model.searchMotoristaIdGet(update);
		model.searchCachorroDist(update, motorista);
	}

}

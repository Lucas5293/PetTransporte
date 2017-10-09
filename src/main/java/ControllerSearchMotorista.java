import com.pengrad.telegrambot.model.Update;

public class ControllerSearchMotorista implements ControllerSearch{
	
	private Model model;
	private View view;
	
	public ControllerSearchMotorista(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	
	public void searchId(Update update){
		view.sendTypingMessage(update);
		model.searchMotoristaId(update);
	}
	
	public void searchDist(Update update) {
		view.sendTypingMessage(update);
		
	}

}

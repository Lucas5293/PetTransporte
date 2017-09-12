import com.pengrad.telegrambot.model.Update;

public class ControllerSearchMotorista implements ControllerSearch{
	
	private Model model;
	private View view;
	
	public ControllerSearchMotorista(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	
	public void search(Object objects[], Update update){
		//view.sendTypingMessage(update);
		model.searchMotorista(objects, update);
	}

}

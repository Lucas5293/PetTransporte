import com.pengrad.telegrambot.model.Update;

public class ControllerSearchCachorro implements ControllerSearch{
	
	
	private Model model;
	private View view;
	
	public ControllerSearchCachorro(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	
	public void search(Object objects[], Update update){
		view.sendTypingMessage(update);
		model.searchCachorro(objects, update);
	}

}

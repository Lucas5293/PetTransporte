import com.pengrad.telegrambot.model.Update;

public class ControllerRegisterMotorista implements ControllerRegister{
	private Model model;
	private View view;
	
	public ControllerRegisterMotorista(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	
	public void add(Object objects[], Update update){
		view.sendTypingMessage(update);
		model.addMotorista(
			new Motorista(update.message().chat().id(),
					(String) objects[0],
					(String) objects[1],
					Integer.valueOf( (String) objects[2]),
					(float) objects[3],
					(float) objects[4]));
	}
	public void remove(Object objects[], Update update){
		view.sendTypingMessage(update);
		model.searchCachorro(objects, update);
	}
}

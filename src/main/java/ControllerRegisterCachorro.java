import com.pengrad.telegrambot.model.Update;

public class ControllerRegisterCachorro implements ControllerRegister{

	private Model model;
	private View view;
	
	public ControllerRegisterCachorro(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}	
	
	public void add(Object objects[], Update update){
		view.sendTypingMessage(update);
		model.addCachorro(
				new Cachorro(update.message().chat().id(),
						(String) objects[0],
						(String) objects[1],
						(String) objects[2],
						(int) objects[3],
						(float) objects[4],
						(float) objects[5]));
	}
	public void remove(Update update){
		view.sendTypingMessage(update);
		model.removeCachorro(model.searchCachorroIdGet(update));
		view.update(update.updateId(), "Registro do pet excluido");
	}
}

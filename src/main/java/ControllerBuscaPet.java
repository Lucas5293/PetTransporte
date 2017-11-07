import java.util.ArrayList;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.model.Update;

public class ControllerBuscaPet {
	private Model model;
	private View view;
	
	public ControllerBuscaPet(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	public void buscarCao(Update update,long motorista, int cao) {
		ArrayList<Cachorro> caes = model.getDisponiveis(motorista);
		view.bot.execute(new SendMessage(caes.get(cao-1).getId(),"Um motorista já está indo buscar seu animal"));
		view.bot.execute(new SendMessage(update.message().chat().id(),"Vá buscar o animal, animal"));
	}
}
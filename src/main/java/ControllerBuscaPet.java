import java.util.ArrayList;

import com.pengrad.telegrambot.request.SendMessage;

public class ControllerBuscaPet {
	private Model model;
	private View view;
	
	public ControllerBuscaPet(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	public void buscarCao(long motorista, int cao) {
		ArrayList<Cachorro> caes = model.getDisponiveis(motorista);
		view.bot.execute(new SendMessage(caes.get(cao).getId(),"Um motorista já está indo buscar seu cão"));
	}
}

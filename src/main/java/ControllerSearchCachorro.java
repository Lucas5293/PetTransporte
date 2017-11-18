import java.util.ArrayList;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

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
		Motorista motorista = model.searchMotoristaIdGet(update);
		if (motorista == null) {
			view.bot.execute(new SendMessage(update.message().chat().id(),"Não há cadastro do motorista!"));
			view.modo.put(update.message().chat().id(), "motorista");
			view.telaMotorista(update);
		}
		else {
			ArrayList<Cachorro> caes = model.searchCachorroDist(update, motorista);
			model.getBuscaPet().setDisponiveis(update.message().chat().id(), caes);
			if (caes.size()!=0)
				view.modo.put(update.message().chat().id(), "motorista/2/"+caes.size());
			view.bot.execute(new SendMessage(update.message().chat().id(),"Digite o número do pet que deseja buscar"+
					"\nOu voltar para retornar a ala de pets"));
			
		}
	}

}

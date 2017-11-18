import java.util.ArrayList;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.model.Update;

public class ControllerBuscaPet implements ControllerBusca {
	private Model model;
	private View view;
	
	public ControllerBuscaPet(Model model, View view){
		this.model = model; //connection Controller -> Model
		this.view = view; //connection Controller -> View
	}
	public void buscarCao(Update update,long motorista, int cao) {
		ArrayList<Cachorro> caes = model.getBuscaPet().getDisponiveis(motorista);
		
		model.getBuscaPet().addBusca(motorista, caes.get(cao-1).getId());
		
		view.bot.execute(new SendMessage(caes.get(cao-1).getId(),"Um motorista já está indo buscar seu animal"));
		view.bot.execute(new SendMessage(update.message().chat().id(),"Vá buscar o animal"));
	}
	
	public void cancelarCaoMotorista(long motorista) {
		long cao = model.getBuscaPet().cancelaBuscaMotorista(motorista);
		view.bot.execute(new SendMessage(cao,"O motorista cancelou a busca de teu pet!"));
		view.bot.execute(new SendMessage(motorista,"Você cancelou a busca do pet"));
	}
	
	public void cancelarCaoPet(long cao) {
		long motorista = model.getBuscaPet().cancelaBuscaCachorro(cao);
		view.bot.execute(new SendMessage(motorista,"O cliente cancelou a busca do pet!"));
		view.bot.execute(new SendMessage(cao,"Você cancelou a busca do pet"));
		
	}
	
	public void atualizaLocalizacao(Update update, float lat, float lon) {
		model.getBuscaPet().atualizaLocalizacao(update.message().chat().id(), lat, lon, view);
		view.bot.execute(new SendMessage(update.message().chat().id(),"A localização foi atualizada para todos os clientes"));
	}
}
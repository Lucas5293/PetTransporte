
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Location;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendLocation;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class View implements Observer{

	
	TelegramBot bot = TelegramBotAdapter.build("448502627:AAGcm8FGS3ZoojKipFmNGHN5lLwI53c0KWU");

	//Object that receives messages
	GetUpdatesResponse updatesResponse;
	//Object that send responses
	SendResponse sendResponse;
	//Object that manage chat actions like "typing action"
	BaseResponse baseResponse;
			
	
	int queuesIndex=0;
	
	ControllerSearch controllerSearch; //Strategy Pattern -- connection View -> Controller
	ControllerRegister controllerRegister; //Strategy Pattern -- connection View -> Controller
	ControllerBuscaPet controllerBuscaPet;
	
	Map<Long, String> modo = new HashMap<Long, String>();
	boolean getLocalizacao = false;
	
	private Model model;
	
	public View(Model model){
		this.model = model; 
	}
	
	public void setControllerSearch(ControllerSearch controllerSearch){ //Strategy Pattern
		this.controllerSearch = controllerSearch;
	}
	public void setControllerRegister(ControllerRegister controllerRegister){ //Strategy Pattern
		this.controllerRegister = controllerRegister;
	}
	
	
	public void receiveUsersMessages() {

		Map<Long, Object []> conteudo = new HashMap<Long, Object []>();
		
		//infinity loop
		while (true){
			
			//taking the Queue of Messages
			updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(queuesIndex));
			
			//Queue of messages
			List<Update> updates = updatesResponse.updates();
			
			
			//taking each message in the Queue
			for (Update update : updates) {
				//updating queue's index
				queuesIndex = update.updateId()+1;
				
				try {
					
					long id = update.message().chat().id();
					
					if (!modo.containsKey(id)) {
						System.out.println("Novo user: "+id);
						modo.put(id,"");
					}
						
					if(!getLocalizacao && update.message().text().equals("/start"))
						telaInicial(update);
					/**
					 *  PET menu
					 */
					else if(modo.get(id).equals("pet")){
						switch(update.message().text().toLowerCase()){
							case "1": { 
								modo.put(id, "pet/1");
								setControllerRegister(new ControllerRegisterCachorro(model, this));
								callControllerRegister(null, update, 1);
								bot.execute(new SendMessage(update.message().chat().id(),"Digite o nome de identificação do pet, espécie e raça, respectivamente (separado por vírgula)\n"+
								"Ou voltar para retornar a ala de pets"));
								break;
							}
							case "2": { 
								modo.put(id, "pet/2"); 
								bot.execute(new SendMessage(update.message().chat().id(),"Digite sim caso queira cancelar a busca\n"+
										"Ou voltar para retornar a ala de pets"));
								break;
							}
							case "voltar": {
								modo.put(id, "");
								telaInicial(update);
								break;
							}
							default: {
								bot.execute(new SendMessage(update.message().chat().id(),"Digite apenas uma opção válida"));
							}
						}
					/**
					 * Motorista Menu
					 */
					}else if(modo.get(id).equals("motorista")){
						switch(update.message().text().toLowerCase()){
							case "1": {
								modo.put(id, "motorista/1");
								setControllerRegister(new ControllerRegisterMotorista(model, this));
								callControllerRegister(null, update, 1);
								bot.execute(new SendMessage(update.message().chat().id(),"Digite o nome do motorista, nome do pet shop e o raio de pesquisa (km), respectivamente (separado por vírgula)"+
										"\nOu voltar para retornar a ala de pets"));
								break;
							}
							case "2": {
								modo.put(id, "motorista/2");
								setControllerSearch(new ControllerSearchCachorro(model, this));
								callControllerSearch(null, update, 2);
								break;
							}
							case "3": { 
								modo.put(id, "motorista/3");
								bot.execute(new SendMessage(update.message().chat().id(),"Digite sim caso queira cancelar a busca\n"+
										"Ou voltar para retornar a ala de motorista"));
								break;
								}
							case "4": { 
								modo.put(id, "motorista/4");
								bot.execute(new SendMessage(update.message().chat().id(),"Mande sua localização atual, para atualizar os clientes\n"+
										"Ou voltar para retornar a ala de motorista"));
								getLocalizacao=true;
								break;
								}
							case "voltar": {
								modo.put(id, "");
								telaInicial(update);
								break;
							}
							default:{
								bot.execute(new SendMessage(update.message().chat().id(),"Digite apenas uma opção válida"));
							}
						}
					}else if(!getLocalizacao && update.message().text().toLowerCase().equals("pet")){					
						telaPet(update);
						modo.put(id, "pet");
						
					}else if(!getLocalizacao && update.message().text().toLowerCase().equals("motorista")){
						telaMotorista(update);
						modo.put(id, "motorista");
						
					}else if(!getLocalizacao && update.message().text().toLowerCase().equals("debug")){
						
					}
					
					/**
					 *  Pet Sub-Opções
					 */
					
					else if(modo.get(id).equals("pet/1")){
						String response = update.message().text();
						if (response.toLowerCase().equals("voltar")) {
							telaPet(update);
							modo.put(id, "pet");
						}
						else {
							String responseL [] = response.toLowerCase().replace(" ","").split(",");
							if (responseL.length!=3) {
								bot.execute(new SendMessage(update.message().chat().id(),"Estrutura errada!"));
								bot.execute(new SendMessage(update.message().chat().id(),"Digite o nome de identificação do pet, espécie e raça, respectivamente (separado por vírgula)"));
								bot.execute(new SendMessage(update.message().chat().id(),"Ou voltar para retornar a ala de pets"));
							}
							else
							{
								conteudo.put(id, responseL);
								bot.execute(new SendMessage(update.message().chat().id(),"Digite uma opção válida"+
										"\n1 - Banho"+
										"\n2 - Tosa"+
										"\n3 - Banho e Tosa"+
										"\n4 - Banho e Tosa com Opcionais"+
										"\n5 - Tosa higiênica"+
										"\n6 - Banho medicinal"));
								modo.put(id, "pet/1/1");
							}
						}
						
					}
					else if(modo.get(id).equals("pet/1/1")){
						String response = update.message().text();
						if ("123456".contains(response) && response.length()==1) {
							String responseL [] = (String []) conteudo.get(id); 
							conteudo.put(id, new Object [] {responseL[0], responseL[1], responseL[2], Integer.valueOf(response)});// conteudo.get(id); 
							bot.execute(new SendMessage(update.message().chat().id(),"Nome: "+responseL[0]
									+"\nEspécie: "+responseL[1]+
									"\nRaça: "+responseL[2]+
									"\nServiço: "+ response));
							
							bot.execute(new SendMessage(update.message().chat().id(),"Para finalizar envie sua localização, através do chat"));
							modo.put(id, "pet/1/1/1");
							getLocalizacao = true;
							
							
							
						}				
						else
						{
								bot.execute(new SendMessage(update.message().chat().id(),"Digite uma opção válida"+
								"\n1 - Banho"+
								"\n2 - Tosa"+
								"\n3 - Banho e Tosa"+
								"\n4 - Banho e Tosa com Opcionais"+
								"\n5 - Tosa higiênica"+
								"\n6 - Banho medicinal"));
						}
					}
					else if(modo.get(id).equals("pet/1/1/1")){
						try {
							Location localizacao = update.message().location();
							float lat = localizacao.latitude();
							float lon = localizacao.longitude();
							getLocalizacao=false;
							Object responseL [] = conteudo.get(id); 
							conteudo.put(id, new Object [] {responseL[0], responseL[1], responseL[2], responseL[3], lat, lon});
						}catch(Exception e) {
							bot.execute(new SendMessage(update.message().chat().id(),"Você não enviou uma localização\nPara finalizar envie sua localização, através do chat"));
							continue;
						}
						
						this.callControllerRegister(conteudo.get(id), update, 0);
						bot.execute(new SendMessage(update.message().chat().id(),"Em minutos um motorista irá buscar seu pet\n"+
						"Você pode cancelar o pedido através do menu Cancelar busca do pet"));
						modo.put(id, "pet");
						telaPet(update);
						
					}
					else if(modo.get(id).equals("pet/2")){
						String response = update.message().text();
						if (response.toLowerCase().equals("sim")) {
							controllerBuscaPet = new ControllerBuscaPet(model,this);
							controllerBuscaPet.cancelarCaoPet(id);
							setControllerRegister(new ControllerRegisterCachorro(model, this));
							callControllerRegister(null, update, 1);
							modo.put(id, "pet");
							telaPet(update);
						}
						else if (response.toLowerCase().equals("voltar")) {
							telaPet(update);
							modo.put(id, "pet");
						}		
						else {
							bot.execute(new SendMessage(update.message().chat().id(),"Digite uma opção válida"));
							bot.execute(new SendMessage(update.message().chat().id(),"Digite sim caso queira cancelar a busca\n"+
									"Ou voltar para retornar a ala de pets"));
						}
							
					}
					
					
					/**
					 *  Motorista Sub-Opções
					 */
					
					else if(modo.get(id).equals("motorista/1")){
						String response = update.message().text();
						
						if (response.toLowerCase().equals("voltar")) {
							telaMotorista(update);
							modo.put(id, "motorista");
						}
						else {
							String responseL [] = response.toLowerCase().replace(" ","").split(",");
							try {
								Integer.valueOf((String) responseL[2]);
							}
							catch(Exception e) {
								bot.execute(new SendMessage(update.message().chat().id(),"Estrutura errada!"+
										"\nDigite o nome do motorista, nome do pet shop e o raio de pesquisa (km), respectivamente (separado por vírgula)"+
										"\nOu voltar para retornar a ala de pets"));
								continue;
							}
							if (responseL.length!=3) {
								bot.execute(new SendMessage(update.message().chat().id(),"Estrutura errada!"+
								"\nDigite o nome do motorista, nome do pet shop e o raio de pesquisa (km), respectivamente (separado por vírgula)"+
								"\nOu voltar para retornar a ala de pets"));
							}
							else
							{
								conteudo.put(id, new Object [] {responseL[0], responseL[1], responseL[2]});
								bot.execute(new SendMessage(update.message().chat().id(),"Nome: "+responseL[0]
										+"\nPet Shop: "+responseL[1]+
										"\nRaio de pesquisa (km): "+responseL[2]));
								bot.execute(new SendMessage(update.message().chat().id(),"Para finalizar envie sua localização, através do chat"));
								modo.put(id, "motorista/1/1");
								getLocalizacao = true;
							}
						}
					}
					else if(modo.get(id).equals("motorista/1/1")){
						try {
							Location localizacao = update.message().location();
							float lat = localizacao.latitude();
							float lon = localizacao.longitude();
							System.out.println(lat +" "+ lon);
							getLocalizacao=false;
							Object responseL [] = conteudo.get(id); 
							conteudo.put(id, new Object [] {responseL[0], responseL[1], responseL[2], lat, lon});
						}catch(Exception e) {
							System.err.println(e);
							bot.execute(new SendMessage(update.message().chat().id(),"Você não enviou uma localização\nPara finalizar envie sua localização, através do chat"));
							continue;
						}
						
						this.callControllerRegister(conteudo.get(id), update, 0);
						bot.execute(new SendMessage(update.message().chat().id(),"O motorista foi cadastrado!\nPesquise pets disponíveis para começar"));
						modo.put(id, "motorista");
						telaMotorista(update);
						
					}
					else if(modo.get(id).length()>=11 && modo.get(id).substring(0,11).equals("motorista/2")){
						if (update.message().text().toLowerCase().equals("voltar")) {
							telaMotorista(update);
							modo.put(id, "motorista");
						}
						else if (Integer.valueOf(update.message().text())<=Integer.valueOf(modo.get(id).substring(12))
								&& Integer.valueOf(update.message().text())>0) {
							controllerBuscaPet = new ControllerBuscaPet(model,this);
							controllerBuscaPet.buscarCao(update,id,Integer.valueOf(update.message().text()));
							modo.put(id, "motorista");
							telaMotorista(update);
						}
						else {
							bot.execute(new SendMessage(update.message().chat().id(),"Erro: digite o numero correspondente ao pet que deseja buscar"+
									"\nOu voltar para retornar a ala de pets"));
						}
					}
					else if(modo.get(id).equals("motorista/3")){
						String response = update.message().text();
						if (response.toLowerCase().equals("sim")) {
							controllerBuscaPet = new ControllerBuscaPet(model,this);
							controllerBuscaPet.cancelarCaoMotorista(id);
							modo.put(id, "motorista");
							telaMotorista(update);
						}
						else if (response.toLowerCase().equals("voltar")) {
							telaMotorista(update);
							modo.put(id, "motorista");
						}		
						else {
							bot.execute(new SendMessage(update.message().chat().id(),"Digite uma opção válida"));
							bot.execute(new SendMessage(update.message().chat().id(),"Digite sim caso queira cancelar a busca\n"+
									"Ou voltar para retornar a ala de motorista"));
						}
							
					}
					else if (modo.get(id).equals("motorista/4")) {
						try {
							Location localizacao = update.message().location();
							float lat = localizacao.latitude();
							float lon = localizacao.longitude();
							getLocalizacao=false;
							controllerBuscaPet = new ControllerBuscaPet(model,this);
							controllerBuscaPet.atualizaLocalizacao(update, lat, lon);
							telaMotorista(update);
							modo.put(id, "motorista");
						}catch(Exception e) {
							bot.execute(new SendMessage(update.message().chat().id(),"Você não enviou uma localização\nEnvie sua localização, através do chat"));
							continue;
						}
					}
					else {
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Digite pet ou motorista para selecionar a opção desejada"));
					}
				}catch(Exception e) {
					System.out.println("Perda de mensagem:");
					System.err.println(e);
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Ocorreu um erro no envio\nVerifique se a informação enviada está coerente e envie novamente"));
				}
			}

		}
		
		
	}
	public void telaInicial(Update update) {
		bot.execute(new SendMessage(update.message().chat().id(),"Bem vindo! \ud83d\udc36 \ud83d\udc31 "+
				"\n- Se você desejar que o serviço delivery de pet-shop,"+ 
				"busque seu animal, digite pet"+
				"\n- Caso contrário, digite motorista para acessar as opções"));
	}
	
	public void telaPet(Update update) {
		bot.execute(new SendMessage(update.message().chat().id(),"1 - Solicitar busca do pet"+
			"\n2 - Cancelar busca do pet"+
			"\nVoltar - Para voltar as opções iniciais"));
	}
	
	public void telaMotorista(Update update) {
		bot.execute(new SendMessage(update.message().chat().id(),"1 - Cadastrar motorista"+
				"\n2 - Ver pets próximos"+
				"\n3 - Cancelar busca do pet"+
				"\n4 - Atualizar localização"+
				"\nVoltar - Para voltar as opções iniciais"));
	}
	
	public void callControllerRegister(Object objects [], Update update, int mode){
		if (mode==0)
			this.controllerRegister.add(objects, update);
		else if (mode==1)
			this.controllerRegister.remove(update);
	}
	public void callControllerSearch(Object objects[],Update update, int mode){
		if (mode==0)
			this.controllerSearch.searchId(update);
		else if (mode==2)
			this.controllerSearch.searchDist(update);
	}
	
	public void update(long chatId, String data){
		sendResponse = bot.execute(new SendMessage(chatId, data));
	}
	public void updateLocation(long chatId, float lat, float lon) {
		sendResponse = bot.execute(new SendLocation(chatId, lat, lon));		
	}
	public void sendTypingMessage(Update update){
		baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
	}
	

}
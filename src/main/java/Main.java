public class Main {

	private static Model model;
	
	public static void main(String[] args) {
		
		model = Model.getInstance();
		initializeModel(model);
		View view = new View(model);
		model.registerObserver(view); //connection Model -> View
		view.receiveUsersMessages();
		

	}
	
	public static void initializeModel(Model model){
		//model.addCachorro(new Cachorro(1,"Toba",1,"Cão","Avenida 1"));
		//model.addCachorro(new Cachorro(2,"Zeca",2,"Cão","Rua dos Bandeirantes"));
		
		//model.addMotorista(new Motorista(1,"Gilberto", "(12) 981273123", 26, "-1234"));
	}
	
}
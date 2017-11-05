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
		model.addCachorro(new Cachorro(21323212, "Lupita","Caozineo","Felps",5,Float.valueOf("-23.3025681"),Float.valueOf("-45.9647224")));
		model.addCachorro(new Cachorro(21323215, "Lolita","Caozineo","Zikas",5,Float.valueOf("-23.3025681"),Float.valueOf("-45.9647224")));
		model.addCachorro(new Cachorro(21323215, "Prenhazinha","Caozineo","Bhas",5,Float.valueOf("-23.3025681"),Float.valueOf("-45.9647224")));
	}
	
}
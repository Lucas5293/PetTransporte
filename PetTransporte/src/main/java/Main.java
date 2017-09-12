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
	public static double calcula(double lat1, double lon1, double lat2, double lon2) {
		
		int R = 6371; // metres
		double φ1 = Math.toRadians(lat1);
		double φ2 = Math.toRadians(lat2);
		double Δφ = Math.toRadians(lat2-lat1);
		double Δλ = Math.toRadians(lon2-lon1);

		double a = (Math.sin(Δφ/2) * Math.sin(Δφ/2) +
		        Math.cos(φ1) * Math.cos(φ2) *
		        Math.sin(Δλ/2) * Math.sin(Δλ/2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

		return R * c;
	}

}
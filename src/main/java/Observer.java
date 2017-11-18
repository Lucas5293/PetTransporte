
public interface Observer {

	public void update(long chatId, String data);
	public void updateLocation(long chatId, float lat, float lon);
	
}

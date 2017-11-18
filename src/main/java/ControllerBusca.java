import com.pengrad.telegrambot.model.Update;

public interface ControllerBusca {
	public void buscarCao(Update update, long motorista, int cao);
	public void cancelarCaoMotorista(long motorista);
	public void cancelarCaoPet(long motorista);
}

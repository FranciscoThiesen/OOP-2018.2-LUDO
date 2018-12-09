import com.inf1636_1611854_1310451.game.Ludo;
import com.inf1636_1611854_1310451.ui.UIHandler;

public class Main {
    public static void main(String[] args) {
    	Ludo ludo = Ludo.getInstance();
    	@SuppressWarnings("unused")
		UIHandler uiHandler = new UIHandler();
    	ludo.startGame();
    }
}

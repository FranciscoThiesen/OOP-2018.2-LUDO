import java.util.*;

import com.inf1636_1611854_1310451.game.BoardSquare;
import com.inf1636_1611854_1310451.game.Die;
import com.inf1636_1611854_1310451.game.Ludo;
import com.inf1636_1611854_1310451.game.Piece;
import com.inf1636_1611854_1310451.game.PiecePositioningInfo;
import com.inf1636_1611854_1310451.game.Player;
import com.inf1636_1611854_1310451.ui.UIHandler;

public class Main {
    public static void main(String[] args) {
    	Ludo ludo = Ludo.getInstance();
    	UIHandler uiHandler = new UIHandler();
    	
    	ludo.onStateChange.attach((Ludo ludoInstance) -> { uiHandler.onGameStateChange(); });

    	uiHandler.onDieRollButtonClick.attach(() -> {ludo.rollDie();});
    	uiHandler.onNextTurnButtonClick.attach(() -> {ludo.endTurn();});
    	uiHandler.onBoardSquareClick.attach((BoardSquare b) -> {ludo.clickBoardSquare(b);});
    }
}

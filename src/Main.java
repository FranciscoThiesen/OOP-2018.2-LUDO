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
    	
    	ludo.onPlayerChange.attach((Player player) -> {uiHandler.changePlayer(player);});
    	ludo.onDieInfoChange.attach((Die die) -> {uiHandler.onDieInfoChange(die);});
    	ludo.onTurnComplete.attach(() -> {uiHandler.onTurnComplete();});
    	ludo.onPieceSelect.attach((Piece p) -> {uiHandler.onPieceSelect(p);});
    	ludo.onPieceUnselect.attach((Piece p) -> {uiHandler.onPieceUnSelect(p);});
    	ludo.onPiecesPositionChange.attach((Vector<PiecePositioningInfo> vec) -> {uiHandler.updatePiecesInfo(vec);});
    	uiHandler.onDieRollButtonClick.attach(() -> {ludo.rollDice();});
    	uiHandler.onNextTurnButtonClick.attach(() -> {ludo.nextPlayer();});
    	uiHandler.onBoardSquareClick.attach((BoardSquare b) -> {ludo.onClickBoardSquare(b);});
    	
    	ludo.run();
    }
}

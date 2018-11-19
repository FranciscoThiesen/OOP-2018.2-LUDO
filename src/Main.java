public class Main {
    public static void main(String[] args) {
    	Ludo ludo = Ludo.getInstance();
    	UIHandler uiHandler = new UIHandler();
    	
    	ludo.onPlayerChange.attach((Player player) -> {uiHandler.changePlayer(player);});
    	ludo.onDiceRoll.attach((Pair<Integer, Integer> result) -> {uiHandler.onDiceRoll(result);});
    	ludo.onTurnComplete.attach(() -> {uiHandler.onTurnComplete();});
    	ludo.onPieceSelect.attach((Piece p) -> {uiHandler.onPieceSelect(p);});
    	ludo.onPieceUnselect.attach((Piece p) -> {uiHandler.onPieceUnSelect(p);});
    	uiHandler.onDiceRollButtonClick.attach(() -> {ludo.rollDice();});
    	uiHandler.onNextTurnButtonClick.attach(() -> {ludo.nextPlayer();});
    	uiHandler.onBoardSquareClick.attach((BoardSquare b) -> {ludo.onClickBoardSquare(b);});
    	
    	ludo.run();
    }
}

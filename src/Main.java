public class Main {
    public static void main(String[] args) {
    	Ludo ludo = new Ludo();
    	UIHandler uiHandler = new UIHandler();
    	uiHandler.updateBoardSquares(ludo.getBoardSquareArray());
    	uiHandler.setVisible(true);
    	
    	ludo.onPlayerChange.attach((Player player) -> {uiHandler.changePlayer(player);});
    	ludo.onDiceRoll.attach((Pair<Integer, Integer> result) -> {uiHandler.onDiceRoll(result);});
    	ludo.onTurnComplete.attach(() -> {uiHandler.onTurnComplete();});
    	uiHandler.onDiceRollButtonClick.attach(() -> {ludo.rollDice();});
    	uiHandler.onNextTurnButtonClick.attach(() -> {ludo.nextPlayer();});
    	
    	ludo.run();
    }
}

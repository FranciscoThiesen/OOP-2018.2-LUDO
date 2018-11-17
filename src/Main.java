public class Main {
    public static void main(String[] args) {
    	Ludo ludo = new Ludo();
    	UIHandler uiHandler = new UIHandler();
    	uiHandler.updateBoardSquares(ludo.getBoardSquareArray());
    	uiHandler.setVisible(true);
    	ludo.run();
    }
}

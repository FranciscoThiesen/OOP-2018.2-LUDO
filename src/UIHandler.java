import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;

public class UIHandler extends JFrame {

	private static final long serialVersionUID = -3395052687382008316L;
	public static final int DEFAULT_WIDTH  = 1000;
    public static final int DEFAULT_HEIGHT = 800;

    private JSplitPane mainPanel;
    
    private UIBoardPanel boardPanel;
    private UIControlsPanel controlsPanel;
    
    public UIHandler() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = screenWidth / 2 - DEFAULT_WIDTH / 2;
        int y = screenHeight / 2 - DEFAULT_HEIGHT / 2;
    
        this.setBounds(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        
        // ----------------------
        
        this.boardPanel = new UIBoardPanel();
        this.controlsPanel = new UIControlsPanel();
        
        //- -------------------------
        
        this.mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.controlsPanel, this.boardPanel);
        this.mainPanel.setOneTouchExpandable(true);
        this.mainPanel.setDividerLocation(150);
        this.add(this.mainPanel);
    }
    
    public void updateBoardSquares(Vector<BoardSquare> vec) {
    	this.boardPanel.updateBoardSquares(vec);
    }

}
package com.inf1636_1611854_1310451.ui;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.imageio.*;
import java.io.IOException;

import com.inf1636_1611854_1310451.game.Die;

public class UIAuxiliarPanel extends JPanel {

    private Die die = new Die();
    private BufferedImage[] diePictures = new BufferedImage[6];

    public UIAuxiliarPanel()
    {
        super();
        
        String fileNames[] = {	"out/production/Ludo/Dado1.png",
        						"out/production/Ludo/Dado2.png",
        						"out/production/Ludo/Dado3.png",
        						"out/production/Ludo/Dado4.png",
        						"out/production/Ludo/Dado5.png",
        						"out/production/Ludo/Dado6.png"};
        for(int i = 0; i < 6; ++i) {
            try {
                this.diePictures[i] = ImageIO.read(new File(fileNames[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDie( Die die ) {
        this.die = die;
        this.repaint();
    }

    private void drawDie(Graphics2D g2D) {
        g2D.drawImage( this.diePictures[ this.die.getValue() - 1], 0, 0, this);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        this.drawDie(g2D);
    }

}

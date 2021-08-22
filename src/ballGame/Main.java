package ballGame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	    JFrame obj=new JFrame();
	    Gameplay gamePlay=new Gameplay();
	    obj.setBounds(10,10,900,800);
	    obj.setTitle("Breakout Ball");
	    obj.setResizable(false);
	    obj.setVisible(true);
	    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    obj.add(gamePlay);
    }
}

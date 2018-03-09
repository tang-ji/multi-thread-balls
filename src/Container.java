import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Container extends JPanel {
	protected ArrayList<Ball> balls = new ArrayList<Ball>(20);
	private int HEIGHT;
	private int WIDTH;
	private Color color = Color.WHITE;

	public Container(int h, int w, ArrayList<Ball> balls) {
		HEIGHT = h;
		WIDTH = w;
		this.balls = balls;
	}
      
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		for (Ball ball : balls) {
			ball.draw(g);
		}
	}

	public Dimension getPreferredSize() {
		return (new Dimension(WIDTH, HEIGHT));
	}
      
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
      
    public int getWidth() {
    	return WIDTH;
    }
      
    public int getHeight() {
    	return HEIGHT;
    }
}
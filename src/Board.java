import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Board extends JPanel implements MouseListener{
	protected ArrayList<Ball> balls = new ArrayList<Ball>(20);
	private Random r = new Random();
	private Container container;
	private double theta = 10;
	int count = 0;
	
	public Board(int width, int height) {

//	      ball = new Ball(x, y, speedX, speedY, radius, red, green, blue);
	      container = new Container(height, width, balls);

	      this.setLayout(new BorderLayout());
	      this.add(container, BorderLayout.CENTER);
	      this.addMouseListener(this);

	      start();

	  }
	
	public void update() {
		for (Ball ball : balls) {
			ball.move(container, balls);
		}
	}
	
	public void start() {

	      Thread t = new Thread() {
	          public void run() {

	              while (true) {

	                  update();
	                  repaint();
	                  try {
	                      Thread.sleep((int)theta);
	                  } catch (InterruptedException e) {
	                  }
	              }
	          }
	      };
	      t.start();
	  }
	
	public static void main(String[] args) {

	      javax.swing.SwingUtilities.invokeLater(new Runnable() {
	          public void run() {
	              JFrame f = new JFrame("Balls Plan");
	              f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
	              f.setContentPane(new Board(800, 600));
	              f.pack();
	              f.setVisible(true);
	          }
	      });
	  }

	  @Override
	  public void mouseClicked(MouseEvent e) {
	      // TODO Auto-generated method stub
	  }

	  @Override
	  public void mouseEntered(MouseEvent e) {
	      // TODO Auto-generated method stub
	  }

	  @Override
	  public void mouseExited(MouseEvent e) {
	      // TODO Auto-generated method stub
	  }

	  @Override
	  public void mousePressed(MouseEvent e) {

	      count++;
	      balls.add(new Ball(r.nextInt(container.getWidth()), r.nextInt(container.getHeight()), 
	    		  r.nextInt(container.getWidth()) / 200, r.nextInt(container.getHeight()) / 200, 
	    		  50 * (r.nextDouble()) * container.getWidth(),
	    		  new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)), count));
	  }

	  @Override
	  public void mouseReleased(MouseEvent e) {
	      // TODO Auto-generated method stub
	  }
	
//	private void collision() {  
//        double[][] dis = new double[ball.length][ball.length];  
//        for (int i = 0; i < ball.length; i++) {  
//            for (int j = 0; j < ball.length; j++) {  
//                dis[i][j] = Math.sqrt(Math.pow(ball[i].getX() - ball[j].getX(),  
//                        2) + Math.pow(ball[i].getY() - ball[j].getY(), 2));  
//            }  
//        }  
//        for (int i = 0; i < ball.length; i++) {  
//            for (int j = i + 1; j < ball.length; j++) {  
//                if (dis[i][j] < (ball[i].getR() + ball[j].getR()) / 2) {  
//                    double t;  
//                    t = ball[i].getVx();  
//                    ball[i].setVx(ball[j].getVx());  
//                    ball[j].setVx(t);  
//                    t = ball[i].getVy();  
//                    ball[i].setVy(ball[j].getVy());  
//                    ball[j].setVy(t);  
//                    double x2 = ball[j].getX() - ball[i].getX(), y2 = ball[j]  
//                            .getY() - ball[i].getY();  
//                    ball[j].setX(ball[i].getX() + x2);  
//                    ball[j].setY(ball[j].getY() + y2);  
//                } 
//            }  
//        }  
//    } 
}

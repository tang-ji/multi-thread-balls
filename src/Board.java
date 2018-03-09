import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class Board extends JPanel implements ActionListener, ItemListener{
	protected ArrayList<Ball> balls = new ArrayList<Ball>(20);
	private Random r = new Random();
	private Plan p;
	private double theta = 10;
	int count = 0, mode = 0;
	
	private JButton add = new JButton("Add a ball");
    private JButton remove = new JButton("Remove a ball");
    private JToggleButton gravity = new JToggleButton("gravity");
    private JToggleButton force = new JToggleButton("force");
    private JToggleButton collision = new JToggleButton("collision");
    private JToggleButton friction = new JToggleButton("friction");
	
	public Board(int width, int height) {

	      p = new Plan(height, width, balls);
	      this.setLayout(new BorderLayout());
	      this.add(p, BorderLayout.CENTER);
	      start();

	  }
	
	public void update() {
		for (Ball ball : balls) {
			ball.move(p, balls);
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
	
	public void Init() {
		JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Balls Plan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());

		this.setLayout(new FlowLayout());
		cp.add(this, BorderLayout.CENTER);

		JPanel resultsSuper = new JPanel();
		resultsSuper.setLayout(new GridLayout(2,1));

		JPanel threads = new JPanel();
		threads.setLayout(new FlowLayout());

		JPanel control = new JPanel();
		control.setLayout(new FlowLayout());
		control.add(add);
		control.add(remove);
		control.add(gravity);
		control.add(force);
		control.add(collision);
		control.add(friction);

		JPanel results = new JPanel();
		results.setLayout(new GridLayout(7,1));

		control.add(results);


		resultsSuper.add(control);
		resultsSuper.add(threads);

		cp.add(resultsSuper, BorderLayout.SOUTH);

		add.addActionListener(this);
		remove.addActionListener(this);
		gravity.addItemListener(this);
		force.addItemListener(this);
		collision.addItemListener(this);
		friction.addItemListener(this);

		//Display the window.
		frame.setSize(800,700);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	new Board(800, 600).Init();
	        }
	    });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add){
			count++;
			balls.add(new Ball(r.nextInt(p.getWidth()), r.nextInt(p.getHeight()), 
		    		r.nextInt(p.getWidth()) / 300, r.nextInt(p.getHeight()) / 300, 
		    		50 * (r.nextDouble()) * p.getWidth(),
		    		new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)), count));
		}
		if (e.getSource() == remove){
			if(balls.size() > 0) balls.remove(balls.size() - 1);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(collision.isSelected()){
			p.collisionMode = true;
	    } 
		else {
			p.collisionMode = false;
	    }
		
		if(gravity.isSelected()){
			p.gravityMode = true;
		}
		else {
			p.gravityMode = false;
		}
		
		if(force.isSelected()){
			p.forceMode = true;
		}
		else {
			p.forceMode = false;
		}
		
		if(friction.isSelected()){
			p.frictionMode = true;
		}
		else {
			p.frictionMode = false;
		}
		
	}


}

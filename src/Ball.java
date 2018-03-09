import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Ball{
	private double x, y, vx, vy, ax, ay, r, m;
	private Color color;
	public int id;
	
	public Ball(double x, double y, double vx, double vy, double m, Color color, int id) {  
        this.x = x;  
        this.y = y;  
        this.vx = vx;  
        this.vy = vy;  
        this.m = m;
        this.r = Math.pow(m, (double)1/3);
        this.color = color;
        this.id = id;
    }  
	
	public void move(Container container, ArrayList<Ball> balls) {
		
		x += vx;
		y += vy;
		getAcceleratedSpeed(balls);
		vx += ax;
		vy += ay;
		
		if (x <= 0 || x + r >= container.getWidth()) {  
            vx = -vx;
            if (x < 0) x = 0;  
            if (x > container.getWidth() - r) x = container.getWidth() - r;  
        }
		
		if (y <= 0 || y + r >= container.getHeight()) {  
            vy = -vy;
            if (y < 0) y = 0;  
            if (y > container.getHeight() - r)  y = container.getHeight() - r;  
		}
    }
	
	public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
    }
	
	public double getX() {  
        return x;  
    }  
  
    public void setX(double x) {  
        this.x = x;  
    }  
  
    public double getY() {  
        return y;  
    }  
  
    public void setY(double y) {  
        this.y = y;  
    }  
  
    public double getR() {  
        return r;  
    }  
  
    public void setRadiu(double radiu) {  
        this.r = radiu;  
    }  
  
    public double getVx() {  
        return vx;  
    }  
  
    public void setVx(double vx) {  
        this.vx = vx;  
    }  
  
    public double getVy() {  
        return vy;  
    }  
  
    public void setVy(double vy) {  
        this.vy = vy;  
    }
    
    public double getM() {  
        return m;  
    }  
  
    public void setM(double m) {  
        this.m = m;  
    }
    
    public Color getcolor() {  
        return color;  
    }  
  
    public void setcolor(Color color) {  
        this.color = color;  
    }
    
    public double getExternalTotalMasse(ArrayList<Ball> balls) {
		double M = 0;
		for(Ball b : balls) {
			if(this.id != b.id) M += b.getM();
		}
		return M;
	}
	
	public double getCentreX(ArrayList<Ball> balls) {
		double T = 0;
		for(Ball b : balls) {
			if(this.id != b.id) T += b.getM() * b.getX();
		}
		return T / getExternalTotalMasse(balls);
	}
	
	public double getCentreY(ArrayList<Ball> balls) {
		double T = 0;
		for(Ball b : balls) {
			if(this.id != b.id) T += b.getM() * b.getY();
		}
		return T / getExternalTotalMasse(balls);
	}
	
	public void getAcceleratedSpeed(ArrayList<Ball> balls) {
		if(balls.size() <= 1) {
			ax = 0;
			ay = 0;
			return;
		}
		double dx = getCentreX(balls) - x, dy = getCentreY(balls) - y;
		double ds = Math.sqrt(dx * dx + dy * dy);
		double a = getExternalTotalMasse(balls) * Math.pow(ds, -0.5) / 1000;
		ax = a / ds * dx;
		ay = a / ds * dy;
	}
	
	
}

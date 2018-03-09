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
	
	public void move(Plan p, ArrayList<Ball> balls) {
		
		x += vx;
		y += vy;
		getAcceleratedSpeed(balls, p);
		vx += ax;
		vy += ay;
		if(p.frictionMode) {
			vx *= 0.995;
			vy *= 0.995;
		}
		
		if (x <= 0 || x + r >= p.getWidth()) {  
            vx = -vx;
            if (x < 0) x = 0;  
            if (x > p.getWidth() - r) x = p.getWidth() - r;  
        }
		
		if (y <= 0 || y + r >= p.getHeight()) {  
            vy = -vy;
            if (y < 0) y = 0;  
            if (y > p.getHeight() - r)  y = p.getHeight() - r;  
		}
		if(p.collisionMode) collision(balls);
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
	
	public void getAcceleratedSpeed(ArrayList<Ball> balls, Plan p) {
		ax = 0;
		ay = 0;
		if(p.gravityMode) {
			if(balls.size() <= 1) {
				ax = 0;
				ay = 0;
				return;
			}
			double dx = getCentreX(balls) - x, dy = getCentreY(balls) - y;
			double ds = Math.sqrt(dx * dx + dy * dy);
			double a = getExternalTotalMasse(balls) * Math.pow(ds, -0.5) / 20000;
			ax = a / ds * dx;
			ay = a / ds * dy;
		}
		
		if(p.forceMode) {
			ay += 0.5;
		}
	}
	
	private void collision(ArrayList<Ball> balls) {  
        for (Ball ball : balls) {
        	if(this.id == ball.id) continue;
        	double dis = Math.sqrt(Math.pow(ball.getX() - this.x, 2) + Math.pow(ball.getY() - this.y, 2));
        	if(dis < ball.getR() + this.getR()) {
        		double vtemp1 = this.getVx(), vtemp2 = ball.getVx();
        		this.setVx(((this.getM() - ball.getM()) * vtemp1 + 2 * ball.getM() * vtemp2) / (ball.getM() + this.getM()));
        		ball.setVx(((-this.getM() + ball.getM()) * vtemp2 + 2 * this.getM() * vtemp1) / (ball.getM() + this.getM()));
        		double dx = ball.getX() - this.getX();
        		
        		vtemp1 = this.getVy();
        		vtemp2 = ball.getVy();
        		this.setVy(((this.getM() - ball.getM()) * vtemp1 + 2 * ball.getM() * vtemp2) / (ball.getM() + this.getM()));
        		ball.setVy(((-this.getM() + ball.getM()) * vtemp2 + 2 * this.getM() * vtemp1) / (ball.getM() + this.getM()));
        		double dy = ball.getY() - this.getY();
        		
        		ball.setX(this.getX() + (this.getR() + ball.getR()) * dx / dis + 0.01);
        		ball.setY(this.getY() + (this.getR() + ball.getR()) * dy / dis + 0.01);
        	}
        }
    } 

}

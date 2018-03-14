import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Ball{
	private double x, y, vx, vy, ax, ay, r, m;
	private Color color;
	public int id;
	public boolean stable = false;
	
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
	
	public Ball(double x, double y, double vx, double vy, double m, Color color, int id, boolean stable) {  
		this.x = x;  
		this.y = y;  
		this.vx = vx;  
		this.vy = vy;  
		this.m = m;
		this.r = Math.pow(m, (double)1/3);
		this.color = color;
		this.id = id;
		this.stable = stable;
	}  
	
	public void move(Plan p, ArrayList<Ball> balls) {
		if(stable) return;
		x += vx;
		y += vy;
		getAcceleratedSpeed(balls, p);
		if (x <= 0 || x + r >= p.getWidth()) {  
			vx = -vx;
			ax = 0;
			if (x < 0) x = 0;  
			if (x > p.getWidth() - r) x = p.getWidth() - r;  
		}
		
		if (y <= 0 || y + r >= p.getHeight()) {  
			vy = -vy;
			ay = 0;
			if (y < 0) y = 0;  
			if (y > p.getHeight() - r)  y = p.getHeight() - r;  
		}
		if(p.collisionMode) collision(balls);
		vx += ax;
		vy += ay;
		if(p.frictionMode) {
			vx *= 0.995;
			vy *= 0.995;
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
	
	public void getAcceleratedSpeed(ArrayList<Ball> balls, Plan p) {
		ax = 0;
		ay = 0;
		if(p.gravityMode) {
			if(balls.size() <= 1) {
				ax = 0;
				ay = 0;
				return;
			}
			for(Ball b : balls) {
				if(this.id == b.id) continue;
				double dx = b.getX() - x, dy = b.getY() - y;
				double ds = Math.sqrt(dx * dx + dy * dy);
				double a = b.getM() * Math.pow(ds, -0.5) / 25000;
				ax += a / ds * dx;
				ay += a / ds * dy;
			}
		}
		
		if(p.forceMode) {
			ay += 0.2;
		}
	}
	
	private void collision(ArrayList<Ball> balls) {  
		for (Ball ball:balls){
			if (this.id == ball.id) continue;
			double dis = Math.sqrt(Math.pow(ball.getX() - this.x, 2) + Math.pow(ball.getY() - this.y, 2));			
        	double dx = -ball.getX() + this.getX();
        	double dy = -ball.getY() + this.getY();
        	double sin = dy/dis, cos = dx/dis;
        	if (dis <= ball.getR() + this.getR()){
        		
        		if (ball.stable){
        			this.setX(ball.getX() - (this.getR() + ball.getR()) * (-dx) / dis + 0.0001);
        			this.setY(ball.getY() - (this.getR() + ball.getR()) * (-dy) / dis + 0.0001);
        			double Vx = this.getVx(), Vy = this.getVy();
        			double Vr = Vx*cos+Vy*sin, Vt = Vx*sin-Vy*cos;
        			this.setVx(-Vr*cos+Vt*sin);
        			this.setVy(-Vr*sin-Vt*cos);
        			continue;
        		}
        		double V1x = ball.getVx(), V1y = ball.getVy(), V2x = this.getVx(), V2y = this.getVy();
        		double m1 = ball.getM(), m2 = this.getM();
        		double V1r = V1x*cos+V1y*sin, V2r = V2x*cos+V2y*sin;
        		double V1t = V1x*sin-V1y*cos, V2t = V2x*sin-V2y*cos;
        		double V1r_prime = ((m1-m2)*V1r+2*m2*V2r)/(m1+m2);
        		double V2r_prime = ((m2-m1)*V2r+2*m1*V1r)/(m1+m2);
        		ball.setVx(V1r_prime*cos+V1t*sin);
        		ball.setVy(V1r_prime*sin-V1t*cos);
        		this.setVx(V2r_prime*cos+V2t*sin);
        		this.setVy(V2r_prime*sin-V2t*cos);
        		ball.setX(this.getX() + (this.getR() + ball.getR()) * (-dx) / dis + 0.0001);
        		ball.setY(this.getY() + (this.getR() + ball.getR()) * (-dy)/ dis + 0.0001);
        	}
		}
        
    } 

}

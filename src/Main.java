public class Main {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	new Board(800, 600).Init();
	        }
	    });
	}

}

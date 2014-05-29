package analysis;

import att.grappa.*;
import javax.swing.*;

public class Grapher {

	public static void main(String args[]) {
		Graph g = new Graph("Test",false,false);
		Node n1 = new Node(g,"n1");
		Node n2 = new Node(g, "n2");
		Edge e = new Edge(g, n1, n2);
		g.printGraph(System.out);
		
		GrappaPanel gp = new GrappaPanel(g);
		gp.addGrappaListener(new GrappaAdapter());
		gp.setScaleToFit(true);
		
		JFrame f = new JFrame();
		f.add(gp);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}
}

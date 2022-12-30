package edu.uga.miage.m1.polygons.gui.persistence;

import gui.persistence.Visitor;
import gui.shapes.*;


/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements Visitor {

    private String representation = "";
	private static final String XSTRING = "\"x\": %d,";
	private static final String YSTRING = "\"y\": %d";

	public JSonVisitor() {
		// empty constructor
    }

    @Override
    public void visit(Circle circle) {
    	this.representation = "";
		this.representation += "{" +
				"\"type\": \"circle\"," +
				String.format(XSTRING, circle.getX()) +
				String.format(YSTRING, circle.getY()) +
				"}";
    }

    @Override
    public void visit(Square square) {
    	this.representation = "";
		this.representation += "{" +
				"\"type\": \"square\"," +
				String.format(XSTRING, square.getX()) +
				String.format(YSTRING, square.getY()) +
				"}";
    }

    @Override
    public void visit(Triangle triangle) {
    	this.representation = "";
		this.representation += "{" +
				"\"type\": \"triangle\"," +
				String.format(XSTRING, triangle.getX()) +
				String.format(YSTRING, triangle.getY()) +
				"}";
    }

	@Override
	public void visit(Image minou) {
		this.representation = "";
		this.representation += "{" +
				"\"type\": \"minou\"," +
				String.format(XSTRING, minou.getX()) +
				String.format(YSTRING, minou.getY()) +
				"}";
	}

	/**
     * @return the representation in JSon example for a Circle
     *
     *         <pre>
     * {@code
     *  {
     *     "shape": {
     *     	  "type": "circle",
     *        "x": -25,
     *        "y": -25
     *     }
     *  }
     * }
     *         </pre>
     */
    public String getRepresentation() {
        return representation;
    }
}

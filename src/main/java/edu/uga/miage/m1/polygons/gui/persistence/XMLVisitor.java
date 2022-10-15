package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor {

    private String representation = "";
	private static final String XSTRING = "<x>%d</x>";
	private static final String YSTRING = "<y>%d</y>";
	private static final String SHAPE = "</shape>";


	public XMLVisitor() {
		// empty
	}

    @Override
    public void visit(Circle circle) {
    	this.representation =  "<shape>type>circle</type>" +
				String.format(XSTRING, circle.getX()) +
				String.format(YSTRING, circle.getY()) +
				SHAPE;
    }

    @Override
    public void visit(Square square) {
    	this.representation = "<shape><type>square</type>" +
				String.format(XSTRING, square.getX()) +
				String.format(YSTRING, square.getY()) +
				SHAPE;
    }

    @Override
    public void visit(Triangle triangle) {
    	this.representation = "<shape><type>triangle</type>" +
				String.format(XSTRING, triangle.getX()) +
				String.format(YSTRING, triangle.getY()) +
				SHAPE;
    }

    /**
     * @return the representation in JSon example for a Triangle:
     *
     *<pre>
     * {@code
     *  <shape>
     *    <type>triangle</type>
     *    <x>-25</x>
     *    <y>-25</y>
     *  </shape>
     * }
     * </pre>
     */
    public String getRepresentation() {
    	return representation;
    }
}

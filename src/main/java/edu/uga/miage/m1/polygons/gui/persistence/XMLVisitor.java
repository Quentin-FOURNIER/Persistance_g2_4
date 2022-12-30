package edu.uga.miage.m1.polygons.gui.persistence;

import gui.persistence.Visitor;
import gui.shapes.*;


/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor {

	private String representation = "";
	private static final String XSTRING = "<x>%d</x>";
	private static final String YSTRING = "<y>%d</y>";
	private static final String IDGROUP = "<idGroup>%d</idGroup>";
	private static final String SHAPE = "</shape>";


	public XMLVisitor() {
		// empty
	}

    @Override
    public void visit(Circle circle) {
    	this.representation +=  "<shape><type>circle</type>" +
				String.format(XSTRING, circle.getX()) +
				String.format(YSTRING, circle.getY()) +
				String.format(IDGROUP, circle.getIdGroupe()) +
				SHAPE;

    }

    @Override
    public void visit(Square square) {
    	this.representation += "<shape><type>square</type>" +
				String.format(XSTRING, square.getX()) +
				String.format(YSTRING, square.getY()) +
				String.format(IDGROUP, square.getIdGroupe()) +

				SHAPE;

	}

    @Override
    public void visit(Triangle triangle) {
    	this.representation += "<shape><type>triangle</type>" +
				String.format(XSTRING, triangle.getX()) +
				String.format(YSTRING, triangle.getY()) +
				String.format(IDGROUP, triangle.getIdGroupe()) +

				SHAPE;

    }

	@Override
	public void visit(Image minou) {
		this.representation += "<shape><type>minou</type>" +
				String.format(XSTRING, minou.getX()) +
				String.format(YSTRING, minou.getY()) +
				String.format(IDGROUP, minou.getIdGroupe()) +
				SHAPE;
	}
	
	public void cleanRepresentation() {
		this.representation = "";
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

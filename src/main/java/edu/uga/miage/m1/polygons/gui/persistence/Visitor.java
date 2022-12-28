package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Minou;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;


public interface Visitor {

    void visit(Circle circle);

    void visit(Square square);

    void visit(Triangle triangle);

    void visit(Minou minou);
}

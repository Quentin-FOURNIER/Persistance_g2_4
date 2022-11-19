package edu.uga.miage.m1.polygons.gui.shapes;


import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import java.util.List;

public interface SimpleShape {

    int getPositionRelativeX();

    void setPositionRelativeX(int positionRelativeX);

    int getPositionRelativeY();

    void setPositionRelativeY(int positionRelativeY);

    List<BaseShape> getShapes();

    void setShapes(List<BaseShape> shapes);

    void accept(Visitor visitor);

    String toString();
}

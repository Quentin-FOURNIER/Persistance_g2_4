package edu.uga.miage.m1.polygons.gui.shapes;

import java.io.File;
import java.io.IOException;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Circle extends BaseShape {

    public Circle(int positionX, int positionY) throws IOException {
        super(positionX, positionY);

        this.setName("Circle");

        this.setIcon(new ImageIcon(ImageIO.read(new File(PATH_TO_IMAGE + "circle.png"))));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Circle [" + getX() + ", " + getY() + "]";
    }
}

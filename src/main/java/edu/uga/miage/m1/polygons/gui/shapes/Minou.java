package edu.uga.miage.m1.polygons.gui.shapes;

import java.io.File;
import java.io.IOException;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Minou extends BaseShape {

    public Minou(int positionX, int positionY) throws IOException {
        super(positionX, positionY);

        this.setName("minou");
        this.setIcon(new ImageIcon(ImageIO.read(new File(PATH_TO_IMAGE + "minou.png"))));


    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Minou [" + getX() + ", " + getY() + "]";
    }
}

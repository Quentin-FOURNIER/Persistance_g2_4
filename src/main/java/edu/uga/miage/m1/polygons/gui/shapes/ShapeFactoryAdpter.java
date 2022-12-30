package edu.uga.miage.m1.polygons.gui.shapes;

import java.io.IOException;

import gui.shapes.Circle;
import gui.shapes.Image;
import gui.shapes.SimpleShape;
import gui.shapes.Square;
import gui.shapes.Triangle;


public class ShapeFactoryAdpter {

	public SimpleShape createShape(Shapes type, int x, int y) throws IOException {
		return switch (type) {
		case TRIANGLE -> new Triangle(x, y, 0);
		case CIRCLE -> new Circle(x, y, 0);
		case SQUARE -> new Square(x, y, 0);
		case MINOU -> new Image(x, y, 0);
		};
	}
	
	public SimpleShape createShape(Shapes type, int x, int y, int groupe) throws IOException {
		return switch (type) {
		case TRIANGLE -> new Triangle(x, y, groupe);
		case CIRCLE -> new Circle(x, y, groupe);
		case SQUARE -> new Square(x, y, groupe);
		case MINOU -> new Image(x, y, groupe);
		};
	}


	public Shapes stringToEnum(String type) {
		return switch (type) {
		case "circle" -> Shapes.CIRCLE;
		case "triangle" -> Shapes.TRIANGLE;
		case "square" -> Shapes.SQUARE;
		case "minou" -> Shapes.MINOU;
		default -> null;
		};
	}

}

package edu.uga.miage.m1.polygons.gui.shapes;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

public class SquareTest {
    Square square;

    @BeforeClass
    public static void setUpClass()  {
        // exécuté une fois avant tous les tests de cette classe
    }

    @AfterClass
    public static void tearDownClass() {// exécuté une fois après tous les tests de cette classe}
    }

    @org.junit.Before
    public void setUp() throws Exception {
        square = new Square(12, 17);
    }

    @org.junit.Test
    public void Square() {
        assertEquals(12 - 25, square.x);
        assertEquals(17 - 25, square.y);
    }

//    @org.junit.Test
//    public void draw() {
//        //
//    }
//
//    @org.junit.Test
//    public void accept() {
//        //
//    }

    @org.junit.Test
    public void getX() {
        assertEquals(12 - 25, square.getX());
    }

    @org.junit.Test
    public void getY() {
        assertEquals(17 - 25, square.getY());
    }
}
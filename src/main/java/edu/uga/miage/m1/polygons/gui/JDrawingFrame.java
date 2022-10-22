package edu.uga.miage.m1.polygons.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.EnumMap;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import lombok.extern.java.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href=
 * "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
@Log
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener {

    private enum Shapes {

        SQUARE, TRIANGLE, CIRCLE
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private final JToolBar mainToolbar;

    private Shapes mainSelected;

    private final JPanel mainPanel;

    private final JLabel mLabel;

    private final transient ActionListener mainReusableActionListener = new ShapeActionListener();

    private final transient XMLVisitor xmlVisitor = new XMLVisitor();

    private final transient JSonVisitor jsonVisitor = new JSonVisitor();

    private final StringBuilder builderXML = new StringBuilder();

    private final StringBuilder builderJSON = new StringBuilder();

    /**
     * Tracks buttons to manage the background.
     */
    private final EnumMap<Shapes, JButton> mainButtons = new EnumMap<>(Shapes.class);

    /**
     * Default constructor that populates the main window.
     */
    public JDrawingFrame(String frameName) {
        super(frameName);
        // Instantiates components
        mainToolbar = new JToolBar("Toolbar");

        JButton xmlButton = new JButton("Export XML");
        JButton jsonButton = new JButton("Export JSON");
        JButton importation = new JButton("Import");
        xmlButton.addActionListener(new ExportXMLActionListener());
        jsonButton.addActionListener(new ExportJSONActionListener());
        importation.addActionListener(new ImportActionListener());
        mainToolbar.add(xmlButton);
        mainToolbar.add(jsonButton);
        mainToolbar.add(importation);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);
        mainPanel.setMinimumSize(new Dimension(400, 400));
        mainPanel.addMouseListener(this);
        mainPanel.addMouseMotionListener(this);
        mLabel = new JLabel(" ", SwingConstants.LEFT);
        // Fills the panel
        setLayout(new BorderLayout());
        add(mainToolbar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(mLabel, BorderLayout.SOUTH);
        // Add shapes in the menu
        String userDir = System.getProperty("user.dir");
        addShape(Shapes.SQUARE, new ImageIcon(userDir + "/src/main/resources/images/square.png"));
        addShape(Shapes.TRIANGLE, new ImageIcon(userDir + "/src/main/resources/images/triangle.png"));
        addShape(Shapes.CIRCLE, new ImageIcon(userDir + "/src/main/resources/images/circle.png"));
        //edu/uga/miage/m1/polygons/gui/
        setPreferredSize(new Dimension(400, 400));
    }

    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     *
     * @param shape The name of the injected <tt>SimpleShape</tt>.
     * @param icon  The icon associated with the injected <tt>SimpleShape</tt>.
     */
    private void addShape(Shapes shape, ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        mainButtons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(mainReusableActionListener);
        if (mainSelected == null) {
            button.doClick();
        }
        mainToolbar.add(button);
        mainToolbar.validate();
        repaint();
    }

    /**
     * <tt>MouseListener</tt> interface to draw the selected shape into the drawing
     * canvas.
     *
     * @param evt The associated mouse event.
     */
    public void mouseClicked(MouseEvent evt) {
        if (mainPanel.contains(evt.getX(), evt.getY())) {
            Graphics2D g2 = (Graphics2D) mainPanel.getGraphics();
            switch (mainSelected) {
                case CIRCLE -> {
                    Circle circle = new Circle(evt.getX(), evt.getY());
                    circle.draw(g2);
                    circle.accept(jsonVisitor);
                    circle.accept(xmlVisitor);
                }
                case TRIANGLE -> {
                    Triangle triangle = new Triangle(evt.getX(), evt.getY());
                    triangle.draw(g2);
                    triangle.accept(jsonVisitor);
                    triangle.accept(xmlVisitor);
                }
                case SQUARE -> {
                    Square square = new Square(evt.getX(), evt.getY());
                    square.draw(g2);
                    square.accept(jsonVisitor);
                    square.accept(xmlVisitor);
                }


            }
            builderXML.append(xmlVisitor.getRepresentation()).append("\n");
            builderJSON.append(jsonVisitor.getRepresentation()).append("\n");
        }
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     *
     * @param evt The associated mouse event.
     */
    public void mouseEntered(MouseEvent evt) {
        // x
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     *
     * @param evt The associated mouse event.
     */
    public void mouseExited(MouseEvent evt) {
        mLabel.setText(" ");
        mLabel.repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate shape
     * dragging.
     *
     * @param evt The associated mouse event.
     */
    public void mousePressed(MouseEvent evt) {
        // x
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete shape
     * dragging.
     *
     * @param evt The associated mouse event.
     */
    public void mouseReleased(MouseEvent evt) {
        //x
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to move a
     * dragged shape.
     *
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) {
        // x
    }

    /**
     * Implements an empty method for the <tt>MouseMotionListener</tt> interface.
     *
     * @param evt The associated mouse event.
     */
    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
    }

    private void modifyLabel(MouseEvent evt) {
        mLabel.setText("(" + evt.getX() + "," + evt.getY() + ")");
    }

    /**
     * Simple action listener for shape tool bar buttons that sets the drawing
     * frame's currently selected shape when receiving an action event.
     */
    private class ShapeActionListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            // Itere sur tous les boutons
            for (Map.Entry<Shapes, JButton> shape : mainButtons.entrySet()) {
                JButton btn = shape.getValue();
                if (evt.getActionCommand().equals(shape.getKey().toString())) {
                    btn.setBorderPainted(true);
                    mainSelected = shape.getKey();
                } else {
                    btn.setBorderPainted(false);
                }
                btn.repaint();
            }
        }
    }

    private enum FileType {
        DOSSIER, FICHIER, BOTH
    }

    /**
     * Cette méthode permet d'ouvrir une fenêtre pour choisir l'emplacement du fichier
     *
     * @return chemin du fichier
     */
    private String chooserPath(FileType type) {
        JFileChooser chooser;
        String path = "";
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        switch (type) {
            case DOSSIER -> chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            case FICHIER -> chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            case BOTH -> chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        }
        int res = chooser.showOpenDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            path = file.getAbsolutePath();
        }
        return path;
    }

    private void writeFile(String path, String data) {
        try (FileWriter file = new FileWriter(path);) {
            file.write(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String readFile(String path) {
        String res = "";
        try {
            res = Files.readString(Path.of(path));
        } catch (IOException ex) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur read File");
        }
        return res;
    }


    public void createShape(String type, int x, int y) {
        Graphics2D g2 = (Graphics2D) mainPanel.getGraphics();
        switch (type) {
            case "triangle" -> drawShape(new Triangle(x, y), g2);
            case "circle" -> drawShape(new Circle(x, y), g2);
            case "square" -> drawShape(new Square(x, y), g2);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public void drawShape(Triangle t, Graphics2D g2) {
        t.draw(g2);
        t.accept(jsonVisitor);
        t.accept(xmlVisitor);
        saveExport();
    }

    public void drawShape(Square s, Graphics2D g2) {
        s.draw(g2);
        s.accept(jsonVisitor);
        s.accept(xmlVisitor);
        saveExport();
    }

    public void drawShape(Circle c, Graphics2D g2) {
        c.draw(g2);
        c.accept(jsonVisitor);
        c.accept(xmlVisitor);
        saveExport();
    }

    public void saveExport() {
        builderXML.append(xmlVisitor.getRepresentation() + "\n");
        builderJSON.append(jsonVisitor.getRepresentation() + "\n");
    }

    public void loadFromJSON(String data) {
        try {
            ObjectNode node = new ObjectMapper().readValue(data, ObjectNode.class);
            for (JsonNode j : node.get("shapes")) {
                createShape(j.get("type").asText(), j.get("x").asInt(), j.get("y").asInt());
            }
        } catch (JsonProcessingException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load JSON");

        }
    }

    public void loadFromXML(String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setExpandEntityReferences(false);
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(path));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("shape");
            String type;
            int x;
            int y;
            for (int i = 0; i < nList.getLength(); i++) {
                Element shape = (Element) nList.item(i);
                type = shape.getElementsByTagName("type").item(0).getTextContent();
                y = Integer.parseInt(shape.getElementsByTagName("y").item(0).getTextContent());
                x = Integer.parseInt(shape.getElementsByTagName("x").item(0).getTextContent());
                createShape(type, x, y);

            }
        } catch (ParserConfigurationException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load XML : Parser");
        } catch (IOException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load XML ");
        } catch (SAXException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load XML : Sax");
        }

    }

    private class ExportXMLActionListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            String path;
            StringBuilder xml;
            path = chooserPath(FileType.DOSSIER);
            xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n<root>\r\n<shapes>\r\n");
            xml.append(builderXML.toString());
            xml.append("</shapes>\r\n" + "</root>");
            writeFile(path + "/Export.xml", xml.toString());

        }
    }

    private class ExportJSONActionListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            String path;
            StringBuilder json;
            path = chooserPath(FileType.DOSSIER);
            json = new StringBuilder();
            json.append("{\n \"shapes\": [\n");
            json.append(builderJSON.toString());
            json.append("] \n}");
            writeFile(path + "/Export.json", json.toString().replace("}\n{", "},\n{"));

        }
    }

    private class ImportActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String path;
            path = chooserPath(FileType.FICHIER);

            List<String> type = Arrays.stream(path.split("\\.")).filter(file -> "xml".equals(file) || "json".equals(file)).toList();
            if (type.isEmpty())
                Logger.getLogger(String.valueOf(Level.WARNING), "Le fichier doit être de type json ou xml");
            else {

                switch (type.get(0)) {
                    case "xml" -> loadFromXML(path);
                    case "json" -> loadFromJSON(readFile(path));
                    default -> Logger.getLogger(String.valueOf(Level.WARNING), "Problème import");
                }
            }


        }
    }

}
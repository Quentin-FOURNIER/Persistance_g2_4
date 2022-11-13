package edu.uga.miage.m1.polygons.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import javax.imageio.ImageIO;
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
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import lombok.extern.java.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href=
 * "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
@Log
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener {

    public enum Shapes {
        SQUARE, TRIANGLE, CIRCLE
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String PATH_TO_IMAGE = "src/main/resources/images/";

    private final JToolBar mainToolbar;

    private Shapes mainSelected;

    private final JPanel mainPanel;

    private final JLabel mLabel;

    private final transient ActionListener mainReusableActionListener = new ShapeActionListener();

    private final transient XMLVisitor xmlVisitor = new XMLVisitor();

    private final transient JSonVisitor jsonVisitor = new JSonVisitor();

    //private final StringBuilder builderXML = new StringBuilder();

    //private final StringBuilder builderJSON = new StringBuilder();

    //private ArrayList<SimpleShape> saveShapes = new ArrayList<>();

    //public SimpleShape movableShape;

    /**
     * Tracks buttons to manage the background.
     */
    private final EnumMap<Shapes, JButton> mainButtons = new EnumMap<>(Shapes.class);


    private boolean move = false;
    private int relx;
    private JComponent component;
    private int rely;

    private Container container;

    /**
     * Default constructor that populates the main window.
     */
    public JDrawingFrame(String frameName) {
        super(frameName);

        // TOOLBAR
        mainToolbar = new JToolBar("Toolbar");
        JButton xmlButton = new JButton("Export XML");
        JButton jsonButton = new JButton("Export JSON");
        JButton importation = new JButton("Import");
        JButton whiteBoard = new JButton("White board");
        xmlButton.addActionListener(new ExportXMLActionListener());
        jsonButton.addActionListener(new ExportJSONActionListener());
        importation.addActionListener(new ImportActionListener());
        whiteBoard.addActionListener(new WhiteBoardActionListener());
        mainToolbar.add(xmlButton);
        mainToolbar.add(jsonButton);
        mainToolbar.add(importation);
        mainToolbar.add(whiteBoard);

        // PANEL
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);
        mainPanel.setMinimumSize(new Dimension(1000, 600));
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
        setPreferredSize(new Dimension(1000, 600));
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


    private JComponent getComponent(int x, int y) {
        // on recherche le premier composant qui correspond aux coordonnées de la souris
        for(Component component : mainPanel.getComponents()) {
            if ( component instanceof JComponent && component.getBounds().contains(x, y) ) {
                return (JComponent)component;
            }
        }
        return null;
    }

    public void mouseClicked(MouseEvent evt) {
        if (mainPanel.contains(evt.getX(), evt.getY()) && getComponent(evt.getX(), evt.getY()) == null) {
            try {
                //mainPanel.add(createShape(mainSelected, evt.getX(), evt.getY()));
                switch (mainSelected) {
                    case CIRCLE -> {
                        Circle circle = new Circle(evt.getX(), evt.getY());
                        circle.draw(mainPanel);
                        circle.accept(jsonVisitor);
                        circle.accept(xmlVisitor);
                    }
                    case TRIANGLE -> {
                        Triangle triangle = new Triangle(evt.getX(), evt.getY());
                        triangle.draw(mainPanel);
                        triangle.accept(jsonVisitor);
                        triangle.accept(xmlVisitor);
                    }
                    case SQUARE -> {
                        Square square = new Square(evt.getX(), evt.getY());
                        square.draw(mainPanel);
                        square.accept(jsonVisitor);
                        square.accept(xmlVisitor);
                    }
                }
                mainPanel.repaint();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void mouseEntered(MouseEvent evt) { /* Pas d'action */ }

    public void mouseExited(MouseEvent evt) { /* Pas d'action */ }

    public void mousePressed(MouseEvent evt) {

        if ( move ) {
            move=false;
            component.setBorder(null);
            component=null;
        }
        else {
            component = getComponent(evt.getX(),evt.getY());
            if ( component!=null ) {
                mainPanel.setComponentZOrder(component,0);
                relx = evt.getX()-component.getX();
                rely = evt.getY()-component.getY();
                move=true;
                component.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
    }

    public void mouseReleased(MouseEvent evt) { /* Pas d'action */ }

    public void mouseDragged(MouseEvent evt) { /* Pas d'action */ }

    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
        if ( move ) {
            // si on déplace
            component.setLocation(evt.getX()-relx, evt.getY()-rely);
        }
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
//
//    /**
//     * Cette méthode permet d'ouvrir une fenêtre pour choisir l'emplacement du fichier
//     *
//     * @return chemin du fichier
//     */
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
//
    private void writeFile(String path, String data) {
        try (FileWriter file = new FileWriter(path);) {
            file.write(data);
        } catch (IOException ex) {
            Logger.getLogger(String.valueOf(Level.WARNING), ex.toString());
        }
    }
//
    public String readFile(String path) {
        String res = "";
        try {
            res = Files.readString(Path.of(path));
        } catch (IOException ex) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur read File");
        }
        return res;
    }
//
//
//    public void createShape(String type, int x, int y) {
//        Graphics2D g2 = (Graphics2D) mainPanel.getGraphics();
//        switch (type) {
//            case "triangle" -> drawShape(new Triangle(x, y), g2);
//            case "circle" -> drawShape(new Circle(x, y), g2);
//            case "square" -> drawShape(new Square(x, y), g2);
//            default -> throw new IllegalStateException("Unexpected value: " + type);
//        }
//    }
//
//    public void drawShape(Triangle t, Graphics2D g2) {
//        //t.draw(g2);
//        t.accept(jsonVisitor);
//        t.accept(xmlVisitor);
//        saveExport();
//    }
//
//    public void drawShape(Square s, Graphics2D g2) {
//        //s.draw(g2);
//        s.accept(jsonVisitor);
//        s.accept(xmlVisitor);
//        saveExport();
//    }
//
//    public void drawShape(Circle c, Graphics2D g2) {
//        //c.draw(g2);
//        c.accept(jsonVisitor);
//        c.accept(xmlVisitor);
//        saveExport();
//    }
//
//    public void saveExport() {
//        builderXML.append(xmlVisitor.getRepresentation() + "\n");
//        builderJSON.append(jsonVisitor.getRepresentation() + "\n");
//    }
//
    public void loadFromJSON(String data) {
        try {
            ObjectNode node = new ObjectMapper().readValue(data, ObjectNode.class);
            for (JsonNode j : node.get("shapes")) {
                switch (j.get("type").asText()) {
                    case "triangle" -> {
                        Triangle triangle = new Triangle(j.get("x").asInt(), j.get("y").asInt());
                        triangle.draw(mainPanel);
                    }
                    case "square" -> {
                        Square square = new Square(j.get("x").asInt(), j.get("y").asInt());
                        square.draw(mainPanel);
                    }
                    case "circle" -> {
                        Circle circle = new Circle(j.get("x").asInt(), j.get("y").asInt());
                        circle.draw(mainPanel);
                    }
                }
                repaint();
                //createShape(j.get("type").asText(), j.get("x").asInt(), j.get("y").asInt());
            }
        } catch (IOException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load JSON");

        }
    }
//
    public void loadFromXML(String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setExpandEntityReferences(false);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
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
                //createShape(type, x, y);
                switch (type) {
                    case "triangle" -> {
                        Triangle triangle = new Triangle(x, y);
                        triangle.draw(mainPanel);
                    }
                    case "square" -> {
                        Square square = new Square(x, y);
                        square.draw(mainPanel);
                    }
                    case "circle" -> {
                        Circle circle = new Circle(x, y);
                        circle.draw(mainPanel);
                    }
                }
                repaint();

            }
        } catch (ParserConfigurationException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load XML : Parser");
        } catch (IOException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load XML ");
        } catch (SAXException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load XML : Sax");
        }

    }

    private class WhiteBoardActionListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            mainPanel.removeAll();
            repaint();
            // vide les exports
            //builderJSON.setLength(0);
            //builderXML.setLength(0);
        }
    }
    private class ExportXMLActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><shapes>";

            for (Component shape: mainPanel.getComponents()) {
                switch (shape.getName()) {
                    case "Triangle" -> {
                        xmlString += "<shape><type>triangle</type><x>"+(shape.getX()+25)+"</x><y>"+(shape.getY()+25)+"</y></shape>";
                    }
                    case "Square" -> {
                        xmlString += "<shape><type>square</type><x>"+(shape.getX()+25)+"</x><y>"+(shape.getY()+25)+"</y></shape>";
                    }
                    case "Circle" -> {
                        xmlString += "<shape><type>circle</type><x>"+(shape.getX()+25)+"</x><y>"+(shape.getY()+25)+"</y></shape>";
                    }
                }

            }

            xmlString += "</shapes></root>";
            String path = chooserPath(FileType.DOSSIER);
            writeFile(path + "/Export.xml", xmlString);
        }
    }
//
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String path;
//            StringBuilder xml;
//            path = chooserPath(FileType.DOSSIER);
//            xml = new StringBuilder();
//            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n<root>\r\n<shapes>\r\n");
//            xml.append(builderXML.toString());
//            xml.append("</shapes>\r\n" + "</root>");
//            writeFile(path + "/Export.xml", xml.toString());
//
//        }
//    }
//
    private class ExportJSONActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String path = chooserPath(FileType.DOSSIER);
            StringBuilder json = new StringBuilder();
            json.append("{\n \"shapes\": [\n");
            for (Component shape: mainPanel.getComponents()) {
                switch (shape.getName()) {
                    case "Triangle" -> json.append("{\"type\": \"triangle\",\"x\": ").append(shape.getX()).append(",\"y\": ").append(shape.getY()).append("}");
                    case "Circle" -> json.append("{\"type\": \"circle\",\"x\": ").append(shape.getX()).append(",\"y\": ").append(shape.getY()).append("}");
                    case "Square" -> json.append("{\"type\": \"square\",\"x\": ").append(shape.getX()).append(",\"y\": ").append(shape.getY()).append("}");

                }
                if (! shape.equals(mainPanel.getComponent(mainPanel.getComponents().length - 1))) {
                    json.append(json.append("},"));
                }
            }
            json.append("] \n}");
            writeFile(path + "/Export.json", json.toString().replace("}\n{", "},\n{"));

        }
    }
//
    private class ImportActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String path;
            path = chooserPath(FileType.FICHIER);

            List<String> type = Arrays.stream(path.split("\\.")).filter(file -> "xml".equals(file) || "json".equals(file)).toList();
            if (type.isEmpty())
                Logger.getLogger(String.valueOf(Level.WARNING), "Le fichier doit être de type json ou xml");
            else {
                mainPanel.removeAll();
                repaint();
                switch (type.get(0)) {
                    case "xml" -> loadFromXML(path);
                    case "json" -> loadFromJSON(readFile(path));
                    default -> Logger.getLogger(String.valueOf(Level.WARNING), "Problème import");
                }
            }


        }
    }

}

package river;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

/**
 * Graphical interface for the River application
 *
 * @author Gregory Kulczycki
 */
public class RiverGUI extends JPanel implements MouseListener {


    // ==========================================================
    // Fields (hotspots)
    // ==========================================================

    private final Rectangle leftFarmerRect = new Rectangle(80, 215, 50, 50);
    private final Rectangle leftWolfRect = new Rectangle(20, 215, 50, 50);
    private final Rectangle leftGooseRect = new Rectangle(20, 275, 50, 50);
    private final Rectangle leftBeansRect = new Rectangle(80, 275, 50, 50);
    private final Rectangle leftBoatRect = new Rectangle(140, 275, 110, 50);
    private final Rectangle leftBoatDriverRect = new Rectangle(140, 215, 50, 50);
    private final Rectangle leftBoatPassengerRect = new Rectangle(200, 215, 50, 50);

    private final Rectangle rightFarmerRect = new Rectangle(730, 215, 50, 50);
    private final Rectangle rightWolfRect = new Rectangle(670, 215, 50, 50);
    private final Rectangle rightGooseRect = new Rectangle(670, 275, 50, 50);
    private final Rectangle rightBeansRect = new Rectangle(730, 275, 50, 50);
    private final Rectangle rightBoatRect = new Rectangle(550, 275, 110, 50);
    private final Rectangle rightBoatDriverRect = new Rectangle(550, 215, 50, 50);
    private final Rectangle rightBoatPassengerRect = new Rectangle(610, 215, 50, 50);

    private final Rectangle restartButtonRect = new Rectangle(350, 120, 100, 30);

    // ==========================================================
    // Private Fields
    // ==========================================================

    private GameEngine engine; // Model
    private boolean restart = false;
    private java.util.List<Item> itemList;

    int[] dx = {0, 60, 0, 60};
    int[] dy = {0, 0, -60, -60};

    private int leftBaseX = 20;
    private int leftBaseY = 275;
    private int leftBoatX = 140;
    private int leftBoatY = 275;

    private int rightBaseX = 670;
    private int rightBaseY = 275;
    private int rightBoatX = 550;
    private int rightBoatY = 275;

    private int rectHeight = 50;
    private int rectWidth = 50;

    private Item passenger1;
    private Item passenger2;



    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {

        engine = new GameEngine();
        addMouseListener(this);
        itemList = Arrays.asList(Item.values());
    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics g) {

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        paintItem(g);
        paintObjectsOnBoat(g);
        String message = "";
        if (engine.gameIsLost()) {
            message = "You Lost!";
            restart = true;
        }
        if (engine.gameIsWon()) {
            message = "You Won!";
            restart = true;
        }
        paintMessage(message, g);
        if (restart) {
            paintRestartButton(g);
        }

    }

    public void paintItem(Graphics g) {
        for (Item i: itemList) {
            paintStringInRectangle(g, engine.getItemColor(i), engine.getItemLabel(i), getItemRectangle(i));
        }

    }

    public Rectangle getItemRectangle(Item item) {
        Rectangle rect = new Rectangle();
        int index = item.ordinal();
        Location location = engine.getItemLocation(item);
        if (location == Location.START) {
            rect = new Rectangle(leftBaseX + dx[index], leftBaseY + dy[index], rectWidth, rectHeight);
        } else if (location == Location.FINISH) {
            rect = new Rectangle(rightBaseX + dx[index], rightBaseY + dy[index], rectWidth, rectHeight);
        }
        return rect;
    }

    public void paintObjectsOnBoat(Graphics g) {
        if (engine.getBoatLocation() == Location.START) {
            g.setColor(Color.ORANGE);
            g.fillRect(140, 275, 110, 50);
            if (engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
                g.setColor(Color.MAGENTA);
                g.fillRect(140, 215, 50, 50);
            }
            if (engine.getItemLocation(Item.ITEM_2) == Location.BOAT) {
                g.setColor(Color.CYAN);
                g.fillRect(200, 215, 50, 50);
                paintStringInRectangle("W", 200, 215, 50, 50, g);
            } else if (engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
                g.setColor(Color.CYAN);
                g.fillRect(200, 215, 50, 50);
                paintStringInRectangle("G", 200, 215, 50, 50, g);
            } else if (engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
                g.setColor(Color.CYAN);
                g.fillRect(200, 215, 50, 50);
                paintStringInRectangle("B", 200, 215, 50, 50, g);
            }
        }
        if (engine.getBoatLocation() == Location.FINISH) {
            g.setColor(Color.ORANGE);
            g.fillRect(550, 275, 110, 50);
            if (engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
                g.setColor(Color.MAGENTA);
                g.fillRect(550, 215, 50, 50);
            }
            if (engine.getItemLocation(Item.ITEM_2) == Location.BOAT) {
                g.setColor(Color.CYAN);
                g.fillRect(610, 215, 50, 50);
                paintStringInRectangle("W", 610, 215, 50, 50, g);
            } else if (engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
                g.setColor(Color.CYAN);
                g.fillRect(610, 215, 50, 50);
                paintStringInRectangle("G", 610, 215, 50, 50, g);
            } else if (engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
                g.setColor(Color.CYAN);
                g.fillRect(610, 215, 50, 50);
                paintStringInRectangle(g, Color.CYAN, "B", new Rectangle(610, 215, 50, 50));
            }
        }
    }

    public void paintStringInRectangle(String str, int x, int y, int width, int height, Graphics g) {
        g.setColor(Color.BLACK);
        int fontSize = (height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = x + width / 2 - fm.stringWidth(str) / 2;
        int strYCoord = y + height / 2 + fontSize / 2 - 4;
        g.drawString(str, strXCoord, strYCoord);
    }

    public void paintStringInRectangle(Graphics g, Color color, String str, Rectangle rect) {
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.BLACK);
        int fontSize = (rect.height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = rect.x + rect.width / 2 - fm.stringWidth(str) / 2;
        int strYCoord = rect.y + rect.height / 2 + fontSize / 2 - 4;
        g.drawString(str, strXCoord, strYCoord);
    }

    public void paintMessage(String message, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    public void paintRestartButton(Graphics g) {
        g.setColor(Color.BLACK);
        paintBorder(restartButtonRect, 3, g);
        g.setColor(Color.PINK);
        g.fillRect(restartButtonRect.x, restartButtonRect.y, restartButtonRect.width, restartButtonRect.height);
        paintStringInRectangle("Restart", restartButtonRect.x, restartButtonRect.y, restartButtonRect.width,
                restartButtonRect.height, g);
    }

    public void paintBorder(Rectangle r, int thickness, Graphics g) {
        g.fillRect(r.x - thickness, r.y - thickness, r.width + (2 * thickness), r.height + (2 * thickness));
    }

    // ==========================================================
    // Startup Methods
    // ==========================================================

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window
        JFrame frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        RiverGUI newContentPane = new RiverGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(RiverGUI::createAndShowGUI);
    }

    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================

    @Override
    public void mouseClicked(MouseEvent e) {

        if (restart) {
            if (this.restartButtonRect.contains(e.getPoint())) {
                engine.resetGame();
                restart = false;
                repaint();
            }
            return;
        }

        if (leftFarmerRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_3) == Location.START) {
                engine.loadBoat(Item.ITEM_3);
            }
        } else if (leftWolfRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_2) == Location.START) {
                engine.loadBoat(Item.ITEM_2);
            }
        } else if (leftGooseRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_1) == Location.START) {
                engine.loadBoat(Item.ITEM_1);
            }
        } else if (leftBeansRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_0) == Location.START) {
                engine.loadBoat(Item.ITEM_0);
            }
        } else if (leftBoatDriverRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.START && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
                engine.unloadBoat(Item.ITEM_3);
            }
        } else if (leftBoatPassengerRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.START) {
                if (engine.getItemLocation(Item.ITEM_2) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_2);
                } else if (engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_1);
                } else if (engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_0);
                }
            }
        } else if (leftBoatRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.START && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
                engine.rowBoat();
            }
        } else if (rightFarmerRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_3) == Location.FINISH) {
                engine.loadBoat(Item.ITEM_3);
            }
        } else if (rightWolfRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_2) == Location.FINISH) {
                engine.loadBoat(Item.ITEM_2);
            }
        } else if (rightGooseRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_1) == Location.FINISH) {
                engine.loadBoat(Item.ITEM_1);
            }
        } else if (rightBeansRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_0) == Location.FINISH) {
                engine.loadBoat(Item.ITEM_0);
            }
        } else if (rightBoatDriverRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
                engine.unloadBoat(Item.ITEM_3);
            }
        } else if (rightBoatPassengerRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.FINISH) {
                if (engine.getItemLocation(Item.ITEM_2) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_2);
                } else if (engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_1);
                } else if (engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_0);
                }
            }
        } else if (rightBoatRect.contains(e.getPoint())) {
            if (engine.getBoatLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
                engine.rowBoat();
            }
        } else {
            return;
        }
        repaint();
    }

    // ----------------------------------------------------------
    // None of these methods will be used
    // ----------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}

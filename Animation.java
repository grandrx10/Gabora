import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Animation {
    JFrame gameWindow;
    GraphicsPanel canvas;
    ArrayList<Entity> entities = new ArrayList<Entity>();
    ArrayList<Room> rooms = new ArrayList<Room>();
    Camera camera = new Camera(0);
    MovementKeyListener keyListener;

    Animation() {
        gameWindow = new JFrame("Game Window");
        gameWindow.setSize(Const.WIDTH, Const.HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new GraphicsPanel();
        gameWindow.add(canvas);

        keyListener = new MovementKeyListener();
        canvas.addKeyListener(keyListener);

        entities.add(new Player(300, 300, 60, 60, "girl", 4, 8));
        entities.add(new Guard(300, 200, 60, 60, "girl", 4, 8));

        entities.add(new Wall(-1100, 400, 10000, 50, "", 0, 0));
        entities.add(new Wall(0, 100, 600, 50, "", 0, 0));
        entities.add(new Wall(0, 100, 50, 300, "", 0, 0));
        entities.add(new Wall(600, 100, 50, 200, "", 0, 0));

        gameWindow.setVisible(true);
    }

    // ------------------------------------------------------------------------------
    public void runGameLoop() {
        while (true) {
            // 1. and 2. Clear the game window and draw everything
            gameWindow.repaint();
            // 3. Wait enough time, so human eye can perceive the drawing
            try {
                Thread.sleep(Const.FRAME_PERIOD);
            } catch (Exception e) {
            }
            // any animations after
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).update(entities);
                // if (entities.get(i).getType().equals("Enemy")) {

                // entities.get(i).search();
                // }
            }
            camera.update(entities);

        } // 5. Repeat
    }

    // ------------------------------------------------------------------------------
    public class GraphicsPanel extends JPanel {
        public GraphicsPanel() {
            setFocusable(true);
            requestFocusInWindow();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).draw(g, camera.getXRange(), camera.getYRange());
            }
        }
    }

    // ------------------------------------------------------------------------------

    public class MovementKeyListener implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                entities.get(0).move("left");
            } else if (key == KeyEvent.VK_RIGHT) {
                entities.get(0).move("right");
            } else if (key == KeyEvent.VK_UP) {
                entities.get(0).jump();
            } else if (key == KeyEvent.VK_DOWN) {
            }
            // char keyChar = e.getKeyChar();
            // if (keyChar == 'a') {
            // entities.get(0).move("left");
            // } else if (keyChar == 'd') {
            // entities.get(0).move("right");
            // } else if (keyChar == 'w') {
            // entities.get(0).jump();
            // } else if (keyChar == 's') {
            // }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ESCAPE) {
                gameWindow.dispose();
            } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                entities.get(0).move("none");
            }
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    // -----------------------------------------------
    public static void main(String[] args) {
        Animation game = new Animation();
        game.runGameLoop();
    }

    public static int randInt(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}

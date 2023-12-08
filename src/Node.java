import java.awt.*;

class Node {
    int x;
    int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x - 5, y - 5, 20, 20);
    }
}
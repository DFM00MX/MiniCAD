import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MiniCAD extends JFrame {
    private List<Node> nodos;
    private List<Point> conexiones;
    private boolean uniendo;
    private int traslacionX;
    private int traslacionY;

    public MiniCAD() {
        super("Mini CAD :p");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        nodos = new ArrayList<>();
        conexiones = new ArrayList<>();
        uniendo = false;
        traslacionX = 0;
        traslacionY = 0;

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarNodos(g);
                dibujarConexiones(g);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!uniendo) {
                    nodos.add(new Node(e.getX() - traslacionX, e.getY() - traslacionY));
                    panel.repaint();
                }
            }
        });

        JButton botonAgrandar = new JButton("Aumentar Figura");
        JButton botonAchicar = new JButton("Reducir Figura");
        JButton botonRotar = new JButton("Rotar Figura");
        JButton botonUnion = new JButton("Crear Figura");
        JButton botonTransladarIzquierda = new JButton("Trasladar Figura ←");
        JButton botonTransladarDerecha = new JButton("Trasladar Figura →");
        JButton botonTransladarArriba = new JButton("Trasladar Figura ↑");
        JButton botonTransladarAbajo = new JButton("Trasladar Figura ↓");

        botonAgrandar.addActionListener(e -> {
            agrandarFigura();
            panel.repaint();
        });

        botonAchicar.addActionListener(e -> {
            achicarFigura();
            panel.repaint();
        });

        botonRotar.addActionListener(e -> {
            rotarFigura();
            panel.repaint();
        });

        botonUnion.addActionListener(e -> {
            uniendo = true;
            conectarNodos();
            panel.repaint();
        });

        botonTransladarIzquierda.addActionListener(e -> {
            trasladarFiguraIzquierda();
            panel.repaint();
        });
        botonTransladarDerecha.addActionListener(e -> {
            trasladarFiguraDerecha();
            panel.repaint();
        });
        botonTransladarArriba.addActionListener(e -> {
            trasladarFiguraArriba();
            panel.repaint();
        });
        botonTransladarAbajo.addActionListener(e -> {
            trasladarFiguraAbajo();
            panel.repaint();
        });

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        botonPanel.add(botonUnion);
        botonPanel.add(botonAchicar);
        botonPanel.add(botonAgrandar);
        botonPanel.add(botonRotar);
        botonPanel.add(botonTransladarIzquierda);
        botonPanel.add(botonTransladarDerecha);
        botonPanel.add(botonTransladarArriba);
        botonPanel.add(botonTransladarAbajo);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(botonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void trasladarFiguraIzquierda() {
        conexiones.clear();
        int desplazamientoX = -10; // Puedes ajustar el desplazamiento según tus necesidades
        //int desplazamientoY = 10;

        traslacionX += desplazamientoX;
        //traslacionY += desplazamientoY;

        // Actualiza las posiciones de los nodos
        for (Node nodo : nodos) {
            nodo.x += desplazamientoX;
           // nodo.y += desplazamientoY;
        }

        conectarNodos();
    }
    private void trasladarFiguraDerecha() {
        conexiones.clear();
        int desplazamientoX = 10; // Puedes ajustar el desplazamiento según tus necesidades
        //int desplazamientoY = 10;

        traslacionX += desplazamientoX;
        //traslacionY += desplazamientoY;

        // Actualiza las posiciones de los nodos
        for (Node nodo : nodos) {
            nodo.x += desplazamientoX;
            // nodo.y += desplazamientoY;
        }

        conectarNodos();
    }

    private void trasladarFiguraArriba() {
        conexiones.clear();
        //int desplazamientoX = 10; // Puedes ajustar el desplazamiento según tus necesidades
        int desplazamientoY = -10;

        //traslacionX += desplazamientoX;
        traslacionY += desplazamientoY;

        // Actualiza las posiciones de los nodos
        for (Node nodo : nodos) {
            //nodo.x += desplazamientoX;
            nodo.y += desplazamientoY;
        }

        conectarNodos();
    }

    private void trasladarFiguraAbajo() {
        conexiones.clear();
        //int desplazamientoX = 10; // Puedes ajustar el desplazamiento según tus necesidades
        int desplazamientoY = 10;

        //traslacionX += desplazamientoX;
        traslacionY += desplazamientoY;

        // Actualiza las posiciones de los nodos
        for (Node nodo : nodos) {
            //nodo.x += desplazamientoX;
            nodo.y += desplazamientoY;
        }

        conectarNodos();
    }
private void imprimirCoordenadas() {
        System.out.println("Coordenadas de la Figura:");
        for (Node nodo : nodos) {
            System.out.println("x: " + nodo.x + ", y: " + nodo.y);
        }
        System.out.println("--------------------");
    }

    private void rotarFigura() {
        conexiones.clear();

        if (nodos.size() >= 1) {
            int centroX = 0;
            int centroY = 0;
            for (Node nodo : nodos) {
                centroX += nodo.x;
                centroY += nodo.y;
            }
            centroX /= nodos.size();
            centroY /= nodos.size();

            // Ángulo de rotación en radianes
            double angulo = Math.toRadians(20); // Puedes ajustar el ángulo según tus necesidades

            // Realiza la rotación de cada nodo alrededor del centro
            for (Node nodo : nodos) {
                int deltaX = nodo.x - centroX;
                int deltaY = nodo.y - centroY;
                nodo.x = (int) (centroX + deltaX * Math.cos(angulo) - deltaY * Math.sin(angulo));
                nodo.y = (int) (centroY + deltaX * Math.sin(angulo) + deltaY * Math.cos(angulo));
            }
            conectarNodos();
        }
    }

    private void achicarFigura() {
        conexiones.clear();

        if (nodos.size() >= 1) {
            // Encuentra el centro de la figura actual
            int centroX = 0;
            int centroY = 0;
            for (Node nodo : nodos) {
                centroX += nodo.x;
                centroY += nodo.y;
            }
            centroX /= nodos.size();
            centroY /= nodos.size();

            // Reduce el tamaño de la figura manteniendo el centro
            double factorEscala = 0.5; // Puedes ajustar el factor según tus necesidades
            for (Node nodo : nodos) {
                nodo.x = (int) ((nodo.x - centroX) * factorEscala + centroX);
                nodo.y = (int) ((nodo.y - centroY) * factorEscala + centroY);
            }
            conectarNodos();
        }
    }

    private void agrandarFigura() {
        conexiones.clear();

        if (nodos.size() >= 1) {
            // Encuentra el centro de la figura actual
            int centroX = 0;
            int centroY = 0;
            for (Node nodo : nodos) {
                centroX += nodo.x;
                centroY += nodo.y;
            }
            centroX /= nodos.size();
            centroY /= nodos.size();

            // Aumenta el tamaño de la figura manteniendo el centro
            double factorEscala = 1.5;
            for (Node nodo : nodos) {
                nodo.x = (int) ((nodo.x - centroX) * factorEscala + centroX);
                nodo.y = (int) ((nodo.y - centroY) * factorEscala + centroY);
            }
            conectarNodos();
        }
    }
    private void conectarNodos() {
        if (nodos.size() >= 2) {
            for (int i = 0; i < nodos.size() - 1; i++) {
                conexiones.add(new Point(nodos.get(i).x, nodos.get(i).y));
                conexiones.add(new Point(nodos.get(i + 1).x, nodos.get(i + 1).y));
            }
            conexiones.add(new Point(nodos.get(nodos.size() - 1).x, nodos.get(nodos.size() - 1).y));
            conexiones.add(new Point(nodos.get(0).x, nodos.get(0).y));
        }
        imprimirCoordenadas();
    }

    private void dibujarNodos(Graphics g) {
        for (Node nodo : nodos) {
            nodo.dibujar(g);
        }
    }

    private void dibujarConexiones(Graphics g) {
        g.setColor(Color.GREEN);
        for (int i = 1; i < conexiones.size(); i += 2) {
            Point punto1 = conexiones.get(i - 1);
            Point punto2 = conexiones.get(i);
            g.drawLine(punto1.x, punto1.y, punto2.x, punto2.y);
        }

        // Pinta el interior de la figura cerrada
        if (conexiones.size() > 2) {
            int[] xPoints = new int[conexiones.size() / 2];
            int[] yPoints = new int[conexiones.size() / 2];
            for (int i = 0; i < conexiones.size(); i += 2) {
                xPoints[i / 2] = conexiones.get(i).x;
                yPoints[i / 2] = conexiones.get(i).y;
            }
            g.setColor(new Color(0, 0, 255, 50)); // Color con transparencia
            g.fillPolygon(xPoints, yPoints, xPoints.length);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniCAD());
    }
}
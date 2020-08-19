package snakepackage;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import enums.GridSize;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.WindowConstants.*;


/**
 * @author jd-
 *
 */
public class

SnakeApp {


    private JButton incio;
    private JButton pausa;
    private JButton reanudar;

    private static SnakeApp app;
    public static final int MAX_THREADS = 8;
    public static Snake[] snakes = new Snake[MAX_THREADS];
    private static final Cell[] spawn = {
        new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2,
        3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
        new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2,
        GridSize.GRID_HEIGHT - 2)};
    private JFrame frame;
    private static Board board;
    int nr_selected = 0;
    public static Thread[] thread = new Thread[MAX_THREADS];

    public SnakeApp() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("The Snake Race");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(618, 640);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17,
                GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 40);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);
        board = new Board();
        
        
        frame.add(board,BorderLayout.CENTER);

        incio = new JButton("Inicio ");
        pausa = new JButton("Pausa ");
        reanudar = new JButton("Reanudar ");

        JPanel actionsBPabel=new JPanel();
        actionsBPabel.setLayout(new FlowLayout());
        actionsBPabel.add(incio);
        actionsBPabel.add(pausa);
        actionsBPabel.add(reanudar);
        frame.add(actionsBPabel,BorderLayout.SOUTH);

        for (int i = 0; i != MAX_THREADS; i++) {

            snakes[i] = new Snake(i + 1, spawn[i], i + 1);
            snakes[i].addObserver(board);
            thread[i] = new Thread(snakes[i]);

        }

        frame.setVisible(true);

    }

    public static void main(String[] args) {
        app = new SnakeApp();

        app.start(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JButton) e.getSource()).setEnabled(false);
                new Thread() {
                    public void run() {
                        app.init();
                    }
                }.start();
            }
        });

        app.pause(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    public void run() {
                        for (int i = 0; i != MAX_THREADS; i++) {
                            snakes[i].detener();
                        }
                    }
                }.start();
            }
        });

        app.resume(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    public void run() {
                        for (int i = 0; i != MAX_THREADS; i++) {
                            snakes[i].reanudar();
                        }
                    }
                }.start();
            }
        });
    }

    public void start(ActionListener action) {
        incio.addActionListener(action);
    }

    public void resume(ActionListener action) {
        reanudar.addActionListener(action);
    }

    public void pause(ActionListener action) {
        pausa.addActionListener(action);
    }

    public void init() {

        for (int i = 0; i != MAX_THREADS; i++) {
            thread[i].start();
        }

        while (true) {
            int x = 0;
            for (int i = 0; i != MAX_THREADS; i++) {
                if (snakes[i].isSnakeEnd() == true) {
                    x++;
                }
            }
            if (x == MAX_THREADS) {
                break;
            }
        }


        System.out.println("Thread (snake) status:");
        for (int i = 0; i != MAX_THREADS; i++) {
            System.out.println("["+i+"] :"+thread[i].getState());
        }
        

    }
    
    public static SnakeApp getApp() {
        return app;
    }

}

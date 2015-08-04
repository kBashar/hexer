package org.kbashar.hexer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test implements MouseMotionListener
{

    JFrame frame;
    Test()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new CustomComponent(new FlowLayout(FlowLayout.LEFT));
        panel.addMouseMotionListener(this);
        panel.setBackground(Color.WHITE);

        for (int i = 1; i <= 160; i++)
        {
            if (i == 94)
            {
                panel.add(new ByteUnit("94"));
                continue;
            }
            panel.add(new ByteUnit("BF"));
        }

        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(500, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
      System.out.println(Character.digit('6', 16));
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {
        print("In mouse Dragged");
        print("X: " + mouseEvent.getX() + " Y: " + mouseEvent.getY());
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        print("In mouse moved");
        print("X: " + mouseEvent.getX() + " Y: " + mouseEvent.getY());
    }

    static void print(Object object)
    {
        System.out.println(object);
    }
}

package org.kbashar.hexer;

import java.awt.Dimension;
import java.awt.ScrollPane;
import javax.swing.JFrame;

/**
 * @author Khyrul Bashar
 */
public class Main
{
    public static void main(String[] args)
    {
        Byte[] array = new Byte[100];

        for (byte i = 0; i<100; i++)
        {
            array[i] = i;
            System.out.print((char)(i));
        }

        HexModel model = new HexModel(array);
        //HexPane hexPane = new HexPane(model, controller);
        //ASCIIPane pane = new ASCIIPane(model);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(new HexEditor(model));
        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(692, 300));
        //frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

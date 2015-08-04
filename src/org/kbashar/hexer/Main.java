package org.kbashar.hexer;

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

        frame.getContentPane().add(new HexEditor(model));
        //frame.setPreferredSize(new Dimension(500, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

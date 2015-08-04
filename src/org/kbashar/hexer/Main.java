package org.kbashar.hexer;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * @author Khyrul Bashar
 */
public class Main
{
    public static void main(String[] args)
    {
        Byte[] array = new Byte[100];

        HexController controller = new HexController();

        for (byte i = 0; i<100; i++)
        {
            array[i] = i;
        }

        HexModel model = new HexModel(array);
        HexPane hexPane = new HexPane(model, controller);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(hexPane);
        frame.setPreferredSize(new Dimension(500, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

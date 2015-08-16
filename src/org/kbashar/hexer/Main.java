package org.kbashar.hexer;

import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;

/**
 * @author Khyrul Bashar
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        FileInputStream stream = new FileInputStream("/home/kbashar/java_error_in_IDEA_3988.log");

        HexModel model = new HexModel(toByteArray(stream));
        //HexPane hexPane = new HexPane(model, controller);
        //ASCIIPane pane = new ASCIIPane(model);
        System.out.println(model.size());
        System.out.println(model.totalLine());
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new HexEditor(model));
        frame.setPreferredSize(new Dimension(692, 300));
        //frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static byte[] toByteArray(InputStream is) throws IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }
}

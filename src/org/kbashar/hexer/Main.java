package org.kbashar.hexer;

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

        System.out.println(model.size());
        System.out.println(model.totalLine());
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new HexEditor(model));
        //frame.setPreferredSize(new Dimension(690, 350));
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

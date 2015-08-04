package org.kbashar.hexer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

/**
 * @author Khyrul Bashar
 */
class ByteUnit extends JComponent implements MouseMotionListener, MouseInputListener, KeyListener
{
    private static final Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
    private Color bgColor = new Color(199, 14, 20);
    private boolean redraw = false;
    private String content;
    private String UIState = "Normal";
    private char[] chars = new char[2];

    ByteUnit(String str)
    {
        content = str;
        setPreferredSize(new Dimension(25, 25));
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setOpaque(false);
        setFocusable(true);
        chars[0] = 'B';
        chars[1] = 'F';
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (UIState.equals("Selected"))
        {
            paintInSelected(g);
        }
        else
        {
            g.setFont(font);
            if (redraw)
            {
                g.setColor(bgColor);
                redraw = false;
            }
            else
            {
                g.setColor(new Color(98, 134, 198));
            }
            g.drawString(content, 1, 20);
        }
    }

    private void paintInSelected(Graphics g)
    {
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        g.setColor(Color.white);
        g.fillRect(0, 0, 25, 25);

        g.setColor(Color.gray);
        FontMetrics fm = g.getFontMetrics();
        int width = fm.charWidth(chars[0]);
        g.fill3DRect(0, 0, width, fm.getHeight(), false);

        g.setColor(Color.black);
        g.drawString(new String(chars), 1, 20);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        System.out.println("Inside Byte Unit");
        System.out.println("ByteUnit: x-> " + mouseEvent.getX() + " y-> " + mouseEvent.getY());
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        if (mouseEvent.getClickCount() == 2)
        {
            UIState = "Selected";
            requestFocusInWindow();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent)
    {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent)
    {
       chars[0] = keyEvent.getKeyChar();
        UIState = "Selected";
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {

    }
}

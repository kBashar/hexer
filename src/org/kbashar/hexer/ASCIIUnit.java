package org.kbashar.hexer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

/**
 * @author Khyrul Bashar
 */
public class ASCIIUnit extends JComponent implements MouseMotionListener, MouseInputListener, KeyListener
{
    public static final short TOTAL_PIXEL = 12;

    private static final Font font = new Font(Font.MONOSPACED, Font.BOLD, 10);
    private static final byte SELECTED = 1;
    private static final byte NORMAL = 0;

    public static int selectedIndex = -1;

    private final int index;
    private byte state = NORMAL;
    private char[] content = new char[1];

    private List<ASCIISelectionChangeListener> asciiSelectionChangeListeners = new ArrayList<ASCIISelectionChangeListener>();

    ASCIIUnit(short bt, int i)
    {
        index = i;
        content[0] = Character.toChars(bt)[0];
        if (!isAsciiPrintable(content[0]))
        {
            content[0] = '.';
        }
        setPreferredSize(new Dimension(TOTAL_PIXEL, TOTAL_PIXEL));
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setOpaque(false);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        switch (state)
        {
            case NORMAL:
                paintInNormal(g);
                break;
            case SELECTED:
                paintInSelected(g);
                break;
            default:
                break;
        }
    }

    private void paintInNormal(Graphics g)
    {
        g.setFont(font);
        g.setColor(new Color(98, 134, 198));
        g.drawChars(content, 0, 1, 2, TOTAL_PIXEL - 3);
    }

    private void paintInSelected(Graphics g)
    {
        g.setColor(new Color(250, 150, 150));
        g.drawRect(0, 0, TOTAL_PIXEL-2, TOTAL_PIXEL-2);
        g.setFont(font);
        g.setColor(new Color(98, 134, 198));
        g.drawChars(content, 0, 1, 2, TOTAL_PIXEL-3);
    }

    private void clearSelection()
    {
        state = NORMAL;
        repaint();
    }

    private void putInSelected()
    {
        state = SELECTED;
        selectedIndex = index;
        requestFocusInWindow();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        System.out.println("Inside ASCII Unit");
        System.out.println("ASCII: x-> " + mouseEvent.getX() + " y-> " + mouseEvent.getY());
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        switch (mouseEvent.getClickCount())
        {
            case 1:
                putInSelected();
                break;
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
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
           switch (keyEvent.getKeyCode())
            {
                case 37:
                    fireSelectionChange(SelectEvent.PREVIOUS);
                    break;
                case 39:
                    fireSelectionChange(SelectEvent.NEXT);
                    break;
                case 38:
                    fireSelectionChange(SelectEvent.UP);
                    break;
                case 40:
                    fireSelectionChange(SelectEvent.DOWN);
                    break;
                default:
                    break;
            }
    }

    private void fireSelectionChange(String navigation)
    {
        SelectEvent event = new SelectEvent(index, navigation);

        for (ASCIISelectionChangeListener ls: asciiSelectionChangeListeners)
        {
            ls.ASCIISelectionChanged(event);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {

    }

    public void addASCIISelectionChangeListener(ASCIISelectionChangeListener listener)
    {
        asciiSelectionChangeListeners.add(listener);
    }

    private static boolean isAsciiPrintable(char ch) {
        return ch >= 32 && ch < 127;
    }

    public void setSelected(boolean selected)
    {
        if (selected && selectedIndex != index)
        {
            putInSelected();
        }
        else if (!selected && selectedIndex == index)
        {
            clearSelection();
        }
    }
}

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
class HexUnit extends JComponent implements MouseMotionListener, MouseInputListener, KeyListener
{
    public static final short TOTAL_PIXEL = 30;

    private static final Font font = new Font(Font.MONOSPACED, Font.BOLD, 19);
    private static final byte EDIT = 2;
    private static final byte SELECTED = 1;
    private static final byte NORMAL = 0;

    public static int selectedIndex = -1;

    private final int index;
    private byte state = NORMAL;
    private int selectedChar = 0;
    private short content;
    private char[] chars;

    private List<HexChangeListener> hexChangeListeners = new ArrayList<HexChangeListener>();
    private List<HexSelectionChangeListener> hexSelectionChangeListeners = new ArrayList<HexSelectionChangeListener>();

    HexUnit(short bt, int i)
    {
        index = i;
        content = bt;
        setPreferredSize(new Dimension(TOTAL_PIXEL-5, TOTAL_PIXEL-5));
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setOpaque(false);
        setFocusable(true);

        chars = Integer.toHexString(content).toCharArray();
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
            case EDIT:
                paintInEdit(g);
                break;
            default:
                break;
        }
    }

    private void paintInNormal(Graphics g)
    {
        g.setFont(font);
        g.setColor(new Color(98, 134, 198));
        g.drawString(new String(chars), 1, 20);
    }

    private void paintInSelected(Graphics g)
    {
        g.setColor(new Color(250, 150, 150));
        g.drawRect(0, 0, 24, 24);
        g.setFont(font);
        g.setColor(new Color(98, 134, 198));
        g.drawString(new String(chars), 1, 20);
    }

    private void paintInEdit(Graphics g)
    {
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        g.setColor(Color.white);
        g.fillRect(0, 0, 24, 24);

        if (selectedChar == 0)
        {
            g.setColor(Color.black);
            g.drawChars(chars, 0, 1, 1, 24);

            g.setColor(new Color(98, 134, 198));
            g.drawChars(chars, 1, 1, g.getFontMetrics().charWidth(chars[0]), 24);
        }
        else if (selectedChar == 1)
        {
            g.setColor(new Color(98, 134, 198));
            g.drawChars(chars, 0, 1, 1, 24);

            g.setColor(Color.black);
            g.drawChars(chars, 1, 1, g.getFontMetrics().charWidth(chars[0]), 24);
        }
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

    private void putInNormal()
    {

    }

    private void putInEdit()
    {
        state = EDIT;
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
        System.out.println("Inside Byte Unit");
        System.out.println("ByteUnit: x-> " + mouseEvent.getX() + " y-> " + mouseEvent.getY());
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        switch (mouseEvent.getClickCount())
        {
            case 1:
                putInSelected();
                break;
            case 2:
                selectedChar = 0;
                putInEdit();
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
        char c = keyEvent.getKeyChar();
        System.out.println(c);
        if (isHexChar(c))
        {
            chars[selectedChar] = c;
            short newValue = (short) Integer.parseInt(new String(chars), 16);
            HexChangedEvent event = new HexChangedEvent(content, newValue, index);
            for (HexChangeListener listener: hexChangeListeners)
            {
                listener.hexChanged(event);
            }

            putInEdit();
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        if (state == EDIT)
        {
            switch (keyEvent.getKeyCode())
            {
                case 37:
                    selectedChar = 0;
                    putInEdit();
                    break;
                case 39:
                    selectedChar = 1;
                    putInEdit();
                    break;
                default:
                    break;
            }
            keyEvent.consume();
        }
        else if (state == SELECTED)
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
    }

    private void fireSelectionChange(String navigation)
    {
        SelectEvent event = new SelectEvent(index, navigation);

        for (HexSelectionChangeListener ls: hexSelectionChangeListeners)
        {
            ls.hexSelectionChanged(event);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {

    }

    public void addHexChangeListener(HexChangeListener listener)
    {
        hexChangeListeners.add(listener);
    }

    public void addHexSelectionChangeListner(HexSelectionChangeListener listener)
    {
        hexSelectionChangeListeners.add(listener);
    }

    private static boolean isHexChar(char c)
    {
        return (c >= 0 && c <= 9) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
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

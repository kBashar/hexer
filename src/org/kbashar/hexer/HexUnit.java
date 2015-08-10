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
    public static final short WIDTH = 30;

    private static final Font font = Util.font;
    private static final byte EDIT = 2;
    private static final byte SELECTED = 1;
    private static final byte NORMAL = 0;

    private final int index;
    private byte state = NORMAL;
    private boolean isFirstCharSelected = true;
    private byte content;

    private List<HexChangeListener> hexChangeListeners = new ArrayList<HexChangeListener>();
    private List<SelectionChangeListener> selectionChangeListeners = new ArrayList<SelectionChangeListener>();

    HexUnit(byte bt, int i)
    {
        index = i;
        content = bt;
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
            case EDIT:
                paintInEdit(g);
                break;
            default:
                break;
        }
    }

    @Override
    public Dimension getMaximumSize()
    {
        return new Dimension(WIDTH, Util.CHAR_HEIGHT);
    }

    @Override
    public Dimension getMinimumSize()
    {
        return new Dimension(WIDTH, Util.CHAR_HEIGHT);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(WIDTH, Util.CHAR_HEIGHT);
    }

    private void paintInNormal(Graphics g)
    {
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        g.setColor(new Color(6, 4, 7));
        g.drawChars(getChars(content), 0, 2, 1, Util.CHAR_HEIGHT - 3);
    }

    private void paintInSelected(Graphics g)
    {
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        g.setColor(new Color(98, 134, 198));
        g.drawChars(getChars(content), 0, 2, 1, Util.CHAR_HEIGHT - 4);
    }

    private void paintInEdit(Graphics g)
    {
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        g.setColor(Color.white);

        if (isFirstCharSelected)
        {
            g.setColor(new Color(98, 134, 198));
            g.drawChars(getChars(content), 0, 1, 1, Util.CHAR_HEIGHT - 3);

            g.setColor(Color.black);
            g.drawChars(getChars(content), 1, 1, g.getFontMetrics().charWidth(getChars(content)[0]), Util.CHAR_HEIGHT - 3);
        }
        else
        {
            g.setColor(Color.black);
            g.drawChars(getChars(content), 0, 1, 1, Util.CHAR_HEIGHT - 3);

            g.setColor(new Color(98, 134, 198));
            g.drawChars(getChars(content), 1, 1, g.getFontMetrics().charWidth(getChars(content)[0]), Util.CHAR_HEIGHT - 3);
        }
    }

    public void putInSelected()
    {
        state = SELECTED;
        requestFocusInWindow();
        repaint();
    }

    public void putInNormal()
    {
        state = NORMAL;
        repaint();
    }

    private void putInEdit()
    {
        state = EDIT;
        repaint();
        requestFocusInWindow();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        switch (mouseEvent.getClickCount())
        {
            case 1:
                fireSelectionChange(SelectEvent.IN);
                break;
            case 2:
                isFirstCharSelected = true;
                fireSelectionChange(SelectEvent.EDIT);
                putInEdit();
        }
        mouseEvent.consume();
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
        if (state == EDIT)
        {
            char c = keyEvent.getKeyChar();
            if (isHexChar(c))
            {
                char[] chars = getChars(content);
                chars[isFirstCharSelected ? 0 : 1] = c;
                byte b = getByte(chars);
                if (content != b)
                {
                    content = b;
                    HexChangedEvent event = new HexChangedEvent(content, index);
                    for (HexChangeListener listener: hexChangeListeners)
                    {
                        listener.hexChanged(event);
                    }
                }
                putInEdit();
            }
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
                    isFirstCharSelected = true;
                    putInEdit();
                    break;
                case 39:
                    isFirstCharSelected = false;
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

        for (SelectionChangeListener ls: selectionChangeListeners)
        {
            ls.selectionChanged(event);
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

    void addSelectionChangedListener(SelectionChangeListener listener)
    {
        selectionChangeListeners.add(listener);
    }

    private static boolean isHexChar(char c)
    {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    private char[] getChars(byte b)
    {
        return String.format("%02X", b & 0XFF).toCharArray();
    }

    private byte getByte(char[] chars)
    {
        return  (byte) (Integer.parseInt(new String(chars), 16) & 0XFF);
    }
}

package org.kbashar.hexer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * @author Khyrul Bashar
 */
class ASCIILine extends JComponent
{
    static final short WIDTH =  120;

    private static final Font font = Util.FONT;
    private static final byte SELECTED = 1;
    private static final byte NORMAL = 0;

    private final int index;
    private int selectedIndex;
    private byte state = NORMAL;
    private char[] content;

    private List<SelectionChangeListener> selectionChangeListeners = new ArrayList<SelectionChangeListener>();

    ASCIILine(char[] chars, int i)
    {
        index = i;
        content = chars;
        setOpaque(false);
        setFocusable(true);
    }

    @Override
    public Dimension getMaximumSize()
    {
        return new Dimension(WIDTH, Util.CHAR_HEIGHT+1);
    }

    @Override
    public Dimension getMinimumSize()
    {
        return new Dimension(WIDTH, Util.CHAR_HEIGHT+1);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(WIDTH, Util.CHAR_HEIGHT+1);
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
        g.drawChars(content, 0, content.length, 1, Util.CHAR_HEIGHT - 3);
    }

    private void paintInSelected(Graphics g)
    {
        setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
        int length = g.getFontMetrics().charsWidth(content, 0, selectedIndex-0);
        g.drawChars(content, 0, selectedIndex - 0, 1,Util.CHAR_HEIGHT - 3);
        g.setColor(new Color(98, 134, 198));
        g.drawChars(content, selectedIndex, 1, length + 1, Util.CHAR_HEIGHT - 3);

        g.setColor(Color.black);
        length += g.getFontMetrics().charWidth(content[selectedIndex]);
        g.drawChars(content, selectedIndex + 1, (content.length - 1) - selectedIndex, length + 1, Util.CHAR_HEIGHT - 3);
    }

    private void putInSelected()
    {
        state = SELECTED;
        repaint();
    }

    private void putInNormal()
    {
        state = NORMAL;
        repaint();
    }

    private static boolean isAsciiPrintable(char ch) {
        return ch >= 32 && ch < 127;
    }

    public void addSelectionChangeListener(SelectionChangeListener listener)
    {
        selectionChangeListeners.add(listener);
    }

    void select(int index)
    {
        selectedIndex = index;
        state = SELECTED;
        repaint();
    }


    void clearSelection(int indexInLine)
    {
        selectedIndex = -1;
        state = NORMAL;
        repaint();
    }

    void updateContent(char[] chars)
    {
        content = chars;
        putInNormal();
    }
}

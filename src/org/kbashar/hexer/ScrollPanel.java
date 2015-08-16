package org.kbashar.hexer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * @author Khyrul Bashar
 */
public class ScrollPanel extends JPanel implements Scrollable
{
    public ScrollPanel(FlowLayout layout)
    {
        super(layout);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize()
    {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle rectangle, int orientation, int direction)
    {
        System.out.println(rectangle.getY());
        if (SwingConstants.VERTICAL == orientation)
        {
            return 1;
        }
        return 0;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle rectangle, int orientation, int direction)
    {
        if (SwingConstants.VERTICAL == orientation)
        {
            return Util.CHAR_HEIGHT + 2;
        }
        return 0;
    }

    @Override
    public boolean getScrollableTracksViewportWidth()
    {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight()
    {
        return true;
    }
}

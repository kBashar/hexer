package org.kbashar.hexer;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 * @author Khyrul Bashar
 *
 * This class shows the hex values of the bytes in the HexViewer.
 */
class HexPane extends JPanel implements HexSelectionChangeListener, MouseListener
{
    private HexModel model;
    private static final int WIDTH = 480;
    private static final int HEIGHT = 330;

    HexPane(HexModel model, HexChangeListener listener)
    {
        this.model = model;
        createUI(listener);
    }

    private void createUI(HexChangeListener listener)
    {
        addMouseListener(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < model.size(); i++)
        {
            HexUnit unit = new HexUnit(model.getByte(i), i);
            unit.addHexChangeListener(listener);
            unit.addHexSelectionChangeListner(this);
            add(unit);
        }
        requestFocusInWindow();
    }

    @Override
    public void hexSelectionChanged(SelectEvent event)
    {
        int index = event.getHexIndex();
        if (event.getNavigation().equals(SelectEvent.NEXT))
        {
            index += 1;
        }
        else if (event.getNavigation().equals(SelectEvent.PREVIOUS))
        {
            index -= 1;
        }
        else if (event.getNavigation().equals(SelectEvent.UP))
        {
            index -= 16;
        }
        else if (event.getNavigation().equals(SelectEvent.DOWN))
        {
            index += 16;
        }
        if ( index >= 0 && index <= model.size()-1 )
        {
            Component component = getComponent(index);
            if (HexUnit.selectedIndex >= 0)
            {
                ((HexUnit)getComponent(HexUnit.selectedIndex)).setSelected(false);
            }

            HexUnit unit = (HexUnit) component;
            unit.setSelected(true);
        }
        System.out.println(event.getHexIndex() + "\n" + event.getNavigation());
    }

    private Point indexToPoint(int index)
    {
        int x = index % 16;
        int y = index / 16 + (index%16) > 0 ? 1 : 0;
        x = x * HexUnit.TOTAL_PIXEL;
        y = y * HexUnit.TOTAL_PIXEL;
        return new Point(x -5, y-5);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        if (HexUnit.selectedIndex >= 0)
        {
            ((HexUnit)getComponent(HexUnit.selectedIndex)).setSelected(false);
        }
        requestFocusInWindow();
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
}
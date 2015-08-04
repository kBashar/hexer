package org.kbashar.hexer;

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
class HexPane extends JPanel implements  MouseListener
{
    private HexModel model;
    private SelectionChangeListener listener;

    private static final int WIDTH = 480;
    private static final int HEIGHT = 330;

    HexPane(HexModel model, HexChangeListener listener, SelectionChangeListener selectionChangeListener)
    {
        this.model = model;
        this.listener = selectionChangeListener;
        createUI(listener, selectionChangeListener);
    }

    private void createUI(HexChangeListener listener, SelectionChangeListener selectionChangeListener)
    {
        addMouseListener(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < model.size(); i++)
        {
            HexUnit unit = new HexUnit(model.getByte(i), i);
            unit.addHexChangeListener(listener);
            unit.addSelectionChangedListener(selectionChangeListener);
            add(unit);
        }
        requestFocusInWindow();
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
        listener.selectionChanged(new SelectEvent(-1, SelectEvent.NONE));
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
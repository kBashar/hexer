package org.kbashar.hexer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * @author Khyrul Bashar
 *
 * This class shows the hex values of the bytes in the HexViewer.
 */
class HexPane extends JPanel implements  MouseListener
{
    private HexModel model;
    private SelectionChangeListener listener;

    static final int WIDTH = (int) (Util.WIDTH_UNIT * 4.6);
    static final int HEIGHT = 330;

    HexPane(HexModel model, HexChangeListener listener, SelectionChangeListener selectionChangeListener)
    {
        this.model = model;
        this.listener = selectionChangeListener;
        createUI(listener, selectionChangeListener);
    }

    private void createUI(HexChangeListener listener, SelectionChangeListener selectionChangeListener)
    {
        addMouseListener(this);
        setOpaque(true);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
        for (int i = 0; i < model.size(); i++)
        {
            HexUnit unit = new HexUnit(model.getByte(i), i);
            unit.addHexChangeListener(listener);
            unit.addSelectionChangedListener(selectionChangeListener);
            add(unit);
        }
        requestFocusInWindow();
    }

    @Override
    public Dimension getMaximumSize()
    {
        return new Dimension(WIDTH, getHeight());
    }

    @Override
    public Dimension getMinimumSize()
    {
        return new Dimension(WIDTH, getHeight());
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
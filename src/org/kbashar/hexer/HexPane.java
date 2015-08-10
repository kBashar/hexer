package org.kbashar.hexer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * @author Khyrul Bashar
 *
 * This class shows the hex values of the bytes in the HexViewer.
 */
class HexPane extends JPanel implements  MouseListener, HexChangeListener
{
    private HexModel model;

    static final int WIDTH = (int) (Util.WIDTH_UNIT * 4.6);
    static final int HEIGHT = 330;
    private ArrayList<BlankClickListener> blankClickListeners = new ArrayList<BlankClickListener>();

    HexPane(HexModel model, SelectionChangeListener selectionChangeListener)
    {
        this.model = model;
        createUI(selectionChangeListener);
    }

    private void createUI(SelectionChangeListener selectionChangeListener)
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
            unit.addHexChangeListener(this);
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

    public void addBlankClickListener(BlankClickListener listener)
    {
        blankClickListeners.add(listener);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        for (BlankClickListener listener: blankClickListeners)
        {
            listener.blankClick(mouseEvent.getPoint());
        }
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

    public void select(int index)
    {
        ((HexUnit)getComponent(index)).putInSelected();
    }

    public void hexChanged(HexChangedEvent event)
    {
        model.updateModel(event.getByteIndex(), event.getNewValue());
    }

    public void clearSelection(int index)
    {
        ((HexUnit)getComponent(index)).putInNormal();
    }


}
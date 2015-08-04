package org.kbashar.hexer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

/**
 * @author Khyrul Bashar
 */
class ASCIIPane extends JComponent implements MouseListener
{
    private HexModel model;
    private SelectionChangeListener listener;

    private static final int LINE_WIDTH = 200;
    private static final int LINE_HEIGHT = 330;

    ASCIIPane(HexModel model, SelectionChangeListener listener)
    {
        this.model = model;
        this.listener = listener;
        createUI(listener);
    }

    private void createUI(SelectionChangeListener listener)
    {
        setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        for (int i = 0; i < model.size(); i++)
        {
            ASCIIUnit unit = new ASCIIUnit(model.getByte(i), i);
            unit.addSelectionChangeListener(listener);
            add(unit);
        }
        requestFocusInWindow();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        this.listener.selectionChanged(new SelectEvent(-1, SelectEvent.NONE));
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

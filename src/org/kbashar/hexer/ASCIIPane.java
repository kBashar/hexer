package org.kbashar.hexer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;

/**
 * @author Khyrul Bashar
 */
class ASCIIPane extends JComponent implements MouseListener
{
    private HexModel model;
    private SelectionChangeListener listener;

    static final int LINE_WIDTH = Util.WIDTH_UNIT * 1;
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
        setBorder(new BevelBorder(BevelBorder.RAISED));
        for (int i = 1; i <= model.totalLine(); i++)
        {
            System.out.println(model.getLineChars(i));
            ASCIILine line = new ASCIILine(model.getLineChars(i), i-1);
            line.addSelectionChangeListener(listener);
            add(line);
        }
        requestFocusInWindow();
    }

    void select(int index)
    {
        System.out.println("Index: " + index +
                " Line No: " + HexModel.lineNumber(index) +
                " Element No: " + HexModel.elementIndexInLine(index));
        ASCIILine line = (ASCIILine) getComponent(HexModel.lineNumber(index)-1);
        line.select(HexModel.elementIndexInLine(index));
    }

    void clearSelection(int index)
    {
        ASCIILine line = (ASCIILine) getComponent(HexModel.lineNumber(index)-1);
        line.clearSelection(HexModel.elementIndexInLine(index));
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        select(18);
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

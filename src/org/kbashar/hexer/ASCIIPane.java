package org.kbashar.hexer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;

/**
 * @author Khyrul Bashar
 */
class ASCIIPane extends JComponent implements MouseListener, HexModelChangeListener
{
    private HexModel model;

    static final int LINE_WIDTH = ASCIILine.WIDTH;
    private static final int LINE_HEIGHT = 330;
    private ArrayList<BlankClickListener> blankClickListeners = new ArrayList<BlankClickListener>();

    ASCIIPane(HexModel model, SelectionChangeListener listener)
    {
        this.model = model;
        createUI(listener);
    }

    private void createUI(SelectionChangeListener listener)
    {
        addMouseListener(this);
        setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBorder(new BevelBorder(BevelBorder.RAISED));
        model.addHexModelChangeListener(this);
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

    @Override
    public void hexModelChanged(HexModelChangedEvent event)
    {
        if (event.getChangeType() == HexModelChangedEvent.SINGLE_CHANGE)
        {
            int lineNumber = HexModel.lineNumber(event.getStartIndex());
            ((ASCIILine) getComponent(lineNumber - 1)).updateContent(model.getLineChars(lineNumber));
        }
    }
}

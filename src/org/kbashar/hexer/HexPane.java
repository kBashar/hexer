package org.kbashar.hexer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;

/**
 * @author Khyrul Bashar
 *
 * This class shows the hex values of the bytes in the HexViewer.
 */
final class HexPane extends JPanel implements  MouseListener, HexChangeListener, AdjustmentListener
{
    private HexModel model;

    static final int WIDTH = (int) (Util.WIDTH_UNIT * 4.6);
    private ArrayList<BlankClickListener> blankClickListeners = new ArrayList<BlankClickListener>();
    private static final int MAXIMUM_LINE  = 10;
    private SelectionChangeListener selectionChangeListener;
    private int totalRenderedLine = 0;

    HexPane(HexModel model, SelectionChangeListener selectionChangeListener)
    {
        this.model = model;
        this.selectionChangeListener = selectionChangeListener;
        createUI();
    }

    private void createUI()
    {
        addMouseListener(this);
        setOpaque(true);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, (Util.CHAR_HEIGHT+2)*model.totalLine()));
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        updateLinesFrom(1, model.totalLine());
        requestFocusInWindow();
    }

    /**
     * This adds hex unit in hexpane from the specified line up to end line.
     * @param startLine int, the starting line.
     * @param endLine int, the ending line
     */
    public void updateLinesFrom(int startLine, int endLine)
    {
        new HexUnitPainter(startLine, endLine, this).execute();
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


    @Override
    public void adjustmentValueChanged(AdjustmentEvent adjust)
    {
       /* Adjustable adjustable = adjust.getAdjustable();
        System.out.println(adjustable.getMaximum());
        System.out.println(model.totalLine());
        System.out.println(adjust.paramString());
        if (adjust.getAdjustmentType() == AdjustmentEvent.TRACK)
        {
            int value = adjust.getValue()/16;
            if (value > totalRenderedLine && value <= model.totalLine())
            {
                updateLinesFrom(totalRenderedLine + 1, value);
            }
            System.out.println("Line no: " + value);
        }*/
    }

    private final class HexUnitPainter extends SwingWorker
    {
        int startLine;
        int endLine;
        HexChangeListener listener;

        HexUnitPainter(int i,int j, HexChangeListener listener)
        {
            startLine = i;
            endLine = j;
            this.listener = listener;
        }

        @Override
        protected Object doInBackground() throws Exception
        {
            for (; startLine <= endLine; startLine++)
            {
                byte[] bytes = model.getBytesForLine(startLine);
                int byteIndex = (startLine-1)*16;
                for (byte b: bytes)
                {
                    HexUnit unit = new HexUnit(b, byteIndex);
                    unit.addHexChangeListener(listener);
                    unit.addSelectionChangedListener(selectionChangeListener);
                    add(unit);
                    byteIndex++;
                }
                totalRenderedLine++;
                //TODO check if repaint even necessary
                repaint();
                revalidate();
            }
            return null;
        }
    }
}
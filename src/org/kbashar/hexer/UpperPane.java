package org.kbashar.hexer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * @author Khyrul Bashar
 */
class UpperPane extends JPanel implements MouseListener
{
    private int height = 30;
    private ArrayList<BlankClickListener> blankClickListeners = new ArrayList<BlankClickListener>();

    UpperPane()
    {
        super();
        createView();
    }

    private void createView()
    {
        addMouseListener(this);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(690, height));
        setBorder(new BevelBorder(BevelBorder.RAISED));

        JPanel offset = new JPanel(new BorderLayout());
        JLabel offsetLabel = new JLabel("Offset");
        offsetLabel.setHorizontalAlignment(SwingConstants.CENTER);
        offsetLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        offset.add(offsetLabel, BorderLayout.CENTER);
        offset.setPreferredSize(new Dimension(AddressPane.WIDTH, height));

        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
        middlePanel.setPreferredSize(new Dimension(HexPane.WIDTH, height));
        for (int i = 0; i < 16; i++)
        {
            JLabel label = new JLabel(String.format("%02X", i));
            label.setFont(Util.font);
            label.setPreferredSize(new Dimension(HexUnit.WIDTH, height));
            middlePanel.add(label);
        }

        JPanel asciiDump = new JPanel(new BorderLayout());
        asciiDump.setPreferredSize(new Dimension(ASCIIPane.LINE_WIDTH, height));
        JLabel asciiDumpLabel = new JLabel("Dump");
        asciiDumpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        asciiDumpLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        asciiDump.add(asciiDumpLabel);

        add(offset, BorderLayout.LINE_START);
        add(middlePanel, BorderLayout.CENTER);
        add(asciiDump, BorderLayout.LINE_END);
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
}

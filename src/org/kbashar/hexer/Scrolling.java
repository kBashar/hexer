package org.kbashar.hexer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * @author Khyrul Bashar
 */
public class Scrolling extends JPanel implements AdjustmentListener, MouseWheelListener
{
    JScrollBar scrollBar;
    Scrolling()
    {
        JPanel panel = new JPanel();
        scrollBar = new JScrollBar(SwingConstants.VERTICAL);
        scrollBar.addAdjustmentListener(this);
        scrollBar.setPreferredSize(new Dimension(30, 300));
        panel.add(scrollBar);

        JLabel label = new JLabel("Hello");

        setBorder(new BevelBorder(BevelBorder.RAISED, Color.GREEN, Color.RED));

        add(label);
        add(panel);
        this.addMouseWheelListener(this);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(300, 400));
        frame.getContentPane().add(new Scrolling());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent)
    {
        System.out.println(adjustmentEvent.getValue());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent)
    {
        System.out.println("Scroll unit: " + mouseWheelEvent.getUnitsToScroll());

        System.out.println(mouseWheelEvent.paramString());
        if (mouseWheelEvent.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
        {
            int total = mouseWheelEvent.getUnitsToScroll() * scrollBar.getUnitIncrement();
            scrollBar.setValue(scrollBar.getValue() + total);
        }
    }
}
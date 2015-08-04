package org.kbashar.hexer;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * @author Khyrul Bashar
 */
public class CustomComponent extends JPanel
{
    CustomComponent(FlowLayout layout)
    {
        super(layout);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(new Color(98, 134, 198));
        g.drawString(".", 92, 35);
    }

}

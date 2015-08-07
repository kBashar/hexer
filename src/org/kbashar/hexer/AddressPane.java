package org.kbashar.hexer;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * @author Khyrul Bashar
 *
 * This class shows the address of the currently selected byte.
 */
public class AddressPane extends JPanel
{
    private Color bgColor = new Color(123, 67, 199);
    static final int WIDTH = (int) (Util.WIDTH_UNIT * 0.6);

    AddressPane(int total)
    {
        createView(total);
    }

    void createView(int total)
    {
        setPreferredSize(new Dimension(WIDTH, 330));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new BevelBorder(BevelBorder.RAISED));
        for (int i = 0; i< total; i++)
        {
            JLabel label = new JLabel(String.format("%07X", i*16));
            label.setFont(Util.font);
            label.setOpaque(true);
            label.setBorder(new BevelBorder(BevelBorder.RAISED));

            label.setMaximumSize(new Dimension(WIDTH, Util.CHAR_HEIGHT+1));
            label.setPreferredSize(new Dimension(WIDTH, Util.CHAR_HEIGHT+1));
            label.setMinimumSize(new Dimension(WIDTH, Util.CHAR_HEIGHT+1));
            add(label);
        }
    }

    void updateAddress(int presentIndex)
    {
        int presentLine = HexModel.lineNumber(presentIndex + 1);
        JLabel label = (JLabel) getComponent(presentLine-1);
        label.setBackground(bgColor);
        label.setText(String.format("%07X", presentIndex));
    }

    void clearSelection(int index)
    {
        int line = HexModel.lineNumber(index);
        if (index != -1)
        {
            JLabel label = (JLabel)getComponent(line - 1);
            label.setBackground(Color.WHITE);
            label.setText(String.format("%07X", 16*(line -1)));
        }
    }
}

package org.kbashar.hexer;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JComponent;

/**
 * @author Khyrul Bashar
 */
class ASCIIPane extends JComponent implements ASCIISelectionChangeListener
{
    private HexModel model;

    private static final int LINE_WIDTH = 200;
    private static final int LINE_HEIGHT = 330;

    ASCIIPane(HexModel model)
    {
        this.model = model;
        createUI();
    }

    private void createUI()
    {
        setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        for (int i = 0; i < model.size(); i++)
        {
            ASCIIUnit unit = new ASCIIUnit(model.getByte(i), i);
            unit.addASCIISelectionChangeListener(this);
            add(unit);
        }
        requestFocusInWindow();
    }

    @Override
    public void ASCIISelectionChanged(SelectEvent event)
    {
        int index = event.getHexIndex();
        if (event.getNavigation().equals(SelectEvent.NEXT))
        {
            index += 1;
        }
        else if (event.getNavigation().equals(SelectEvent.PREVIOUS))
        {
            index -= 1;
        }
        else if (event.getNavigation().equals(SelectEvent.UP))
        {
            index -= 16;
        }
        else if (event.getNavigation().equals(SelectEvent.DOWN))
        {
            index += 16;
        }
        if ( index >= 0 && index <= model.size()-1 )
        {
            Component component = getComponent(index);
            if (ASCIIUnit.selectedIndex >= 0)
            {
                ((ASCIIUnit)getComponent(ASCIIUnit.selectedIndex)).setSelected(false);
            }

            ASCIIUnit unit = (ASCIIUnit) component;
            unit.setSelected(true);
        }
        System.out.println(event.getHexIndex() + "\n" + event.getNavigation());
    }
}

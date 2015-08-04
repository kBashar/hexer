package org.kbashar.hexer;

import java.awt.FlowLayout;
import javax.swing.JPanel;

/**
 * @author Khyrul Bashar
 */
public class HexEditor extends JPanel implements HexChangeListener, SelectionChangeListener
{
    private HexModel model;
    private HexPane hexPane;
    private ASCIIPane asciiPane;
    private int selectedIndex = -1;

    HexEditor(HexModel model)
    {
        super();
        this.model = model;
        createView();
    }

    private void createView()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        hexPane = new HexPane(model, this);
        asciiPane = new ASCIIPane(model);

        add(hexPane);
        add(asciiPane);
    }

    @Override
    public void hexChanged(HexChangedEvent event)
    {
        System.out.println( "Index: " +event.getByteIndex() +
                "\nOld Value: " + event.getOldValue() +
                "\nNew value: " + event.getNewValue());
    }

    @Override
    public void selectionChanged(SelectEvent event)
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
            if (selectedIndex >= 0)
            {
                ((ASCIIUnit)asciiPane.getComponent(selectedIndex)).setSelected(false);
                ((HexUnit)hexPane.getComponent(selectedIndex)).setSelected(false);
            }

            ((ASCIIUnit)asciiPane.getComponent(index)).setSelected(true);
            ((HexUnit)hexPane.getComponent(index)).setSelected(true);
        }
        System.out.println(event.getHexIndex() + "\n" + event.getNavigation());
    }
}

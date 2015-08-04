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
    private AddressPane addressPane;

    public static int selectedIndex = -1;

    HexEditor(HexModel model)
    {
        super();
        this.model = model;
        createView();
    }

    private void createView()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        addressPane = new AddressPane(model.size()/16 + 1);
        hexPane = new HexPane(model, this, this);
        asciiPane = new ASCIIPane(model, this);

        add(addressPane);
        add(hexPane);
        add(asciiPane);
    }

    @Override
    public void hexChanged(HexChangedEvent event)
    {
        System.out.println("Index: " + event.getByteIndex() +
                "\nOld Value: " + event.getOldValue() +
                "\nNew value: " + event.getNewValue());
    }

    @Override
    public void selectionChanged(SelectEvent event)
    {
        int index = event.getHexIndex();
        if (event.getNavigation().equals(SelectEvent.NONE))
        {
            clearSelections();
            selectedIndex = -1;
            return;
        }
        else if (event.getNavigation().equals(SelectEvent.NEXT))
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
                clearSelections();
            }

            ((ASCIIUnit)asciiPane.getComponent(index)).setSelected(true);
            ((HexUnit)hexPane.getComponent(index)).setSelected(true);
            addressPane.updateAddress(selectedIndex, index);
            selectedIndex = index;

        }
        System.out.println(event.getHexIndex() + "\n" + event.getNavigation());
    }

    private void clearSelections()
    {
        if (selectedIndex != -1)
        {
            ((ASCIIUnit)asciiPane.getComponent(selectedIndex)).setSelected(false);
            ((HexUnit)hexPane.getComponent(selectedIndex)).setSelected(false);
        }
    }
}

package org.kbashar.hexer;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
    private UpperPane upperPane;

    public static int selectedIndex = -1;

    HexEditor(HexModel model)
    {
        super();
        this.model = model;
        createView();
    }

    private void createView()
    {
        setLayout(new BorderLayout());
        addressPane = new AddressPane(model.size()/16 + 1);
        hexPane = new HexPane(model, this, this);
        asciiPane = new ASCIIPane(model, this);
        upperPane = new UpperPane();

        add(upperPane, BorderLayout.PAGE_START);
        add(addressPane, BorderLayout.LINE_START);
        add(hexPane, BorderLayout.CENTER);
        add(asciiPane, BorderLayout.LINE_END);
    }

    @Override
    public void hexChanged(HexChangedEvent event)
    {
        System.out.println("Index: " + event.getByteIndex() +
                "\nOld Value: " + event.getOldValue() +
                "\nNew value: " + event.getNewValue());
    }

    @Override
    public Dimension getMaximumSize()
    {
        return new Dimension(572, getHeight());
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

            asciiPane.select(index);
            ((HexUnit)hexPane.getComponent(index)).setSelected(true);
            addressPane.updateAddress(selectedIndex, index);
            selectedIndex = index;

        }
    }

    private void clearSelections()
    {
        if (selectedIndex != -1)
        {
            asciiPane.clearSelection(selectedIndex);
            ((HexUnit)hexPane.getComponent(selectedIndex)).setSelected(false);
        }
    }
}

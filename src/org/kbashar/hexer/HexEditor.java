package org.kbashar.hexer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import javax.swing.JPanel;

/**
 * @author Khyrul Bashar
 */
public class HexEditor extends JPanel implements HexChangeListener, SelectionChangeListener, BlankClickListener
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
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        addressPane = new AddressPane(model.size()/16 + 1);
        hexPane = new HexPane(model, this, this);
        asciiPane = new ASCIIPane(model, this);
        upperPane = new UpperPane();

        addressPane.addBlankClickListener(this);
        hexPane.addBlankClickListener(this);
        asciiPane.addBlankClickListener(this);
        upperPane.addBlankClickListener(this);

        add(upperPane, BorderLayout.PAGE_START);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.add(addressPane);
        panel.add(hexPane);
        panel.add(asciiPane);
        add(panel);
    }

    @Override
    public void hexChanged(HexChangedEvent event)
    {
        System.out.println("Index: " + event.getByteIndex() +
                "\nNew value: " + event.getNewValue());
    }

    @Override
    public Dimension getMaximumSize()
    {
        return new Dimension(690, getHeight());
    }

    @Override
    public Dimension getMinimumSize()
    {
        return new Dimension(690, getHeight());
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(690, getHeight());
    }

    @Override
    public void selectionChanged(SelectEvent event)
    {
        int index = event.getHexIndex();
        if (event.getNavigation().equals(SelectEvent.NONE))
        {
            clearSelections();
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
            addressPane.updateAddress(index);
            if (!event.getNavigation().equals(SelectEvent.EDIT))
            {
                System.out.println("Not Edit");
                hexPane.select(index);
            }
            selectedIndex = index;
        }
    }

    private void clearSelections()
    {
        if (selectedIndex != -1)
        {
            asciiPane.clearSelection(selectedIndex);
            hexPane.clearSelection(selectedIndex);
            addressPane.clearSelection(selectedIndex);
            selectedIndex = -1;
        }
    }

    @Override
    public void blankClick(Point point)
    {
        clearSelections();
    }
}

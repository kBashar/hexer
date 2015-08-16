package org.kbashar.hexer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * @author Khyrul Bashar
 */
public class HexEditor extends JPanel implements SelectionChangeListener, BlankClickListener
{
    private HexModel model;
    private HexPane hexPane;
    private ASCIIPane asciiPane;
    private AddressPane addressPane;
    private UpperPane upperPane;

    private Action jumpToIndex = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            JDialog dialog = createJumpDialog();
            dialog.setVisible(true);
        }
    };

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
        hexPane = new HexPane(model, this);
        asciiPane = new ASCIIPane(model, this);
        upperPane = new UpperPane();

        addressPane.addBlankClickListener(this);
        hexPane.addBlankClickListener(this);
        asciiPane.addBlankClickListener(this);
        upperPane.addBlankClickListener(this);

        add(upperPane, BorderLayout.PAGE_START);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setPreferredSize(new Dimension(700, (Util.CHAR_HEIGHT + 2) * model.totalLine()));
        panel.add(addressPane);
        panel.add(hexPane);
        panel.add(asciiPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(700, 300));

        JScrollBar scrollBar = scrollPane.createVerticalScrollBar();
        scrollBar.setUnitIncrement(Util.CHAR_HEIGHT+1);
        scrollBar.setBlockIncrement(Util.CHAR_HEIGHT+1);
        scrollBar.setValues(0, 30, 1, model.totalLine() * (Util.CHAR_HEIGHT + 1));
        scrollBar.addAdjustmentListener(hexPane);
        scrollPane.setVerticalScrollBar(scrollBar);

        scrollPane.setViewportView(panel);

        add(scrollPane);

        KeyStroke jumpKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK);
        this.getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(jumpKeyStroke, "jump");
        this.getActionMap().put("jump", jumpToIndex);
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

    private JDialog createJumpDialog()
    {
        JDialog dialog = new JDialog(SwingUtilities.windowForComponent(this), "Test Jump");
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().add(new JLabel("Hello"));
        dialog.setPreferredSize(new Dimension(200, 200));
        dialog.pack();
        return dialog;
    }
}

package org.kbashar.hexer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
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

    private JScrollBar verticalScrollBar;

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

        addressPane = new AddressPane(model.totalLine(), model);
        hexPane = new HexPane(model);
        hexPane.addHexChangeListeners(model);
        asciiPane = new ASCIIPane(model);
        upperPane = new UpperPane();

        model.addHexModelChangeListener(hexPane);
        model.addHexModelChangeListener(asciiPane);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setPreferredSize(new Dimension(620, (Util.CHAR_HEIGHT) * model.totalLine()));
        panel.add(addressPane);
        panel.add(hexPane);
        panel.add(asciiPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(panel);
        scrollPane.setPreferredSize(new Dimension(690, Util.CHAR_HEIGHT * 20));

        scrollPane.getActionMap().put("unitScrollDown", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            }
        });

        scrollPane.getActionMap().put("unitScrollUp", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            }
        });

        verticalScrollBar = scrollPane.createVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(Util.CHAR_HEIGHT);
        verticalScrollBar.setBlockIncrement(Util.CHAR_HEIGHT );
        verticalScrollBar.setValues(0, 1, 0,(model.totalLine() +1) * (Util.CHAR_HEIGHT));
        scrollPane.setVerticalScrollBar(verticalScrollBar);

        scrollPane.setViewportView(panel);
        add(scrollPane);

        hexPane.addSelectionChangeListener(this);
    }

    @Override
    public Dimension getMaximumSize()
    {
        return new Dimension(690, 300);
    }

    @Override
    public Dimension getMinimumSize()
    {
        return new Dimension(690, 300);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(690, 300);
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
        if (index >= 0 && index <= model.size() - 1)
        {
            hexPane.setSelected(index);
            addressPane.setSelected(index);
            asciiPane.setSelected(index);
        }
    }

    @Override
    public void blankClick(Point point)
    {
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

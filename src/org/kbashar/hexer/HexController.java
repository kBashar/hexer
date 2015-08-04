package org.kbashar.hexer;

/**
 * @author Khyrul Bashar
 */
public class HexController implements HexChangeListener
{

    @Override
    public void hexChanged(HexChangedEvent event)
    {
        System.out.println( "Index: " +event.getByteIndex() +
                "\nOld Value: " + event.getOldValue() +
                "\nNew value: " + event.getNewValue());
    }
}

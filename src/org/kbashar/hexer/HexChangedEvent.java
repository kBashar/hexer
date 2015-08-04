package org.kbashar.hexer;

/**
 * @author Khyrul Bashar
 */
public class HexChangedEvent
{
    private final short oldValue;
    private final short newValue;
    private final int byteIndex;

    public HexChangedEvent(short oldValue, short newValue, int byteIndex)
    {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.byteIndex = byteIndex;
    }

    public short getOldValue()
    {
        return oldValue;
    }

    public short getNewValue()
    {
        return newValue;
    }

    public int getByteIndex()
    {
        return byteIndex;
    }
}

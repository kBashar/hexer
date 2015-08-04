package org.kbashar.hexer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Khyrul Bashar
 */
public class HexModel
{
    private ArrayList<Byte> data;

    public HexModel(Byte[] bytes)
    {
        data = new ArrayList<Byte>();
        data.ensureCapacity(bytes.length);

        data.addAll(Arrays.asList(bytes));
    }

    public byte getByte(int index)
    {
        return data.get(index).byteValue();
    }

    /**
     * Provides the size of the model i.e. size of the input.
     * @return int value.
     */
    public int size()
    {
        return data.size();
    }
}

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

    public int totalLine()
    {
        return size() % 16 != 0 ? size()/16 + 1 : size()/16;
    }

    public static int lineNumber(int index)
    {
        if (index == -1)
        {
            return 0;
        }
        else if (index == 0)
        {
            return 1;
        }
        else
        {
            return index % 16 != 0 ? index/16 + 1 : index/16;
        }
    }
}

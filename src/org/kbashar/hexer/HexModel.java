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
     * Provides a character array of 16 characters on availability.
     * @param lineNumber int. The line number of the characters. Line counting starts from 1.
     * @return A char array.
     */
    public char[] getLineChars(int lineNumber)
    {
        int start = (lineNumber-1) * 16;
        int length = data.size() - start < 16 ? data.size() - start:16;
        char[] chars = new char[length];

        for (int i = 0; i < chars.length; i++)
        {
            char c = Character.toChars(data.get(start++))[0];
            if (!isAsciiPrintable(c))
            {
                c = '.';
            }
            chars[i] = c;
        }
        return chars;
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
        index += 1;
        if (index == 0)
        {
            return 0;
        }
        else
        {
            return index % 16 != 0 ? index/16 + 1 : index/16;
        }
    }

    public static int elementIndexInLine(int index)
    {
        return index%16;
    }

    private static boolean isAsciiPrintable(char ch) {
        return ch >= 32 && ch < 127;
    }
}

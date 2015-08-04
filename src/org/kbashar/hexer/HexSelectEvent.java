package org.kbashar.hexer;

/**
 * @author Khyrul Bashar
 */
public class HexSelectEvent
{
    public static final String NEXT = "next";
    public static final String PREVIOUS = "previous";
    public static final String UP = "up";
    public static final String DOWN= "down";

    private final int hexIndex;
    private final String navigation;

    HexSelectEvent(int ind, String nav)
    {
        hexIndex = ind;
        navigation = nav;
    }

    public int getHexIndex()
    {
        return hexIndex;
    }

    public String getNavigation()
    {
        return navigation;
    }
}

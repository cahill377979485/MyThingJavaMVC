package com.my.mythings;

import com.my.mythings.xutil.MyUtil;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void init() {
        arr = MyUtil.getNameAndPrice(input);
    }

    private String input = "a1.100";
    private String[] arr;

    @Test
    public void getName() {
        assertEquals("a", arr[0]);
    }

    @Test
    public void getPrice() {
        assertEquals("1.100", arr[1]);
    }
}
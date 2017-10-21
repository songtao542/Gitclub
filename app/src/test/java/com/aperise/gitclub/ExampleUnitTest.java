package com.aperise.gitclub;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        SimpleDateFormat DFS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        System.out.println("dddd=" + DFS.format(new Date()));

        SimpleDateFormat DFT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String s = "2017-05-28T05:42:49Z";
        System.out.println("dddd1=" + DFT.format(DFS.parse(s)));

    }
}
package com.example.githubrepoinfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class IssueAdapterTest {

	IssueAdapter adapterUnderTest = new IssueAdapter(null, new ArrayList<>());

    @Test
    public void useAppContext() {
        int actualIssueCount = adapterUnderTest.getItemCount();
        assertEquals(0, actualIssueCount);
    }
}
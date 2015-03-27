package edu.temple.cis3238.gravity.gravity.model.physicd2d;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/26/2015
 */
public class Region2DTest extends TestCase {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetD2xGrav() throws Exception {
        Region2D reg = new Region2D();
        assertEquals(0.0f, reg.getD2xGrav(), 0.0f);
    }

    @Test
    public void testSetD2xGrav() throws Exception {
        Region2D reg = new Region2D();
        reg.setD2xGrav(2.0f);
        assertEquals(2.0f, reg.getD2xGrav(), 0.0f);
    }

    @Test
    public void testGetD2yGrav() throws Exception {
        Region2D reg = new Region2D();
        assertEquals(0.0f, reg.getD2yGrav(), 0.0f);
    }

    @Test
    public void testSetD2yGrav() throws Exception {
        Region2D reg = new Region2D();
        reg.setD2yGrav(5.0f);
        assertEquals(5.0f, reg.getD2yGrav(), 0.0f);
    }

    @Test
    public void testGetOccupantID() throws Exception {
        Region2D reg = new Region2D();
        assertEquals(-1, reg.getOccupantID());
    }

    @Test
    public void testSetOccupantID() throws Exception {
        Region2D reg = new Region2D();
        reg.setOccupantID(5);
        assertEquals(5, reg.getOccupantID());
    }
}
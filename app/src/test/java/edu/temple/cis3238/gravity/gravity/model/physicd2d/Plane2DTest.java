package edu.temple.cis3238.gravity.gravity.model.physicd2d;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import android.test.AndroidTestCase;

import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.model.Point;

import static org.junit.Assert.assertNotEquals;


public class Plane2DTest extends TestCase {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRegionsConstructorTruncatesAsExpected() throws Exception {
        List<Region2D> regions = new LinkedList<>();
        for(int index = 0; index < 23; index ++) {
            regions.add(new Region2D(index));
        }
        Plane2D plane = new Plane2D(5, regions);
        Point position = new Point(0, 0);
        assertEquals("<0, 0> should have id 0.", 0, plane.accessRegion(position).getOccupantID());
        position = new Point(5, 0);
        assertEquals("<5, 0> is out of bounds and should be null.", null, plane.accessRegion(position));
        position = new Point(0, 4);
        assertEquals("<0, 4> is out of bounds and should be null.", null, plane.accessRegion(position));
        position = new Point(4, 3);
        assertEquals("<4, 3> is the last valid point and should have id 19.", 19, plane.accessRegion(position).getOccupantID());
        position = new Point(1, 4);
        assertEquals("<5, 6> is out of bounds and should be null.", null, plane.accessRegion(position));

    }

    @Test
    public void testDefineRegionValidInput() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(2, 3);
        plane.defineRegion(position, new Region2D(9.0f, 12.0f, 5));
        Region2D regionCreated = plane.accessRegion(position);
        assertEquals("Unexpected value for d2xGrav found in specified region.", 9.0f, regionCreated.getD2xGrav(), 0.0f);
        assertEquals("Unexpected value for d2yGrav found in specified region.", 12.0f, regionCreated.getD2yGrav(), 0.0f);
        assertEquals("Unexpected value for occupantID found in specified region.", 5, regionCreated.getOccupantID());
    }

    @Test
    public void testDefineRegionValidInputMinXY() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(0, 0);
        plane.defineRegion(position, new Region2D(9.0f, 12.0f, 5));
        Region2D regionCreated = plane.accessRegion(position);
        assertEquals("Unexpected value for d2xGrav found in specified region.", 9.0f, regionCreated.getD2xGrav(), 0.0f);
        assertEquals("Unexpected value for d2yGrav found in specified region.", 12.0f, regionCreated.getD2yGrav(), 0.0f);
        assertEquals("Unexpected value for occupantID found in specified region.", 5, regionCreated.getOccupantID());
    }

    @Test
    public void testDefineRegionValidInputMaxXY() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(4, 4);
        plane.defineRegion(position, new Region2D(9.0f, 12.0f, 5));
        Region2D regionCreated = plane.accessRegion(position);
        assertEquals("Unexpected value for d2xGrav found in specified region.", 9.0f, regionCreated.getD2xGrav(), 0.0f);
        assertEquals("Unexpected value for d2yGrav found in specified region.", 12.0f, regionCreated.getD2yGrav(), 0.0f);
        assertEquals("Unexpected value for occupantID found in specified region.", 5, regionCreated.getOccupantID());
    }

    @Test
    public void testDefineRegionValidInputMinXMaxY() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(0, 4);
        plane.defineRegion(position, new Region2D(9.0f, 12.0f, 5));
        Region2D regionCreated = plane.accessRegion(position);
        assertEquals("Unexpected value for d2xGrav found in specified region.", 9.0f, regionCreated.getD2xGrav(), 0.0f);
        assertEquals("Unexpected value for d2yGrav found in specified region.", 12.0f, regionCreated.getD2yGrav(), 0.0f);
        assertEquals("Unexpected value for occupantID found in specified region.", 5, regionCreated.getOccupantID());
    }

    @Test
    public void testDefineRegionValidInputMaxXMinY() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(4, 0);
        plane.defineRegion(position, new Region2D(9.0f, 12.0f, 5));
        Region2D regionCreated = plane.accessRegion(position);
        assertEquals("Unexpected value for d2xGrav found in specified region.", 9.0f, regionCreated.getD2xGrav(), 0.0f);
        assertEquals("Unexpected value for d2yGrav found in specified region.", 12.0f, regionCreated.getD2yGrav(), 0.0f);
        assertEquals("Unexpected value for occupantID found in specified region.", 5, regionCreated.getOccupantID());
    }

    @Test
    public void testDefineRegionInvalidInputOOBx() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(9, 3);
        plane.defineRegion(position, new Region2D(8.0f, 11.0f, 4));
        Region2D regionCreated = plane.accessRegion(position);
        assertEquals("The function should return null for x out of bounds.", null, regionCreated);
    }

    @Test
    public void testDefineRegionInvalidInputOOBy() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(3, 9);
        plane.defineRegion(position, new Region2D(8.0f, 11.0f, 4));
        Region2D regionCreated = plane.accessRegion(position);
        assertEquals("The function should return null for y out of bounds.", null, regionCreated);
    }

    @Test
    public void testDefineRegionInvalidInputOOBxy() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(9, 9);
        plane.defineRegion(position, new Region2D(8.0f, 11.0f, 4));
        Region2D regionCreated = plane.accessRegion(position);
        assertEquals("The function should return null for x and y out of bounds.", null, regionCreated);
    }

    @Test
    public void testAccessRegion() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(2, 3);
        plane.defineRegion(position, new Region2D(9.0f, 12.0f, 5));
        Region2D regionCreated = plane.accessRegion(position);
        assertEquals("Unexpected value for d2xGrav found in specified region.", 9.0f, regionCreated.getD2xGrav(), 0.0f);
        assertEquals("Unexpected value for d2yGrav found in specified region.", 12.0f, regionCreated.getD2yGrav(), 0.0f);
        assertEquals("Unexpected value for occupantID found in specified region.", 5, regionCreated.getOccupantID());
    }

    @Test
    public void testAccessRegionUninitialized() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(4, 1);

    }

    @Test
    public void testSetRegionEmpty() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(2, 3);
        plane.defineRegion(position, new Region2D(9.0f, 12.0f, 5));
        plane.setRegionEmpty(position);
        Region2D regionCreated = plane.accessRegion(position);
        assertEquals("Value for occupantID found in specified region should be -1.", -1, regionCreated.getOccupantID());
    }

    @Test
    public void testSetRegionOccupied() throws Exception {
        Plane2D plane = new Plane2D(5, 5);
        Point position = new Point(1, 1);
        plane.setRegionOccupied(position, 8);
        assertEquals("The id at the specified region should be 8.", 8, plane.accessRegion(position).getOccupantID());
    }
}
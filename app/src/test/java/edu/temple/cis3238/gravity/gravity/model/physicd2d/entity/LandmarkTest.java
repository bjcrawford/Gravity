package edu.temple.cis3238.gravity.gravity.model.physicd2d.entity;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.model.Point;

import static org.junit.Assert.*;

public class LandmarkTest {

    private List<List<Point>> shapes;

    @Before
    public void setUp() throws Exception {
        this.shapes = new ArrayList<>();

        ArrayList<Point> tmpShape= new ArrayList<Point>();
        tmpShape.add(new Point(0, 0));
        tmpShape.add(new Point(0, 1));
        tmpShape.add(new Point(0, -1));
        tmpShape.add(new Point(-1, 0));
        tmpShape.add(new Point(1, 0));
        this.shapes.add(tmpShape);
        tmpShape= new ArrayList<Point>();
        tmpShape.add(new Point(0, 0));
        tmpShape.add(new Point(1, 1));
        tmpShape.add(new Point(1, -1));
        tmpShape.add(new Point(-1, 1));
        tmpShape.add(new Point(1, -1));
        this.shapes.add(tmpShape);
    }

    @After
    public void tearDown() throws Exception {
        this.shapes = null;
    }

    @Test
    public void jsonConstructor() throws Exception {
        String jSelfString = "{\"shapes\":[[{\"x\":0,\"y\":0},{\"x\":0,\"y\":1},{\"x\":0,\"y\":-1},{\"x\":-1,\"y\":0},{\"x\":1,\"y\":0}],[{\"x\":0,\"y\":0},{\"x\":1,\"y\":1},{\"x\":1,\"y\":-1},{\"x\":-1,\"y\":1},{\"x\":1,\"y\":-1}]], \"dTheta\":20,\"mass\":1000,\"lifespan\":300,\"x0\":5,\"y0\":10,\"id\":1}";
        JSONObject jBody = new JSONObject(jSelfString);
        Landmark l = new Landmark(jBody);
        assertEquals("Body x should be 5.", 5, l.getPosition().x);
        assertEquals("Body y should be 10.", 10, l.getPosition().y);
        assertEquals("Body lifespan should be 300.", 300, l.getLifespan());
        assertEquals("Body id should be 0.", 1, l.getId());
        assertEquals("Body mass should be 1000.", 1000, l.getMass());
        assertEquals("Body shapes should have been initialized.", 1, l.shapes.get(1).get(1).x);
        assertEquals("Body shapes should have been initialized.", 1, l.shapes.get(1).get(1).y);
    }

    @Test
    public void testToJSON() throws Exception {

        String jSelfString = "{\"shapes\":[[{\"x\":0,\"y\":0},{\"x\":0,\"y\":1},{\"x\":0,\"y\":-1},{\"x\":-1,\"y\":0},{\"x\":1,\"y\":0}],[{\"x\":0,\"y\":0},{\"x\":1,\"y\":1},{\"x\":1,\"y\":-1},{\"x\":-1,\"y\":1},{\"x\":1,\"y\":-1}]],\"lifespan\":300,\"y0\":10,\"mass\":0,\"x0\":5,\"id\":1,\"dTheta\":20}";

        Landmark l = new Landmark(1, new Point(5, 10), 20, 300, this.shapes);
        JSONObject selfAsJSON = l.toJSON();
        assertEquals("Body toJSONshould return an accurate json representation of the body",
                jSelfString, selfAsJSON.toString());
    }
}
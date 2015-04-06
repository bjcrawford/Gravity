package edu.temple.cis3238.gravity.gravity.model.physicd2d.entity;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.model.Point;

import static org.junit.Assert.*;

public class PhenomenonTest {

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
        String jSelfString = "{\"shapes\":[[{\"x\":0,\"y\":0},{\"x\":0,\"y\":1},{\"x\":0,\"y\":-1},{\"x\":-1,\"y\":0},{\"x\":1,\"y\":0}],[{\"x\":0,\"y\":0},{\"x\":1,\"y\":1},{\"x\":1,\"y\":-1},{\"x\":-1,\"y\":1},{\"x\":1,\"y\":-1}]],\"lifespan\":300,\"x0\":5,\"y0\":10,\"dy0\":15,\"id\":0,\"dx0\":10}";
        JSONObject jPhenomenon = new JSONObject(jSelfString);
        Phenomenon p = new Phenomenon(jPhenomenon);
        assertEquals("Phenomenon x should be 5.", 5, p.getPosition().x);
        assertEquals("Phenomenon y should be 10.", 10, p.getPosition().y);
        assertEquals("Phenomenon lifespan should be 300.", 300, p.getLifespan());
        assertEquals("Phenomenon dx0 should be 10.", 10, p.dx);
        assertEquals("Phenomenon dy0 should be 15.", 15, p.dy);
        assertEquals("Phenomenon id should be 0.", 0, p.getId());
        assertEquals("Phenomenon shapes should have been initialized.", 1, p.shapes.get(1).get(1).x);
        assertEquals("Phenomenon shapes should have been initialized.", 1, p.shapes.get(1).get(1).y);
    }

    @Test
    public void testUpdate() throws Exception {
        Phenomenon p = new Phenomenon(0, new Point(5, 5), 10, 10, -1, this.shapes);
        p.update(0.1f);
        Point pos = p.getPosition();
        assertEquals("Update should displace body to <6, 6>. Actual x:", 6, pos.x);
        assertEquals("Update should displace body to <6, 6>. Actual y:", 6, pos.y);
    }

    @Test
    public void testApplyAcceleratingForce1() throws Exception {

        int x0 = 5, y0 = 5, dx0 = 0, dy0 = 0;
        float deltaD2x = 10.0f, deltaD2y = 5.0f;
        float deltaT = 10.0f;

        Phenomenon p = new Phenomenon(0, new Point(x0, y0), dx0, dy0, -1, this.shapes);
        p.applyAcceleratingForce(deltaD2x, deltaD2y);

        int dx1 = (int) (dx0 + (deltaD2x * deltaT));
        int dy1 = (int) (dy0 + (deltaD2y * deltaT));

        int expectedX =(int) (x0 + (dx1 * deltaT) + (0.5 * 10 * deltaT * deltaT));
        int expectedY =(int) (y0 + (dy1 * deltaT) + (0.5 * 5 * deltaT * deltaT));

        p.update(deltaT);
        assertEquals("Checking effect of acceleration on displacement. dx:", expectedX, p.getPosition().x);
        assertEquals("Checking effect of acceleration on displacement. dy:", expectedY, p.getPosition().y);
    }

    @Test
    public void testToJSON() throws Exception {

        String jSelfString = "{\"shapes\":[[{\"x\":0,\"y\":0},{\"x\":0,\"y\":1},{\"x\":0,\"y\":-1},{\"x\":-1,\"y\":0},{\"x\":1,\"y\":0}],[{\"x\":0,\"y\":0},{\"x\":1,\"y\":1},{\"x\":1,\"y\":-1},{\"x\":-1,\"y\":1},{\"x\":1,\"y\":-1}]],\"lifespan\":300,\"y0\":10,\"x0\":5,\"dy0\":15,\"id\":0,\"dx0\":10}";

        Phenomenon p = new Phenomenon(0, new Point(5, 10), 10, 15, 300, this.shapes);
        p.applyAcceleratingForce(20, 30);
        JSONObject selfAsJSON = p.toJSON();
        assertEquals("Body toJSONshould return an accurate json representation of the body",
                jSelfString, selfAsJSON.toString());
    }

    @Test
    public void testRotation() throws Exception {
        Phenomenon p = new Phenomenon(0, new Point(0, 0), 0, 0, -1, this.shapes);
        assertEquals("Initial shape of body should index 0.", 0, p.getShape().get(1).x);
        p.applyAcceleratingForce(-1, -1);
        p.update(1.0f);
        assertEquals("Updated shape of body should index 1.", 1, p.getShape().get(1).x);
    }
}
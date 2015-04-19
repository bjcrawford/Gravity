package edu.temple.cis3238.gravity.gravity.model.graphics2d.graphicsentity;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/25/2015
 */
public class EntityTest extends TestCase {

    @Before
    public void setUp() {

    }

    @Test
    public void testTest() {
        assertTrue(1 + 1 == 2);
    }

    @Test
    public void testEntity() {
        try {
            GraphicsEntity graphicsEntity = new GraphicsEntity(new JSONObject("{" +
                    "\"id\": \"5\"," +
                    "\"name\": \"Player\"," +
                    "\"img_res_id0\": \"R.drawable.player_0\"," +
                    "\"img_res_id1\": \"R.drawable.player_1\"," +
                    "\"img_res_id2\": \"R.drawable.player_2\"," +
                    "\"img_res_id3\": \"R.drawable.player_3\"," +
                    "\"img_res_id4\": \"R.drawable.player_4\"," +
                    "\"img_res_id5\": \"R.drawable.player_5\"," +
                    "\"img_res_id6\": \"R.drawable.player_6\"," +
                    "\"img_res_id7\": \"R.drawable.player_7\"," +
                    "\"img_res_id8\": \"R.drawable.player_8\"," +
                    "\"img_res_id9\": \"R.drawable.player_9\"," +
                    "\"img_res_id10\": \"R.drawable.player_10\"," +
                    "\"img_res_id11\": \"R.drawable.player_11\"" +
                    "}"));
            assertEquals(5, graphicsEntity.getId());
            assertEquals("Player", graphicsEntity.getName());
            assertEquals("R.drawable.player_0", graphicsEntity.getImgResId(0));
            assertEquals("R.drawable.player_1", graphicsEntity.getImgResId(1));
            assertEquals("R.drawable.player_2", graphicsEntity.getImgResId(2));
            assertEquals("R.drawable.player_3", graphicsEntity.getImgResId(3));
            assertEquals("R.drawable.player_4", graphicsEntity.getImgResId(4));
            assertEquals("R.drawable.player_5", graphicsEntity.getImgResId(5));
            assertEquals("R.drawable.player_6", graphicsEntity.getImgResId(6));
            assertEquals("R.drawable.player_7", graphicsEntity.getImgResId(7));
            assertEquals("R.drawable.player_8", graphicsEntity.getImgResId(8));
            assertEquals("R.drawable.player_9", graphicsEntity.getImgResId(9));
            assertEquals("R.drawable.player_10", graphicsEntity.getImgResId(10));
            assertEquals("R.drawable.player_11", graphicsEntity.getImgResId(11));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {

    }
}

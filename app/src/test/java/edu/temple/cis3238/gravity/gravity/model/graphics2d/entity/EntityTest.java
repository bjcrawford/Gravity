package edu.temple.cis3238.gravity.gravity.model.graphics2d.entity;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntityTest extends TestCase {

    @Before
    public void setUp() {
    }

    @Test
    public void testEntity() {
        try {
            Entity entity = new Entity(new JSONObject("{" +
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
            assertEquals(5, entity.getId());
            assertEquals("Player", entity.getName());
            assertEquals("R.drawable.player_0", entity.getImgResId(0));
            assertEquals("R.drawable.player_1", entity.getImgResId(1));
            assertEquals("R.drawable.player_2", entity.getImgResId(2));
            assertEquals("R.drawable.player_3", entity.getImgResId(3));
            assertEquals("R.drawable.player_4", entity.getImgResId(4));
            assertEquals("R.drawable.player_5", entity.getImgResId(5));
            assertEquals("R.drawable.player_6", entity.getImgResId(6));
            assertEquals("R.drawable.player_7", entity.getImgResId(7));
            assertEquals("R.drawable.player_8", entity.getImgResId(8));
            assertEquals("R.drawable.player_9", entity.getImgResId(9));
            assertEquals("R.drawable.player_10", entity.getImgResId(10));
            assertEquals("R.drawable.player_11", entity.getImgResId(11));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {

    }
}

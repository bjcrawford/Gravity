package models.physicd2d;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
public class Physics2D {

// Fields ------------------------------------------------------------------------------------------

    /**An array of Region2D objects used to represent a rectangular physics environment.*/
    private Region2D[] world;

    /**Used to track the current number of entities (i.e bodies and phenomena), and to assign unique ids to new entities.*/
    private int idCtr;

    /**A collection of bodies currently active in the world*/
    private List<Body> bodies;

    /**A collection of landmarks currently active in the world*/
    private List<Landmark> landmarks;

    /**A collection of phenomena currently active in the world*/
    private List<Phenomenon> phenomena;

// Constructors ------------------------------------------------------------------------------------

    public Physics2D() {
        this.idCtr = 0;
        this.bodies = new ArrayList<Body>();
        this.phenomena = new ArrayList<Phenomenon>();
    }

// General Public Functions ------------------------------------------------------------------------

    public void createBody() {

        this.idCtr ++;
    }

    public void createPhenomenon() {


    }

    public void update(float deltaT) {

    }

// General Private Functions -----------------------------------------------------------------------

// Getters and Setters -----------------------------------------------------------------------------

}

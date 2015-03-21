package models.physicd2d;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
public class Region2D {

// Fields ------------------------------------------------------------------------------------------

    /**The acceleration due to gravity in the x direction associated with this region*/
    private float d2xGrav;

    /**The acceleration due to gravity in the y direction associated with this region*/
    private float d2yGrav;

    //**If the region is occupied: The unique id of the regions occupant <br>Else: -1 to signify that the region is empty*/
    private int occupantID;

// Constructors ------------------------------------------------------------------------------------

    /**
     * Initializes the local fields of the new Region2D to:<br>
     * acceleration due to gravity in the x direction = 0<br>
     * acceleration due to gravity in the y direction = 0<br>
     * the id of the regions current occupant = -1
     */
    public Region2D() {
        this.d2xGrav = 0;
        this.d2yGrav = 0;
        this.occupantID = -1;
    }

    /**
     * Initializes the local fields of the new Region2D.
     * @param d2xGrav Defines the initial value of acceleration due to gravity in the x direction.
     * @param d2yGrav Defines the initial value of acceleration due to gravity in the y direction.
     */
    public Region2D(float d2xGrav, float d2yGrav) {
        this.d2yGrav = d2yGrav;
        this.d2yGrav = d2yGrav;
    }

    /**
     * Initializes the local fields of the new Region2D.
     * @param occupantID The unique id of the initial occupant of the region.
     */
    public Region2D(int occupantID){
        this.occupantID = occupantID;
    }

    /**
     * Initializes the local fields of the new Region2D.
     * @param d2xGrav Defines the initial value of acceleration due to gravity in the x direction.
     * @param d2yGrav Defines the initial value of acceleration due to gravity in the y direction.
     * @param occupantID The unique id of the initial occupant of the region.
     */
    public Region2D(float d2xGrav, float d2yGrav, int occupantID) {
        this.d2yGrav = d2yGrav;
        this.d2yGrav = d2yGrav;
        this.occupantID = occupantID;
    }

// General Public Functions ------------------------------------------------------------------------

// General Private Functions -----------------------------------------------------------------------

// Getters and Setters -----------------------------------------------------------------------------

    /**
     *
     * @return The value of acceleration due to gravity in the x direction.
     */
    public float getD2xGrav() {
        return d2xGrav;
    }

    /**
     *
     * @param d2xGrav The value of acceleration due to gravity in the x direction.
     */
    public void setD2xGrav(float d2xGrav) {
        this.d2xGrav = d2xGrav;
    }

    /**
     *
     * @return The value of acceleration due to gravity in the y direction.
     */
    public float getD2yGrav() {
        return d2yGrav;
    }

    /**
     *
     * @param d2yGrav The value of acceleration due to gravity in the y direction.
     */
    public void setD2yGrav(float d2yGrav) {
        this.d2yGrav = d2yGrav;
    }

    /**
     *
     * @return The unique id of the current occupant of the region.
     */
    public int getOccupantID() {
        return occupantID;
    }

    /**
     *
     * @param occupantID The unique id of the current occupant of the region.
     */
    public void setOccupantID(int occupantID) {
        this.occupantID = occupantID;
    }
}

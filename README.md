Gravity
=======

![](https://raw.githubusercontent.com/bjcrawford/Gravity/master/ghimages/GravityLogo.png)

Gravity is an open-source space themed adventure/puzzle game for the Android platform.The player 
must attempt to navigate a ship through a level while dealing with the force of gravity from 
surrounding obstacles.

### Content Structure & Level Format

![](https://raw.githubusercontent.com/bjcrawford/Gravity/master/ghimages/JSONContent.png)

* Content is divided into content packs, a JSON file which contain some versioning and release 
information, and a collection of one or more stories.

* A story is a collection of levels, which share common structural and stylistic attributes.

* Each level contains metadata describing the level structure and associated graphics resources.

### Gravity Map

![](https://raw.githubusercontent.com/bjcrawford/Gravity/master/ghimages/GravityMap.png)

* The gravity map of the each level exists as a discrete field of acceleration vectors.

* Each region contains an acceleration vector.

* A pair of <x, y> coordinates is hashed to the acceleration vector associated with the containing 
region. i.e    #(x, y) = a

* By defining these regions on initialization, we reduce the time needed to find the acceleration 
acting on a body.

### Collision Detection

![](https://raw.githubusercontent.com/bjcrawford/Gravity/master/ghimages/CollisionDetection.png)

* The collision detection logic exploits the structure of the gravity map to efficiently check for 
collisions.

* In addition to an acceleration vector, each region contains an occupant ID (with ID’s less than 
zero indicating an empty region).

* During translation, each subsection of the entity’s shape is checked for conflicts.

### Rendering

![](https://raw.githubusercontent.com/bjcrawford/Gravity/master/ghimages/Rendering.png)

* Composed of multiple draw operations.

* The background can have a variable depth of field.

* In every draw operation:
  * The background is drawn
  * Different entities are drawn (ships, planets, ...)
  * A navigation map is drawn
  * A timer is drawn

### App Flow

![](https://raw.githubusercontent.com/bjcrawford/Gravity/master/ghimages/AppFlow.png)

* Activities are the main components of an Android application. They are a combination of a 
UI and logic. Each represents a self-contained screen.

* Fragments are modular, reusable UI portions that can be placed inside and managed by activities.

### Game Structure

![](https://raw.githubusercontent.com/bjcrawford/Gravity/master/ghimages/MVCDiagram.png)

* Uses the MVC design pattern.

* The controller runs the main game logic loop.

* The model manages the game state.

* The view handles drawing to the screen

### Java Documentation

You can view the java docs at [http://bcrawford.github.io/Gravity](http://bcrawford.github.io/Gravity).
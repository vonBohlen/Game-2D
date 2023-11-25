package old.entities.entity;

public class Entity {

    /*//region GameLogic
    public Rectangle box;
    private final Deque<Entity> entityCollisions = new ArrayDeque<>();
    private final Deque<Object> objectCollisions = new ArrayDeque<>();
    //endregion

    //region Handlers
    public RenderManager renderManager;
    public KeyHandler keyHandler;
    public ActionManager actionManager;
    public Instance instance;
    //endregion

    /**
     *
     * @param pX the x position of the entity
     * @param pY the y position of the entity
     * @param pHeight the height of the entity
     * @param pWidth the width of the entity
     * @param i the game instance
     */
    /*public Entity(int pX, int pY, int pHeight, int pWidth, Instance i){

        //region Handlers
        this.renderManager = i.getRenderManager();
        this.actionManager = i.getActionManager();
        this.keyHandler = i.getKeyHandler();

        this.instance = i;

        actionManager.registerEntity(this);
        //endregion

        //region HitBoxCreation
        this.box = new Rectangle(pX, pY, pWidth, pHeight);
        //endregion
    }

    public Entity(RenderManager pRender, ActionManagerOld pAction) {

        this.renderManager = pRender;
        this.actionManager = pAction;

        //actionManager.registerEntity(this);

    }

    /**
     * Moves the entity from its current position for the provided x and y shift values.
     * @param xShift shift on the x-axis
     * @param yShift shift on the y-axis
     * @return true if the move was successful, else false
     */
    /*public boolean move(int xShift, int yShift) {
        Rectangle shift = new Rectangle(this.box.x, this.box.y, this.box.width, this.box.height);
        Entity entityCache;
        Object objectCache;

        while (xShift != 0 || yShift != 0) {
            int newX = box.x;
            int newY = box.y;

            if (xShift < 0) {
                newX = box.x - 1;
                xShift++;
            }
            else if (xShift > 0){
                newX = box.x + 1;
                xShift--;
            }

            if (yShift < 0) {
                newY = box.y - 1;
                yShift++;
            }
            else if (yShift > 0){
                newY = box.y + 1;
                yShift--;
            }

            shift.x = newX;
            shift.y = newY;

            entityCache = actionManager.checkCollisionForEntity(this, shift);
            objectCache = actionManager.checkCollisionForObject(shift);

            if (entityCache != null) {
                entityCollisions.push(entityCache);
                return false;
            }

            if (objectCache != null) {
                objectCollisions.push(objectCache);
                return false;
            }

            this.box.x = newX;
            this.box.y = newY;
        }
        return true;
    }

    /**
     *Changes the position of the entity, by setting new x and y values.
     * @param newX new x-position for the entity
     * @param newY new y-position fot the entity
     * @return true if the position change successful, else false
     */
    /*public boolean setPosition(int newX, int newY) {
        Rectangle newPosition = new Rectangle(newX, newY, this.box.width, this.box.height);
        Entity entityCache = actionManager.checkCollisionForEntity(this, newPosition);
        if (entityCache != null) {
            entityCollisions.push(entityCache);
            return false;
        }

        Object objectCache = actionManager.checkCollisionForObject(newPosition);
        if (objectCache != null) {
            objectCollisions.push(objectCache);
            return false;
        }

        this.box.x = newX;
        this.box.y = newY;
        return true;
    }

    /**
     *Changes the size of the entity, by setting new width height values.
     * @param newWidth new width value for the entity
     * @param newHeight new height value for the entity
     * @return true if the resize was successful, else false
     */
    /*public boolean changeEntitySize(int newWidth, int newHeight) {
        Rectangle newSize = new Rectangle(this.box.x, this.box.y, newWidth, newHeight);
        Entity entityCache = actionManager.checkCollisionForEntity(this, newSize);
        if (entityCache != null) {
            entityCollisions.push(entityCache);
            return false;
        }

        Object objectCache = actionManager.checkCollisionForObject(newSize);
        if (objectCache != null) {
            objectCollisions.push(objectCache);
            return false;
        }

        this.box.width = newWidth;
        this.box.height = newHeight;
        return true;
    }

    /**
     * @return the last entity, the entity collided with
     */
    /*public Entity getEntityCollision() {
        return entityCollisions.pop();
    }

    /**
     * @return the last object, the entity collided with
     */
    /*public Object getObjectCollision() {
        return objectCollisions.pop();
    }

    public void update() {}
    public void draw(Graphics2D g2) {}*/
}

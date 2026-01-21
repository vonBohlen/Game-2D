# Getting started

1. **[Installation](#installation)**
2. **[Creating an instance](#creating-an-instance)**
3. **[Starting the game loop](#starting-the-game-loop)**
4. **[Adding an object](#adding-an-object)**
5. **[Using an object](#using-an-object)**
6. **[Additional documentation](#additional-documentation)**

## Installation

Currently, there are no official packages of Game2D. \
To use Game2D, you either have to compile the project by yourself, or you have to clone this repository and base your work on to it. \
In the future, Game2D will be available as a maven package.

## Creating an instance

To use Game2D, we need to create an instance of the engine first. \
This can be done, by adding this line of code to your project:

```java
public static Instance instance = new Instance(null);
```

## Starting the game loop

Next we need to start the engine, which can simply be done by adding:

```java
instance.start("Title-of-your-project");
```

## Adding an object

Until now, we have an empty instance of the engine, which is pretty boring.

So let's add some excitement by creating an object. \
To create a game object, create a new class which extends one of the game object classes of Game2D:

```java
public class Cube extends Entity {
    
    public Cube() {
        super(hitBox, true, 0);
    }

    @Override
    public void update(){}
    
}
```

To actually use this game object, we need to set its hitbox. \
Game2D uses the Rectangle class of Java awt as hitboxes, therefore we can simply add:

```java
Rectangle hitbox = new Rectangle(0, 0, 40, 40); // x, y, width, height
```

## Using an object

This function of the game object will be called by the engine, according to the tps (_ticks per second_):

```java
@Override
public void update(){}
```

Therefore, we can use it to manipulate our object.

For example to move the object around the center of the screen:

```java
int centerX = DataHand.renderMan.getWidth() / 2;
int centerY = DataHand.renderMan.getHeight() / 2;

int count = 0;
int segment = Integer.parseInt(ConfProvider.getConf(DataHand.confPath).getProperty("game2d.core.tps")) / 4;

@Override
public void update(){
    if (ActionMan.getGameTick() % segment != 0) return;
    if (count > 3) count = 0;
    switch (count) {
        case 0 -> setPosition(centerX, centerY - 100);
        case 1 -> setPosition(centerX + 100, centerY);
        case 2 -> setPosition(centerX, centerY + 100);
        case 3 -> setPosition(centerX - 100, centerY);
    }
    count++;
}
```

## Additional documentation

**The full documentation of Game2D can be found in [/doc](../doc).**  \
In the future there will also be a wiki page dedicated to Game2D.

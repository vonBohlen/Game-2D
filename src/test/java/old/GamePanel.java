package old;

public class GamePanel {
/*
    // SCREEN SETTINGS
    final int originalTitleSize = 16; //16x16 title
    final int scale = 3;

    public final int titleSize = originalTitleSize * scale; // 48x48 title
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    public final int screenWidth = titleSize * maxScreenCol; // 768 pixels
    public final int screenHeight = titleSize * maxScreenRow; // 576 pixels
    public ActionManager actionManager = Main.getActionManager();

    final int fps = 120;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, actionManager, keyHandler);
    Square square = new Square(this, actionManager);
    DebugDisplay debugDisplay = new DebugDisplay();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                debugDisplay.updateFPS(drawCount);
                drawCount = 0;
                timer = 0;
            }
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        for (Entity ce: actionManager.getEntities()) {
            ce.draw(g2);
        }

        debugDisplay.draw(g2);

        g2.dispose();
    }*/

}

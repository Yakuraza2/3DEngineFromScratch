package src.fr.rhumain.worldcraft;

/**
 * Décrivez votre classe src.fr.rhumain.worldcraft.Game ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Game implements Runnable
{
    private GraphicMotor graphicMotor;
    private boolean running;
    private final Thread thread;
    
    private Player player;
    private Block[][][] chunk;

    public static void main(String[] args){ new Game();}
    
    /**
     * Constructeur d'objets de classe src.fr.rhumain.worldcraft.Game
     */
    public Game()
    {
        Models.init();
        chunk = new Block[16][16][16];
        this.player = new Player(8, 10, 6.5);
        this.thread = new Thread(this);
        graphicMotor = new GraphicMotor(player, this);

        if(true) {
            for (int x = 0; x < 16; x++)
                for (int z = 0; z < 16; z++) {
                    this.chunk[x][8][z] = new Block(x, 8, x);
                }

            this.chunk[8][7][8] = new Block(8, 7, 8);
        }

        //this.chunk[8][6][8] = new Block(10, 9, 8);
        this.chunk[7][7][8] = new Block(10,10,8);

        this.start();
    }
    
    public Block getBlockAt(double x, double y, double z){
        if(x >= 16 || y >= 16 || z >= 16) return null;
        if(x < 0 || y < 0 || z < 0) return null;
        return chunk[(int)(Math.floor(x))][(int)(Math.floor(y))][(int)(Math.floor(z))];
    }
    
     private void start() {
        running = true;
        thread.start();
    }
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;//60 times per second
        double delta = 0;
        graphicMotor.requestFocus();
        while(running) {
            
            System.out.println("starting rendering...");
            long now = System.nanoTime();
            delta += ((now-lastTime) / ns);
            lastTime = now;
            while (delta >= 1)//Make sure update is only happening 60 times a second
            {
                //handles all the logic restricted time
                graphicMotor.update(player, chunk);
                delta--;
            }
            graphicMotor.render();//displays to the screen unrestricted time
        }
    }
}

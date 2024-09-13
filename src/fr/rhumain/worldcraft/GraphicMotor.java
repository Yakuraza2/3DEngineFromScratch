package src.fr.rhumain.worldcraft;

import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;
import java.util.*;

/**
 * Décrivez votre classe src.fr.rhumain.worldcraft.GraphicMotor ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class GraphicMotor
{


    private static final Color SKY_COLOR = new Color(30, 30, 200);

    private final Pixel[][] pixels = new Pixel[1200][800];
    public static final double PITCH_MAX = Math.toRadians(120.0);
    public static final double YAW_MAX = Math.toRadians(90.0);
    public static final int SHOW_DISTANCE = 16;
    
    private final GFrame frame;
    private Game game;
    
    private final BufferedImage screen;

    public GraphicMotor(Player player, Game game){
        this.createPixels();
        this.frame = new GFrame(player);
        this.screen = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
        //this.panel = new src.fr.rhumain.worldcraft.GPanel();
        //this.frame.add(this.panel);
        
        this.game = game;
        this.frame.setVisible(true);
    }

    private void createPixels(){
        for (int x=0; x<pixels.length; x++){
            for (int y=0; y<pixels[x].length; y++){
                pixels[x][y] =  new Pixel();
            }
        }
    }
    
    public void requestFocus(){ this.frame.requestFocus(); }
    
    public void update(Player player, Block[][][] blocks){

        System.out.println("updating...");

        //Replacing all distances by the view distance of the player to update the render.
        for (Pixel[] pixel : pixels) {
            for (Pixel value : pixel) {
                value.reset();
            }
        }

        for(int x=0; x< blocks.length; x++){
            for(int y=0; y<blocks[x].length; y++){
                for(int z=0; z<blocks[y].length; z++){
                    if(blocks[x][y][z] == null) continue;
                    if(blocks[x][y][z].getLocation().getDistanceFrom(player.getLocation()) > SHOW_DISTANCE) continue;
                    raster(blocks[x][y][z], player);
                }
            }
        }

        //this.putSkyColor();

        this.render();
    }

    private void raster(Block block, Player player){
        for(int id=0; id<block.getModel().getTriangles().size(); id++){
            Vector normal = block.getModel().getNormal(id);
            System.out.println(STR."Rasting \{id} triangle");

            //Cette condition est trop sévère et retirera des triangles normalement visibles
            //if(normal.scalaire(player.getNormal()) > 0) continue;
            System.out.println("Il faut afficher ce triangle");

            Double[][] triangle = block.getModel().getTriangles().get(id);

            double xP= player.getLocation().getX();
            double yP= player.getLocation().getY();
            double zP= player.getLocation().getZ();

            int[][] points = new int[3][];
            double[] distances = new double[3];

            for(int i=0; i<3; i++){
                double x = block.getLocation().getX() + triangle[i][0];
                double y = block.getLocation().getY() + triangle[i][1];
                double z = block.getLocation().getZ() + triangle[i][2];

                double d = getDistance(x, y ,z);
                //double p = getRadiantAngleXZ(x, z) + Math.toRadians(player.getLocation().getPitch());
                //double o = getRadiantAngleY(y, d) + Math.toRadians(player.getLocation().getYaw());

                //points[i] = getPixels(d, p, o);
                points[i] = getPixels(x, y ,z, player);
                distances[i] = d;

                if (points[i] != null) {
                    drawPoint(points[i][0], points[i][1], Color.RED);  // Affiche chaque sommet en rouge
                }
            }

            drawFragment(points[0], points[1], points[2], distances[0], distances[1], distances[2]);
            /*drawPoint(points[0][0], points[0][1], Color.RED);
            drawPoint(points[1][0], points[1][1], Color.GREEN);
            drawPoint(points[2][0], points[2][1], Color.BLUE);*/
        }


    }

    private double getRadiantAngleY(double y, double d){
        //return Math.acos(y/d);
        return Math.asin(y/d);
    }

    private double getRadiantAngleXZ(double x, double z){
        return Math.atan2(z, x);
    }

    private double getDistance(double x, double y, double z){
        return Math.sqrt(x*x + y*y + z*z);
    }

    private int[] getPixels(double distance, double angleP, double angleO){
        //int x = (int) (distance*Math.sin(angleP - PITCH_MAX/2)*screen.getWidth()/2 + screen.getWidth()/2);
        //int y = (int) (distance*Math.sin(angleO - YAW_MAX/2)*screen.getHeight()/2 + screen.getHeight()/2);
        int x = (int) (Math.sin(angleP)*distance + screen.getWidth()/2);
        int y = (int) (Math.sin(angleO)*distance + screen.getHeight()/2);
        return new int[]{x, y};
    }

    private int[] getPixels(double x, double y, double z, Player player) {
        // Coordonnées relatives au joueur
        double xRel = x - player.getLocation().getX();
        double yRel = y - player.getLocation().getY();
        double zRel = z - player.getLocation().getZ();

        // Rotation selon yaw (horizontal)
        double cosYaw = Math.cos(player.getLocation().getYaw());
        double sinYaw = Math.sin(player.getLocation().getYaw());
        double xRot = xRel * cosYaw - zRel * sinYaw;
        double zRot = xRel * sinYaw + zRel * cosYaw;

        // Rotation selon pitch (vertical)
        double cosPitch = Math.cos(player.getLocation().getPitch());
        double sinPitch = Math.sin(player.getLocation().getPitch());
        double yRot = yRel * cosPitch - zRot * sinPitch;
        zRot = yRel * sinPitch + zRot * cosPitch;

        // Projection en 2D (perspective)
        //if (zRot <= 0) return null; // Si derrière la caméra, on ne dessine pas
        double fovFactor = 500; // Facteur de zoom
        int screenX = (int) ((fovFactor * xRot) / zRot + screen.getWidth() / 2);
        int screenY = (int) ((fovFactor * yRot) / zRot + screen.getHeight() / 2);

        return new int[]{screenX, screenY};
    }

    /*private void drawFragment(int[] p1, int[] p2, int[] p3,
                              double d1, double d2, double d3){
        double maxDistance = SHOW_DISTANCE; // exemple d'échelle
        int red = (int) Math.min((d1 / maxDistance) * 255, 255);
        int green = (int) Math.min((d2 / maxDistance) * 255, 255);
        int blue = (int) Math.min((d3 / maxDistance) * 255, 255);
        Color color = new Color(red, green, blue);

        drawLine(p1, p2, d1, d2, color);
        drawLine(p2, p3, d2, d3, color);
        drawLine(p3, p1, d3, d1, color);
    }*/

    private void drawFragment(int[] p1, int[] p2, int[] p3, double d1, double d2, double d3) {
        // Détermine les limites du triangle (bounding box)
        int minX = Math.max(0, Math.min(p1[0], Math.min(p2[0], p3[0])));
        int maxX = Math.min(screen.getWidth() - 1, Math.max(p1[0], Math.max(p2[0], p3[0])));
        int minY = Math.max(0, Math.min(p1[1], Math.min(p2[1], p3[1])));
        int maxY = Math.min(screen.getHeight() - 1, Math.max(p1[1], Math.max(p2[1], p3[1])));

        drawLine(p1, p2, Color.RED);  // Ligne 1
        drawLine(p2, p3, Color.GREEN);  // Ligne 2
        drawLine(p3, p1, Color.BLUE);  // Ligne 3

        // Couleur pour le remplissage du triangle
        Color color = new Color((int) ((d1 * 40) % 255), (int) ((d1 * d2 * 10) % 255), (int) ((d3 * 20 * d1) % 255));

        // Parcours des pixels dans la bounding box
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                // Vérifie si le point (x, y) est à l'intérieur du triangle
                if (pointInTriangle(x, y, p1, p2, p3)) {
                    // Interpolation des distances pour calculer la valeur
                    double d = interpolateDistance(x, y, p1, p2, p3, d1, d2, d3);
                    tryToSet(x, y, d, color);
                }
            }
        }
    }

    private double interpolateDistance(int x, int y, int[] p1, int[] p2, int[] p3, double d1, double d2, double d3) {
        // Utilisation de barycentrique ou interpolation linéaire simple
        double denom = (p2[1] - p3[1]) * (p1[0] - p3[0]) + (p3[0] - p2[0]) * (p1[1] - p3[1]);
        double w1 = ((p2[1] - p3[1]) * (x - p3[0]) + (p3[0] - p2[0]) * (y - p3[1])) / denom;
        double w2 = ((p3[1] - p1[1]) * (x - p3[0]) + (p1[0] - p3[0]) * (y - p3[1])) / denom;
        double w3 = 1.0 - w1 - w2;

        return w1 * d1 + w2 * d2 + w3 * d3;
    }

    private void drawPoint(int x, int y, Color color) {
        // Vérifie que les coordonnées du point sont dans les limites de l'écran
        if (x >= 0 && x < pixels.length && y >= 0 && y < pixels[0].length) {
            // Définit le pixel à la couleur spécifiée
            pixels[x][y].tryToSet(0, color);
        }
    }

    private boolean pointInTriangle(int px, int py, int[] p1, int[] p2, int[] p3) {
        double denominator = ((p2[1] - p3[1]) * (p1[0] - p3[0])) + ((p3[0] - p2[0]) * (p1[1] - p3[1]));
        double a = ((p2[1] - p3[1]) * (px - p3[0])) + ((p3[0] - p2[0]) * (py - p3[1])) / denominator;
        double b = ((p3[1] - p1[1]) * (px - p3[0])) + ((p1[0] - p3[0]) * (py - p3[1])) / denominator;
        double c = 1 - a - b;

        return (a >= 0) && (b >= 0) && (c >= 0);
    }

    private double sign(int px, int py, int x1, int y1, int x2, int y2) {
        return (px - x2) * (y1 - y2) - (x1 - x2) * (py - y2);
    }

    /*private void drawLine(int[] p1, int[] p2,
                          double d1, double d2,
                          Color color){
        System.out.println(STR."\{Arrays.toString(p1)} | \{Arrays.toString(p2)}  ->  \{color.toString()}");

        if(p1 == null || p2 == null) return;

        if(p1[0] > p2[0]) {
            int[] temp = p1;
            p1 = p2;
            p2 = temp;
        }

        int dx = (p2[0]-p1[0]);
        if(dx==0) return;

        int a = (p2[1] - p1[1])/dx;
        int b = p1[1];
        //ax + b

        double m = (d2-d1)/dx;

        for(int x=p1[0]; x<p2[0]; x++){
            tryToSet(x, a*x+b, m*x+d1, color);
        }
    }*/

    private void drawLine(int[] p1, int[] p2, Color color) {
        int x1 = p1[0], y1 = p1[1];
        int x2 = p2[0], y2 = p2[1];

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            drawPoint(x1, y1, color);  // Colore le pixel

            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    private void tryToSet(int x, int y, double v, Color color) {
        if(x < 0 || x >= pixels.length || y < 0 || y >= pixels[0].length) return;

        pixels[x][y].tryToSet(v, color);
    }

    private void putSkyColor(){
        for(int x=0; x<pixels.length; x++)
            for(int y=0; y< pixels[x].length; y++){
                this.pixels[x][y].tryToSet(SHOW_DISTANCE, SKY_COLOR);
            }
    }

    /*private void tryDrawPixel(Pixel pixel){
        System.out.println(STR."Try painting a pixel (\{x} : \{y} -> \{distance})");
        if(x<0 || y<0 || x>= this.screen.getWidth() || y>= this.screen.getHeight()) return;
        if(this.pixels[x][y].getDistance() < distance) return;

        System.out.println("Painting a pixel");
        this.pixels[x][y].setDistance(distance);
        this.screen.setRGB(x, y,color.getRGB());
    }*/
    
    public void render(){

        updatePixels();

        System.out.println("rendering...");
        BufferStrategy bs = this.frame.getBufferStrategy();
        if(bs == null) {
            this.frame.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(screen, 0, 0, screen.getWidth(), screen.getHeight(), null);
        bs.show();
    }

    private void updatePixels(){
        for (int x=0; x<pixels.length; x++){
            for (int y=0; y<pixels[x].length; y++){
                screen.setRGB(x, y, pixels[x][y].getColor().getRGB());
            }
        }
    }
}

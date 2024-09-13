package src.fr.rhumain.worldcraft;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class Model {

    //Double array because 1 triangle = 3 points  && 1 point = 3 coordinates;
    List<Double[][]> triangles = new ArrayList<>();
    List<Vector> normals = new ArrayList<>();

    public Model(){

    }

    public void addTriangle(double x0, double y0, double z0,
                            double x1, double y1, double z1,
                            double x2, double y2, double z2,
                            Vector normal){

        Double[][] triangle = new Double[3][];
        triangle[0] = new Double[]{x0, y0, z0};
        triangle[1] = new Double[]{x1, y1, z1};
        triangle[2] = new Double[]{x2, y2, z2};

        this.triangles.add(triangle);
        this.normals.add(normal);
    }

    public void addQuad(double x, double y, double z,
                        double a, double b, double c,
                        Vector normal){

        // Première triangle : P1(x, y, z), P2(a, y, c), P3(x, b, z)
        this.addTriangle(x, y, z, a, y, c, x, b, z, normal);

        // Deuxième triangle : P2(a, y, c), P4(a, b, c), P3(x, b, z)
        this.addTriangle(a, y, c, a, b, c, x, b, z, normal);
    }

    public Vector getNormal(int id){ return normals.get(id); }

}

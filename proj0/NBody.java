public class NBody {
    public static double readRadius(String filename){
        /**
         * return a double corresponding to the radius of the universe in that file
         */
        In in = new In(filename);
        in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String filename){
        /**
         * return the body array in this file
         */
        In in = new In(filename);
        int n = in.readInt();
        in.readDouble();
        Body[] bs = new Body[n];

        int i = 0;
        while (i < n) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            bs[i++] = new Body(xP, yP, xV, yV, m, img);
        }
        return bs;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double uniRadius = NBody.readRadius(filename);
        Body[] planets = NBody.readBodies(filename);

        StdDraw.setScale(-uniRadius, uniRadius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");

        /**
         * draw the planets
         */

         for(Body b : planets){
             b.draw();
         }

         StdDraw.enableDoubleBuffering();
         for (double t = 0; t <= T; t += dt) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            /**
             * Calculate the net forces for every planet
             */
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            /**
             * Update positions and velocities of each planet
             */
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            /**
             * Draw the background
             */
            StdDraw.picture(0, 0, "images/starfield.jpg");
            /**
             * Draw all planets
             */
            for (Body planet : planets) {
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);

        }
        /**
         * print the final universe 
         */
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", uniRadius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                          planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
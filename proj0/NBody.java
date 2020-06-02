public class NBody {

    /**
     * return the radius of the universe in that file
     */
    public static double readRadius(String dir) {
        /* Start reading in FileName.txt */
        In in = new In(dir);

        int Number = in.readInt();
        double Radius = in.readDouble();
        return Radius;
    }

    /**
     * return an array of Planets corresponding to the planets in the file
     */
    public static Planet[] readPlanets(String dir) {
        In in = new In(dir);

        int Number = in.readInt();
        double Radius = in.readDouble();
        Planet[] Planets = new Planet[Number];

        int i = 0;
        while (i < Number) {
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            Planets[i] = new Planet(xPos, yPos, xVel, yVel, mass, img);
            i += 1;
        }
        return Planets;
    }

    /**
     * main method: Drawing the Initial Universe State
     */
    public static void main(String[] args) {
        // collect input data
        double T = Double.parseDouble(args[0]); // covert the Strings to doubles
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double uniRadius = NBody.readRadius(filename);
        Planet[] Planets = NBody.readPlanets(filename);

        /**
         * Drawing the Background
         */

        /** Sets up the universe so it goes from
         * -R, -R up to R, R */
        StdDraw.setScale(-uniRadius, uniRadius);
        /* Clears the drawing window. */
        StdDraw.clear();
        /* Stamps the copy of starfield.png */
        StdDraw.picture(0, 0, "images/starfield.jpg");

        /**
         * Draw all of the Planets
         */
        for (Planet p : Planets) {
            p.draw();
        }

        /* Shows the drawing to the screen, and waits 2000 milliseconds. */
        // StdDraw.show();
        // StdDraw.pause(2000);

        /**
         * Creating an Animation
         */
        StdDraw.enableDoubleBuffering();

        for (double t = 0; t < T; t += dt) {
            double[] xForces = new double[Planets.length];
            double[] yForces = new double[Planets.length];

            for (int count = 0; count < Planets.length; count++) {
                xForces[count] = Planets[count].calcNetForceExertedByX(Planets);
                yForces[count] = Planets[count].calcNetForceExertedByY(Planets);
            }

            for (int count = 0; count < Planets.length; count++) {
                Planets[count].update(dt, xForces[count], yForces[count]);
            }

            StdDraw.setScale(-uniRadius, uniRadius);
            StdDraw.picture(0, 0, "images/starfield.jpg");

            /**
             * Draw all of the Planets
             */
            for (Planet p : Planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        /**
         * print out the final state of the universe in the same format as the input
         */
        StdOut.printf("%d\n", Planets.length);
        StdOut.printf("%.2e\n", uniRadius);
        for (int i = 0; i < Planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
                    Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);
        }
    }
}
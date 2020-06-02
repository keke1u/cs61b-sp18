/**
 * Planet
 */
public class Planet {
    /**
     * current x position
     */
    public double xxPos;

    /**
     * current y position
     */
    public double yyPos;

    /**
     * current x velocity
     */
    public double xxVel;

    /**
     * current y velocity
     */
    public double yyVel;

    public double mass;

    public String imgFileName;

    final static double G = 6.67e-11; // constant
    /**
     * two constructors
     */
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet (Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /**
     * Calculate the distance between two planets
     */
    public double calcDistance(Planet a) {
        double dx = this.xxPos - a.xxPos;
        double dy = this.yyPos - a.yyPos;
        double r = Math.sqrt(dx * dx + dy * dy);
        return r;
    }

    /**
     * Describe the force exerted on this planet by the given planet
     */
    public double calcForceExertedBy(Planet a) {
        double F = (G * this.mass * a.mass) / (calcDistance(a) * calcDistance(a));
        return F;
    }

    /**
     * Describe the force exerted in the X direction
     */
    public double calcForceExertedByX(Planet a) {
        double Fx = (a.xxPos - this.xxPos) / calcDistance(a) *calcForceExertedBy(a);
        return Fx;
    }

    /**
     * Describe the force exerted in the Y direction
     */
    public double calcForceExertedByY(Planet a) {
        double Fy = (a.yyPos - this.yyPos) / calcDistance(a) *calcForceExertedBy(a);
        return Fy;
    }

    /**
     * Calculate the net X force exerted by all planets
     */
    public double calcNetForceExertedByX(Planet[] bl) {
        double Fnetx = 0;
        for (Planet p: bl) {
            if (!this.equals(p)) {
                Fnetx += calcForceExertedByX(p);
            }
        }
        return Fnetx;
    }

    /**
     * Calculate the net Y force exerted by all planets
     */
    public double calcNetForceExertedByY(Planet[] bl) {
        double Fnety = 0;
        for (Planet p: bl) {
            if (!this.equals(p)) {
                Fnety += calcForceExertedByY(p);
            }
        }
        return Fnety;
    }

    /**
     * update the planet's velocity and position from forces
     */
    public void update(double dt, double fX, double fY) {
        double ax = fX / mass;
        double ay = fY / mass;
        xxVel += dt * ax;
        yyVel += dt * ay;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    /**
     * Draw the Planet's image at the Planet's position
     */
    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}




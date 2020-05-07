public class Body{


    /**
    *Its current x position
    */
    public double xxPos;
    /**
    *Its current y position
    */
    public double yyPos;
    /**
     * Its current velocity in the x direction
     */
    public double xxVel;
    /**
     * Its current velocity in the y direction
     */
    public double yyVel;
    /**
     * Its mass
     */
    public double mass;
    /**
     * The name of the file that corresponds to the image that depicts the body (for example, jupiter.gif)
     */
    public String imgFileName;

    public Body(double xP, double yP, double xV,double yV, double m, String img){
        imgFileName=img;
        mass=m;
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
    }

    public Body(Body b){
        imgFileName=b.imgFileName;
        mass=b.mass;
        xxPos=b.xxPos;
        yyPos=b.yyPos;
        xxVel=b.xxVel;
        yyVel=b.yyVel;
    }

    public double calcDistance(Body b){
        /**
         * calculates the distance between two Bodys
         */
        double dx = this.xxPos - b.xxPos;
        double dy = this.yyPos - b.yyPos;
        return Math.hypot(dx, dy);
    }

    public double calcForceExertedBy(Body b){
        /**
         * returns a double describing the force exerted on this body by the given body.
         */

         double G = 6.67e-11;
         return G * this.mass * b.mass / (Math.pow(this.calcDistance(b), 2));
    }

    public double calcForceExertedByX(Body b){
        /**
         * describe the force exerted in the X direction
         */
        return this.calcForceExertedBy(b) * (b.xxPos - this.xxPos) / this.calcDistance(b);
    }

    public double calcForceExertedByY(Body b){
        /**
         * describe the force exerted in the y direction
         */
        return this.calcForceExertedBy(b) * (b.yyPos - this.yyPos) / this.calcDistance(b);
    }

    public double calcNetForceExertedByX(Body[] bs){
        /**
         * calculates the net X force exerted by all bodies in that array upon the current Body
         */
        double fxnet = 0;
        for(Body b : bs){
            if(!this.equals(b)){
                fxnet += this.calcForceExertedByX(b);
            }
        }
        return fxnet;
    }

    public double calcNetForceExertedByY(Body[] bs){
        /**
         * calculates the net y force exerted by all bodies in that array upon the current Body
         */
        double fynet = 0;
        for(Body b : bs){
            if(!this.equals(b)){
                fynet += this.calcForceExertedByY(b);
            }
        }
        return fynet;
    }

    public void update(double dt, double fx , double fy){
        /**
         * change in the body’s velocity and position in a small period of time dt
         */
        double ax = fx/this.mass;
        double ay = fy/this.mass;
        this.xxVel += ax * dt;
        this.yyVel += ay * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
    }
}
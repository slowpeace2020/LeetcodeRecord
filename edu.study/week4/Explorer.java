package week4;

public class Explorer {
    String name;
    int batterLife;
    float xPos;
    float yPos;

    public Explorer(String aName) {
        this.name = aName;
        this.batterLife = 100;
        this.xPos = 0;
        this.yPos = 0;
    }

    void moveLeft(float distance){
        if(distance>batterLife){
            System.out.println("Insufficient battery life");
            return;
        }
        xPos-=distance;
        batterLife-=distance;
        System.out.print("x position: "+xPos);
        System.out.println(" battery life: "+batterLife);
    }

    void moveRight(float distance){
        if(distance>batterLife){
            System.out.println("Insufficient battery life");
            return;
        }
        xPos+=distance;
        batterLife-=distance;
        System.out.print("x position: "+xPos);
        System.out.println(" battery life: "+batterLife);
    }

    void charge(){
        batterLife = 100;
    }

    String getName(){
        return name;
    }

    float getxPos(){
        return xPos;
    }

    float getYPos(){
        return yPos;
    }

    int getBatterLife(){
        return batterLife;
    }

    @Override
    public String toString() {
        return "Explorer{" +
                "name='" + name + '\'' +
                ", batterLife=" + batterLife +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}

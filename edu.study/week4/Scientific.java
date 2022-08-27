package week4;

public class Scientific extends Explorer{


    public Scientific(String aName) {
        super(aName);
    }

    @Override
    void moveLeft(float distance){
        if(distance>batterLife){
            System.out.println("Insufficient battery life");
            return;
        }
        xPos-=distance;
        batterLife-=distance;
        System.out.print("x position: "+xPos + " Y position: "+yPos);
        System.out.println(" battery life: "+batterLife);
    }

    @Override
    void moveRight(float distance){
        if(distance>batterLife){
            System.out.println("Insufficient battery life");
            return;
        }
        xPos+=distance;
        batterLife-=distance;
        System.out.print("x position: "+xPos + " Y position: "+yPos);
        System.out.println(" battery life: "+batterLife);
    }

    String checkSubstance(float volume, float grams){
        float density = grams/volume;
        if(density==2.70){
            return "Aluminum";
        }
        if(density==8.94){
            return "Copper";
        }
        if(density==19.3){
            return "Gold";
        }
        if(density==7.86){
            return "Iron";
        }
        if(density==11.34){
            return "Lead";
        }
        if(density==1.74){
            return "Magnesium";
        }
        if(density==10.5){
            return "Silver";
        }
        if(density==5.75){
            return "Tin";
        }
        if(density==7.14){
            return "Zinc";
        }

        return "Unkown";
    }

    @Override
    public String toString() {
        return "Scientific{" +
                "name='" + name + '\'' +
                ", batterLife=" + batterLife +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}

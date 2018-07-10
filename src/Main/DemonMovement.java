package Main;

/**
 * Created by alxye on 01-Jul-18.
 */
public class DemonMovement extends Thread {
    public static double distance = Math.sqrt(Math.pow((Content.demonX+Content.tileSize/2)-(Content.XPlayerOffset+Content.tileSize/2), 2) + Math.pow((Content.demonY-Content.tileSize/2)-Content.YPlayerOffset+Content.tileSize/2, 2));

    public void run() {
        /*while(Content.gameState) {
            Content.dem
        }*/
        while(true) { //cartesian literal demon - cartesian literal player
             /*differenceX = Content.demonX - (Content.XOffset - Content.getCurrentLocationX());
             differenceY = Content.demonY - (Content.YOffset - Content.getCurrentLocationY());
            System.out.println(differenceX + "&" +  Content.XOffset + "," + differenceY);*/
            //simple pythagoras theorem to calculate the distance between 2 points on two axis

            //need to calculate the gradient of the line
            //this makes the demon follow the player on the X axis
            distance = Math.sqrt(Math.pow((Content.demonX+Content.tileSize/2)-(Content.XPlayerOffset+Content.tileSize/2), 2) + Math.pow((Content.demonY-Content.tileSize/2)-Content.YPlayerOffset+Content.tileSize/2, 2));
            if((Content.demonX+Content.tileSize/2)-(Content.XPlayerOffset+Content.tileSize/2) > -1)
                Content.demonX-=Content.currentLevel.getDemonSpeed();
            if((Content.demonX+Content.tileSize/2)-(Content.XPlayerOffset+Content.tileSize/2) < -1)
                Content.demonX+=Content.currentLevel.getDemonSpeed();
            if((Content.demonY-Content.tileSize/2)-Content.YPlayerOffset+Content.tileSize/2 > -1)
                Content.demonYMod-=Content.currentLevel.getDemonSpeed();
            if((Content.demonY-Content.tileSize/2)-Content.YPlayerOffset+Content.tileSize/2 < -1)
                Content.demonYMod+=Content.currentLevel.getDemonSpeed();
            if(distance < 500)
                Content.demonProximity = true;
//            System.out.println(distance);

            if(distance < 100) {
                Content.gameOver();
                Content.resetPos();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

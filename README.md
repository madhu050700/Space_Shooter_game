#Team Learn2Play

#Project Members

-Ahsun Tariq (Team Liason)

-Parikshit Panwar

-Madhumitha Sivakumaran

-Guang-Zheng Lee



#Introduction

Instructions to run: Install JAVA and Android studio. Clone the repo and open it using Android studio. Sync gradle files. Create a run configuration for desktop (Select JAVA 11 Android API 32 Platform). Set the classpath module as team-Learn2Play.desktop. Set the main class as com.L2P.DesktopLauncher. Set the working directory to assets. Done! 
![](screendumps/config.PNG)

TODO: Describe the theme and concept of the game


![](screendumps/gameConcept.PNG)


#Current Features

-Player Character: A purple ship with following abilities:

![](screendumps/player.PNG)


    -Move in 8 directions using ARROW KEYS within bounds of world.

    -Fire projectiles using SPACE BAR.

    -Toggle slow mode using CAPS LOCK




-Regular Enemy Type 1:

![](screendumps/enemy1.PNG)

    -Red Ship firing yellow projectiles.

    -Lifetime is set to 10s, after that the ship leaves. If a ship leaves, a new ship will be spawned.


-Regular Enemy Type 2:

![](screendumps/enemy2.PNG)


    -Blue ship firing yellow projectiles.

    -Lifetime is set to 10s, after that the ship leaves. If a ship leaves, a new ship will be spawned.

-Mid Boss:

![](screendumps/midBoss.PNG)

    -Large grey sized ship spawning at 24s mark.

    -Fires red projectiles

    -Moves faster and is a little harder to dodge.
-Final Boss:

![](screendumps/boss.PNG)

    -Very Large evil looking Green ship spawning 30s.
    
    -Fires large yellow projectiles at much faster rate.

    

Project Vision

TODO: Describe the end product we aim to implement, the features me may add/have not added, the software design patterns that might be applicable. 

The **Space Shooter** game will have the following features:

-**Health features:**

Each Character will have different health points (HP), for example, the health points of bosses will be greater than the regular enemies which will make it difficult to destroy them. The player will have 7 health points at the starting of the game, each regular enemies will 2 health points, mid bosses will have 10 health points and final boss will have 20 health points. The player will be able to refill (upto 7 HP) its health by obtaining health bonuses which will also appear randomly in the game.

-**Firing features:**

Each character in the game is associated with unique firing abilities. Mid bosses and Final boss will have ability to fire their signature shots which will cause greater damage than their regular bullets. However, these signature moves will not be very frequent as compared to their regular shots. Furthermore, mid bosses and final boss will also have the ability to fire regular shots in different patterns. The player will also have the ability to fire signature shots which will cause more damage than regular bullets but only after obtaining firing bonuses which will appear randomly in the game. For the firing power, bullets of regaular enemies will decrease the player HP by 1, mid boss regular bullets will decrease player HP by 2 points and signature shots by 3 points, final boss regular bullets will decrease player HP by 3 points and signature shots by 4 points. 

**TODO:** Describe firing patterns and their damage power.

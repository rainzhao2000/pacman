# ICS4U CULMINATING ACTIVITY: Pac-Man

### By: Rain Zhao and Jacky Zhao

### How to setup github (mac os)

1. Create a github account and give me your username or the email you used, then I'll send an invite and you can accept it.
2. Install git from https://git-scm.com/downloads
3. (From here on out, type commands using terminal) cd into your folder of choice for workspace (see terminal navigation commands)
4. Configure your name with `git config --global user.name "Mona Lisa"`
5. Configure your github email `git config --global user.email "email@example.com"`
6. Clone this repository `git clone https://github.com/rainzhao2000/pacman.git`
7. `cd pacman`
8. Work on files in eclipse
9. To update your files (in case I made changes) `git pull`
10. To upload your files (commit and push)
```
git add .
git commit -m "very short description of what you did"
git push
```
Note: First time pushing requires `git push origin master` instead of `git push` and you can check the status of your files at any time with `git status`

<details>
 <summary>Description:</summary>
<br>
We’re going to try to replicate the popular game of Pac-Man in Java with GUI elements. Although, it is important to note that for complexity reasons (yes Pac-Man is incredibly complex, see for example just the ghost behaviour alone: http://gameinternals.com/post/2072558330/understanding-pac-man-ghost-behavior), our proposal provides a basis for our own simplified interpretation and modification of the rules; which may also differ from our final game.

Our version of the game will have the player control Pac-Man through a maze with each stage housing 240 Pac-Dots, 4 Power Pellets, 4 Ghosts as well as a random bonus fruit that appears during the gameplay under the center Ghost House where the ghosts spawn. When Pac-Man walks over a Pac-Dot or Power Pellet or a fruit, they will be eaten. Each Pac-Dot eaten adds 10 points to the player’s score and the fruits vary from Cherry: 100 points, to Strawberry: 300 points, to Orange: 500 points, to Apple: 700 points, to Melon: 1000 points, to Galaxian Boss: 2000 points, to Bell: 3000 points, to Key: 5000 points. 

When a Power Pellet is eaten the player gains 50 points and the Ghosts will turn into edible form for 10 seconds in stage 1. When the Pac-Man walks over a Ghost when they are edible, the Ghost will be eaten and sent back to the Ghost House and respawn, the player also earns 200 points for the first kill, 400, 800, and 1600 points for the second, third, and fourth kill within a single Power Pellet. When the Pac-Man walks over a Ghost when they are inedible, the player will lose a life (3 total), if the player still has a life then the Ghosts and the player will reset to their respective spawns but the remaining Pac-Dots of the stage will remain. A stage is completed when all the Pac-Dot and the Power Pellets have been eaten. Upon a new stage, the ghosts and Pac-Man will all move faster by 5%, the ghosts’ edible times will also decrease by 5%. 

Pac-Man can be controlled by the arrow keys, and only the most recent key is logged for operation when Pac-Man reaches an intersection. Pac-Man will, however, stop moving upon collision with a wall if no input is received. The ghosts, named: Blinky (red), Pinky (pink), Inky (cyan), and Clyde (orange), each have their own personalities, cannot turn 180 degrees except when they first become edible, move 5% faster than Pac-Man when they are inedible, and move 5% slower when edible. Blinky will move randomly until it sees Pac-Man in its line of sight, and will pursue in his direction until he either eats Pac-Man or reaches an intersection/corner where he then picks a random available direction to look in. If Blinky can no longer find Pac-Man in its direction at this point, it will continue its random behaviour. Pinky will exhibit the same behaviour as Blinky, but there is a random chance it can decide to turn at an intersection and move around a block of the maze to try to intercept Pac-Man on the other side. Inky will exhibit Blinky’s behaviour 75% of the time and wander aimlessly the other 25% of the time while Clyde will exhibit Blinky’s behaviour only 50% of the time.
</details>
 
### Details:
* #### Objects:
  * Pacman
    * Variables:
      * speed
      * direction
      * xPos
      * yPos
    * Methods:
      * update()
      * paint()
      * getPos()
      * death()
  * Ghosts static class
    * Methods:
      * paint()
      * getPos()
      * death()
    * Subclasses: Blinky, Pinky, Inky, Clyde
      * Variables:
        * speed
        * Direction
        * row
        * col
        * xPos
        * yPos
      * Methods:
        * update() [specific to the ghost]
        * changeMode() [update Ghost to vulnerable state]
  * GameManager Class
    * Variables:
      * 2D int Array
    * Methods:
      * main()
      * setup() [use respawn and resetCoin]
      * update()
      * paint()
      * respawn() [reset Ghosts and Pac Man]
      * resetCoin() [reset all Pac-Dots and Power Pellets]
      * checkEnding()
      * displayEnding()
      * keyListener()
#### Details (continued):
For our game, everything is going to be handled by the GameManager class; this includes: the setup of the game (map, scores, positions etc.), the main update method that checks everything by calling specific checking and updating methods in individual objects, and the GUI drawing methods. When the game starts, the main method will setup the window size and the GUI elements etc. and the setup method is called to set the scores, ghost and Pac-Man properties, collectable item locations, etc. This setup method is also useful for the fact that we can restart the game any time we want and every aspect of the game apart from the main window elements receive a complete reset. Then in the update method is where we can handle object and item locations within a 2D array that is in the GameManager class and do the checking for collisions, controls, points, deaths, spawning, etc. Lastly our paint method should be called by the update method to repaint the frame of the game each time, rendering the newly updated positions and animations and any useful graphic elements. To conclude, game logic and handling will be take place relative to the 2D array we create in the GameManager class, and the GUI will simply paint the elements and map them accordingly to the space using math.

| INPUT | PROCESSING | OUTPUT |
| --- | --- | --- |
| { ←, ↑, ↓, → }<br>The arrow keys on the keyboard<br><br>The key pressed will be stored in 4 boolean variables. Each boolean variable represent a direction, their values will be assigned in the KeyPressed method. More detail will be discussed in the Processing section. | In the KeyPressed method, we will determine which arrow key is pressed by checking the KeyCode of the KeyEvent. Then we will set all 4 directions (their variables are named as up, down, left, and right) to false and set the direction that is pressed to true. | The Pac-Man will try to turn in the direction indicated. If the Pac-Man can not turn immediately, it will wait for the very next intersection that allows the turning operation. |
| Mouse left click for starting the game<br><br>Nothing is needed to detect this input, the start button will be a clickable button. | No processing needed. | Begin the game. |

The reason for the IPO chart being empty is that the complexity of our project is not on how the program process the user’s input, but on the algorithm that allows the program to function as intended.

### Requirements

You may come up with an idea of your own; however, keep in mind that it must meet the standards and expectations for a Grade 12 University Preparation course.
Your program must incorporate programming concepts learned throughout the course of the year, including:  
* Java GUI and/or graphics components
* Data Types
* Decision statements 
* Looping structures
* 1D & 2D arrays
* Object-oriented programming 
* Methods & overloading
* Writing to File
* Recursion 

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

# Proposal

<details>
 <summary>Description:</summary>
<br>
We’re going to try to replicate the popular game of Pac-Man in Java with GUI elements. Although, it is important to note that for complexity reasons (yes Pac-Man is incredibly complex, see for example just the ghost behaviour alone: http://gameinternals.com/post/2072558330/understanding-pac-man-ghost-behavior), our proposal provides a basis for our own simplified interpretation and modification of the rules; which may also differ from our final game.

Our version of the game will have the player control Pac-Man through a maze with each stage housing 240 Pac-Dots, 4 Power Pellets, 4 Ghosts as well as a random bonus fruit that appears during the gameplay under the center Ghost House where the ghosts spawn. When Pac-Man walks over a Pac-Dot or Power Pellet or a fruit, they will be eaten. Each Pac-Dot eaten adds 10 points to the player’s score and the fruits vary from Cherry: 100 points, to Strawberry: 300 points, to Orange: 500 points, to Apple: 700 points, to Melon: 1000 points, to Galaxian Boss: 2000 points, to Bell: 3000 points, to Key: 5000 points. 

When a Power Pellet is eaten the player gains 50 points and the Ghosts will turn into edible form for 10 seconds in stage 1. When the Pac-Man walks over a Ghost when they are edible, the Ghost will be eaten and sent back to the Ghost House and respawn, the player also earns 200 points for the first kill, 400, 800, and 1600 points for the second, third, and fourth kill within a single Power Pellet. When the Pac-Man walks over a Ghost when they are inedible, the player will lose a life (3 total), if the player still has a life then the Ghosts and the player will reset to their respective spawns but the remaining Pac-Dots of the stage will remain. A stage is completed when all the Pac-Dot and the Power Pellets have been eaten. Upon a new stage, the ghosts and Pac-Man will all move faster by 5%, the ghosts’ edible times will also decrease by 5%. 

Pac-Man can be controlled by the arrow keys, and only the most recent key is logged for operation when Pac-Man reaches an intersection. Pac-Man will, however, stop moving upon collision with a wall if no input is received. The ghosts, named: Blinky (red), Pinky (pink), Inky (cyan), and Clyde (orange), each have their own personalities, cannot turn 180 degrees except when they first become edible, move 5% faster than Pac-Man when they are inedible, and move 5% slower when edible. Blinky will move randomly until it sees Pac-Man in its line of sight, and will pursue in his direction until he either eats Pac-Man or reaches an intersection/corner where he then picks a random available direction to look in. If Blinky can no longer find Pac-Man in its direction at this point, it will continue its random behaviour. Pinky will exhibit the same behaviour as Blinky, but there is a random chance it can decide to turn at an intersection and move around a block of the maze to try to intercept Pac-Man on the other side. Inky will exhibit Blinky’s behaviour 75% of the time and wander aimlessly the other 25% of the time while Clyde will exhibit Blinky’s behaviour only 50% of the time.
</details>
 
<details>
 <summary>Details:</summary>
 <h4>Objects:</h4>
 <ul>
  <li>Pacman
   <ul>
    <li>Variables:
     <ul>
      <li>speed</li>
      <li>direction</li>
      <li>xPos</li>
      <li>yPos</li>
     </ul>
    </li>
    <li>Methods:
     <ul>
      <li>update()</li>
      <li>paint()</li>
      <li>getPos()</li>
      <li>death()</li>
     </ul>
    </li>
   </ul>
  </li>
  <li>Ghosts static class
   <ul>
    <li>Methods:
     <ul>
      <li>paint()</li>
      <li>getPos()</li>
      <li>death()</li>
      </ul>
     </li>
    <li>Subclasses: Blinky, Pinky, Inky, Clyde
     <ul>
      <li>Variables:
       <ul>
        <li>speed</li>
        <li>Direction</li>
        <li>row</li>
        <li>col</li>
        <li>xPos</li>
        <li>yPos</li>
       </ul>
      </li>
      <li>Methods:
       <ul>
        <li>update() [specific to the ghost]</li>
        <li>changeMode() [update Ghost to vulnerable state]</li>
       </ul>
      </li>
     </ul>
     </li>
   </ul>
  </li>
  <li>GameManager Class
   <ul>
    <li>Variables:
     <ul>
      <li>2D int Array</li>
     </ul>
    </li>
    <li>Methods:
     <ul>
      <li>main()</li>
      <li>setup() [use respawn and resetCoin]</li>
      <li>update()</li>
      <li>paint()</li>
      <li>respawn() [reset Ghosts and Pac Man]</li>
      <li>resetCoin() [reset all Pac-Dots and Power Pellets]</li>
      <li>checkEnding()</li>
      <li>displayEnding()</li>
      <li>keyListener()</li>
     </ul>
     </li>
   </ul>
   </li>
 </ul>
 </details>
 
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

# Final Documentation

<details>
 <summary>Decription:</summary>
 <br>
For this project, we recreated a simplified version of Pac-Man with an added bonus of customizable game maps. In the game, the pac-dots, the power pellets, and the fruits (known as edibles) can be eaten by the pacman character. Every pac-dot eaten earns the player 10 points; every fruit, 100 points; and when the pacman eats a power pellet, the player will not only earn 50 points but also ghosts will slow down and become edible for 10 seconds. When all the edibles are eaten by the pacman, the level is clear and the player will enter the next stage. The map remains the same but will increase the speed of all characters by 10% and reduce the edible time of the ghosts by 10%.
 
What is different in our version of Pacman, compared to the original Pacman game, is the AI logic of the ghost. We found that the complexity of the ghost AI in the original game is beyond our ability in the time allotted (see http://gameinternals.com/post/2072558330/understanding-pac-man-ghost-behavior). In the original game, the 4 ghosts have much more intricate details to their personalities and behaviour. Our ghosts instead, have a probability of pursuing the pacman when they are inedible and running away from the Pacman when they are edible. Blinky, the red ghost, will 100% pursue pacman when it is inedible and will 100% run away from pacman when itself is edible. In a similar way, Pinky the pink ghost, Inky the cyan ghost, and Clyde the yellow ghost will each have a probability of 75%, 50%, and 25%. When pacman is not in their line of sight towards the direction that they are moving, or if pacman is not visible from the intersection that they are in, the ghosts will randomly choose a direction and move on. Additionally, the ghosts know how to get out of dead ends or run away by turning 180 degrees.

Our portal implementation differs a bit from the original game. Since we allow the user to put more than 2 portals, we have to modify the teleporting logic a little bit to make the game both reliable and enjoyable. If there are only a pair of portals, then by entering from any one of them will bring you to the other one. If there are more than 2 portals, the game will randomly teleport you to one of the many exits. The direction that the character was moving before they enter the portal will remain unchanged and carry on after they exit the portal. It is not possible to teleport again from the portal that the character just exit without moving out of the tile that contains the portal.

We also implemented the ability to load and save the maps that the player have created so that when they want to replay a level that they have saved they can easily do so. In the Map Creator window the player can choose from a selection of items on the right side to draw into the map as well as change the lives counter. For debug purposes, we included toggle pos and toggle grid buttons to be able to see behind the scenes as well as a speed multiplier that can make the game run in fast or slow motion.
</details>
<details>
 <summary>Screenshots</summary>
</details>
<details>
 <summary>Details</summary>
 <br>
 <h4>Overview:</h4>
Our game is built on object oriented programming. We have a total of seven classes: Main, Game, MapCreator, DrawPanel, Chararcter, Pacman, and Ghost. The Main class holds some important static elements which is referred to throughout the other classes, but other than that it holds the main method that instantiates a new game window. Using Eclipse Windowbuilder we designed the Game and MapCreator windows to hold the necessary buttons, spinners, labels, and other gui elements for the players to see and use. The Game class also listens for key presses to control the game and the MapCreator offers the tools: DrawPanel and buttons for map creation, saving, and loading. Our DrawPanel class is an extension of JPanel and handles all of logic and painting required to make the game run. DrawPanel is on a timer that paints at the conditions of a framerate, and for each frame all the objects in the map are updated. While most fixed elements of the map array can be painted from DrawPanel, our character objects including pacman and the ghosts need to be painted in their own away to make animations work. As pacman and the ghosts share similar methods and variables, we’ve decided to make a Character superclass that provides the platform for how the pacman and ghosts move, paint, and access their properties. The Pacman class itself has to handle player controls as well as checking conditions for running into walls, eating edibles, interacting with ghosts. Lastly, the ghost class is purely responsible for ray tracing pacman and deciding whether to chase based on given probability.

<h4>Main:</h4>
<ul>
<li>Holds Code enum that we use as a reference to ingame elements</li>
<li>Holds Direction enum that we use to indicate 4 directions for Character objects</li>
<li>Holds Game and Character objects as well as map dimensions and framerate for references</li>
<li>Instantiates Game</li>
</ul>

<h4>Game:</h4>
<ul>
<li>Holds score panel, game canvas panel, info panel and debug panel to show control surfaces and information</li>
<li>Instantiates DrawPanel as a canvas</li>
<li>Listens for arrow key controls and P pause</li>
<li>Can instantiate MapCreator</li>
</ul>

<h4>MapCreator:</h4>
<ul>
<li>Holds a selection of buttons that correspond to ingame elements</li>
<li>Instantiates its own DrawPanel as a fixed canvas for creating maps</li>
<li>Can save the current map to a text file holding int values that correspond to enum elements</li>
<li>Can open maps from reading the int values in a text file</li>
<li>Implements created map on close</li>
</ul>

<h4>DrawPanel:</h4>
<ul>
<li>Updates every frame on a timer</li>
<li>Starts with a JOptionPane prompt for the player to begin as well as informs player when game is paused and when game is over</li>
<li>Draws the map by for looping through array</li>
<li>Invokes the draw methods of the characters for animation</li>
<li>Checks for stage completion, deaths, and can set ghost edibility</li>
<li>Holds map modifying methods e.g setTile, uploadMap etc</li>
</ul>

<h4>Character:</h4>
<ul>
<li>Super class of pacman and ghost</li>
<li>Holds characteristics such as position, row, column</li>
<li>Holds super methods such as animate</li>
<li>Holds super methods that are being overridden by subclasses</li>
<li>Back bone of character</li>
</ul>

<h4>Pacman:</h4>
<ul>
<li>Keeps track of pacman’s position in terms of the map and window (x, y and row, col), as well as speed, direction and lives</li>
<li>Checks the surrounding tiles for available spaces (e.g path, pac-dot, powerpellet, fruit, etc)</li>
<li>Detects ghost collision and edibles</li>
</ul>

<h4>Ghost:</h4>
<ul>
<li>Extends Character</li>
<li>Holds its own AI system for direction decision</li>
<li>to be continued</li>
</ul>
</details>

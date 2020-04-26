# final-project-team-35-scrabble

Gaming is a popular entertainment venture, a lot of gaming currently is being done virtually online. In the cit-591 course, we have had the practice of some of the popular games like BlackjackSolitaire as an assignment, and on a basic level Chess, Snakes and Ladders etc. We want to build on this expertise and scale higher with new games called Scrabble. In this game, a user (or two users) can access and play against each other. In this game, players will have a game board, 15 by 15 tiles, a letter bag, and seven racks. The rule is player 1 clicks the letter in the rack and then clicks any tile on the board to relocate the letter. Each time the player finish this round, click submit button to save the words on board, then it will pass to the other player or next round. The more words player made, the more score the player win. Each letter has its own score and some tiles have bonus effects.

## Getting Started

## How to run in terminal 

1. Clone this repo to your local machine with this link https://github.com/UPenn-CIT599/final-project-team-35-scrabble.git
2. Open a terminal under folder "Scrabble".
3. Build Gradle project in terminal:   ./gradlew build
4. Run the project in terminal:  ./gradlew run

## How to run in Eclipse
1. Add JavaSE-13 library.
Right click on the Scrabble project -> Project -> Properties -> Java Build Path -> Library tab -> Add Library -> JRE System Library ->
Execution environment -> JavaSE-13
2. Download JavaFX from https://openjfx.io/. Unzip it.
3. Add JavaFx library to eclipse
Right click on the Scrabble project -> Project -> Properties -> Java Build Path -> Library tab -> Add Library -> User Library -> User Library -> New -> name it as JavaFX -> Browse to the JavaFx library you just downloaded. Add all JARs in the folder. Here is a more detailed instruction: https://www.javatpoint.com/javafx-with-eclipse
4. Build Gradle
Open your pc(mac) terminal -> open the scrabble folder in terminal -> input "./gradlew build" (Mac) ".\gradlew build" (PC)

### How to Play

In the welcome page, you can choose singler player mode 🕺 or multi player mode and name your player.👯‍♀️

<img src="https://github.com/UPenn-CIT599/final-project-team-35-scrabble/blob/master/Scrabble/src/main/resources/welcome%20interface.png" width="600" height="400">

In the game page, you can click the letter in the rack and then click any tile you want to relocate the letter.🎴
First word must be played using the center square with a star. ⭐️
Each time you finish this round, click submit button , it will pass to the other player or your next round.🔂
The more words you made, the more score you win. Each letter has its own score and some tiles have bonus effects.🤑

<img src="https://github.com/UPenn-CIT599/final-project-team-35-scrabble/blob/master/Scrabble/src/main/resources/multiple%20player%20interface.png" width="600" height="400">

You can search if a word is in dictionary by clicking search button 📖, swap tiles from tile bag by clicking swap button and the letter you want to swap and swap button again 🔄, pass your turn by clicking pass button ▶️, and undo your step by clicking undo button.↩️

<img src="https://github.com/UPenn-CIT599/final-project-team-35-scrabble/blob/master/Scrabble/src/main/resources/search%20function.png" width="600" height="400">

You can check how many letters are left in letter bag by clicking bag button.💰

<img src="https://github.com/UPenn-CIT599/final-project-team-35-scrabble/blob/master/Scrabble/src/main/resources/bag%20of%20letter.png" width="600" height="400">

Here is a video demo of the game by Mina:
https://youtu.be/HoPkGZyrYZQ

## Built With

* [JavaFX](https://openjfx.io/) - The GUI interface.
* [Gradle](https://gradle.org/) - Project Configuration.


## Authors

* **Daisy Liu**
* **Shodhan Shetty**
* **Yugui Chen**



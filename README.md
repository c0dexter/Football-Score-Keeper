# Score Keeper App (UDACITY COURSE) + private project of Arduino External LCD with the Bluetooth connection to displaying points

[![N|Solid](http://i.imgur.com/LgahjBMm.png)](http://i.imgur.com/LgahjBMm.png) [![N|Solid](http://i.imgur.com/PXnL0Rpm.png)](http://i.imgur.com/PXnL0Rpm.png) [![N|Solid](http://i.imgur.com/cxpwKbDm.png)](http://i.imgur.com/cxpwKbDm.png) [![N|Solid](http://i.imgur.com/GRKmLexm.jpg)](http://i.imgur.com/GRKmLexm.jpg)

----

# YouTube video-clips with short presentations #

### Presentation of application ###
[![Watch the video](http://i.imgur.com/fSHcGGPm.png)](https://youtu.be/yV69eKoHzVg)

### Presentation of application in action with external display ###
[![Watch the video](http://i.imgur.com/nfBaGIpm.png)](https://youtu.be/WfG5685hLw0)

----
**PROJECT SPECIFICATION**
*Score Keeper*

# Layout #

## CRITERIA ##
1. Game Chosen
2. Overall layout
3. Column contents
4. Score buttons
5. Reset button
6. Best practices

## MEETS SPECIFICATIONS ##
Your design must include:

**Ad 1.** The chosen game has either multiple amounts of points that can be scored, as in american football, or multiple important metrics to track, such as fouls, outs, and innings in baseball.
 
**Ad 2.** App is divided into two columns, one for each team.
 
**Ad 3.** Each column contains a large TextView to keep track of the current score for that team. Optionally, a second TextView to track another important metric such as fouls can be added.

**Ad 4.** Each column contains multiple buttons. The buttons must track either:

 * Each track a different kind of scoring

 Or

 * Each track a different metric (one score, the other fouls, for instance).

**Ad 5.** The layout contains a ‘reset’ button.

**Ad 7.** The code adheres to all of the following best practices:

* Text sizes are defined in sp

* Lengths are defined in dp

* Padding and margin is used appropriately, such that the views are not crammed up against each other.

----

# Functionality #

## CRITERIA ##
1. Errors
2. Score Button Function
3. Reset Button Function

## MEETS SPECIFICATIONS ##
**Ad 1.** The code runs without errors.

**Ad 2.** Each score button updates the score TextView in its column by adding the correct number of points.

**Ad 3.** The reset button resets the scores on both of the score TextViews.


----

# Code Readability #

## CRITERIA ##
1. Naming conventions
2. Code style

## MEETS SPECIFICATIONS ##
**Ad 1.** Any classes are named after the object they represent.All variables are named by their intended contents. All methods are named by their intended effect or in the style required by a callback interface.

**Ad 2.** here are no unnecessary blank lines.One variable is declared per declaration line. The code within a method is indented with respect to the method declaration line.

----

# CODE FOR EXTERNAL DISPLAY (ATMEGA, ARDUINO) #

Code with colored syntax: [Code for external display based on Arduino](http://wklej.org/id/3075917/)

```
#!c

#include <LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd(0x27, 16, 2);
String readString;
byte index = 0;     // Index into array; where to store the character
bool headerDisplayed = false;


// Team A (HOSTS) Strings declaration
String teamAName, teamAScore, teamAYellowCard, teamARedCard;

// Team B (GUESTS) Strings declaration
String teamBName, teamBScore, teamBYellowCard, teamBRedCard;


// Variant of display mode
int displayMode = 0;

// A method for updating Strings
void updateStringsValues() {
  teamAName = getValue(readString, '|', 0);
  teamAScore = getValue(readString, '|', 1);
  teamAYellowCard = getValue(readString, '|', 2);
  teamARedCard = getValue(readString, '|', 3);
  
  teamBName = getValue(readString, '|', 4);
  teamBScore = getValue(readString, '|', 5);
  teamBYellowCard = getValue(readString, '|', 6);
  teamBRedCard = getValue(readString, '|', 7);
  
  Serial.println("Updating data");
}


// LCD view - for displaying scores
void displayScores(){
  
  if(headerDisplayed == false){
    lcd.setCursor(0,0);
  lcd.print("** GOALS VIEW **");
  delay(1500);
  headerDisplayed = true;
  lcd.clear();
  }
  
  lcd.setCursor(1, 0);
  lcd.print(teamAName);
  lcd.setCursor(9, 0);
  lcd.print(teamBName);
  lcd.setCursor(3, 1);
  lcd.print(teamAScore);
  if(teamAName != ""){
    lcd.setCursor(7, 1);
    lcd.print("-");
  }
  lcd.setCursor(11, 1);
  lcd.print(teamBScore);
}

// LCD view - for displaying statistic of HOSTS TEAM
void displayHostsStats(){
  if(headerDisplayed == false){
    lcd.setCursor(0,0);
  lcd.print("** HOSTS VIEW **");
  delay(1500);
  headerDisplayed = true;
  lcd.clear();
  }
  lcd.setCursor(0, 0);
  lcd.print("* " + teamAName + " stats *");
  lcd.setCursor(0, 1);
  lcd.print("G: " + teamAScore + "  " + ("Y: "+(teamAYellowCard + ("  R: " +  teamARedCard))));
}

// LCD view - for displaying statistic of GUESTS TEAM
void displayGuestsStats(){
  if(headerDisplayed == false){
    lcd.setCursor(0,0);
  lcd.print("* GUESTS VIEW *");
  delay(1500);
  headerDisplayed = true;
  lcd.clear();
  }
  lcd.setCursor(0, 0);
  lcd.print("* " + teamBName + " stats *");
  lcd.setCursor(0, 1);
  lcd.print("G: " + teamBScore + "  " + ("Y: "+(teamBYellowCard + ("  R: " +  teamBRedCard))));
}

// LCD view - for displaying mixed views one by one
void displayDynamicStats(){
  if(headerDisplayed == false){
    lcd.setCursor(0,0);
  lcd.print("* DYNAMIC VIEW *");
  delay(1500);
  headerDisplayed = true;
  lcd.clear();
  }
  lcd.clear();
  displayScores();
  delay(2000);
  lcd.clear();
  displayHostsStats();
  delay(2000);
  lcd.clear();
  displayGuestsStats();
  delay(2000);
  lcd.clear(); 
}

// Method for dividing String by using separator and putting it into String Array
String getValue(String data, char separator, int index) {
  int found = 0;
  int strIndex[] = {0, -1};
  int maxIndex = data.length() - 1;

  for (int i = 0; i <= maxIndex && found <= index; i++) {
    if (data.charAt(i) == separator || i == maxIndex) {
      found++;
      strIndex[0] = strIndex[1] + 1;
      strIndex[1] = (i == maxIndex) ? i + 1 : i;
    }
  }
  return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}


// Setup - setup display
void setup() {
  lcd.init(); // initialize the lcd
  lcd.backlight();
  Serial.begin(9600);
  lcd.begin(16, 2);
  lcd.setCursor(1, 0);
  lcd.print("UDACITY Course");
    delay(1500);
  for(int i=0; i<16; i++) {
    lcd.scrollDisplayLeft();
    delay(200);
  }
  lcd.setCursor(0, 1);
  lcd.print("Score Keeper App!");
  delay(300);
  for(int i=0; i<16; i++) {
    lcd.scrollDisplayRight();
    delay(200);
  }
  delay(2000);

  for(int i=0; i<16; i++) {
    lcd.scrollDisplayRight();
    delay(200);
  }
  lcd.clear();
  while (Serial.available() > 0) {
    Serial.read();
  }
}

// This is MAIN
void loop() {

// Reading data from Android
  if (Serial.available()) {
    delay(100);
    readString = "";
    while (Serial.available()) {
      delay(5);  //small delay to allow input buffer to fill
      char c = Serial.read();  //gets one byte from serial buffer
      if (c == '#' || c == '\0') {
          Serial.println("Sign # - Stop");
        break;
      }  //breaks out of capture loop to print readstring
      readString += c;
    }
    // Checking if user clicked on Arduino app a DISPLAY MODE button
    if(readString == "DisplayMode"){
      // increment variable for display mode
      displayMode++;
      lcd.clear();
      headerDisplayed = false;
      if(displayMode>3){
      displayMode=0;
      readString="";
      lcd.clear();
      }
      Serial.println("Received command for change view");
      readString="";
    }else{
    updateStringsValues();
    }
  } 

// Board views switching
switch (displayMode) {
    case 0:
      displayScores();
      break;
    case 1:
      displayHostsStats();
      break;
    case 2:
      displayGuestsStats();
      break;
    case 3:
      displayDynamicStats();
      break;
    default: 
      displayScores();
    break;
  }
}
```

## Team 8-Bit Scouting app 2024
This app is still a work in progress. Guidelines and app flow are described below.

### Guidelines and concepts
_General ideas governing the design and development of the app._

#### Configurable
1. Everything should be as replaceable and customizable as possible
2. Each page of data input holds a number of predefined input types
3. There is no set number of pages for each section

#### Failsafe
1. Data should always be stored in more than one place
2. Internet connectivity is assumed to be unavailable while at competitions

#### Ease of use
1. Data recording should not require precise input
2. Especially while match scouting
3. Have the option to change values/edit existing data wherever possible


### Main App Flow
_How the app will be set up for each season and used at competition._

#### Season start
1. Decide what fields we want to record
2. Design layouts and pages for each

#### Before Competition
1. Separate program generates information and saves it to a google sheet
2. All competing teams for pit scouting
3. A list of teams for each match, and who scouts each one for match scouting
4. Download this sheet to each tablet
5. Put on a sd card

#### At competition (Match scouting)
1. When you open the app to match scouting mode, it uses a scout ID to know which robots to show
2. Select current match
3. Grey out matches that have already been scouted, or put them in a different menu
4. Record pre-match information
5. Starting position
6. Robot present
7. Record auto data
8. Record teleop data
9. Review and confirm, additional notes
10. Save match data to sd card as backup
11. Generate qr code to scan from main device

#### At competition (Pit scouting)
1. Select team to scout
2. Grey out teams that have already been scouted, or put them in a different menu
3. Record pit scouting data
4. Save information to sd card


### Inputs
_All inputs that can be used in the scouting app. These will be a uniform size and therefore can be easily swapped out and customized for each season._

#### Types
* Switch
  * Simple true/false
  * Press to toggle between the two
* Cycle option
  * Tap to cycle through a set of options
* Dropdown Menu
  * Tap to cycle through a set of options
* Number
  * Press + or - to increment/decrement
* Text input
  * Place where you can fill in text
  * No save button because it would get missed

#### Sizing and Positioning
* Each item has a predefined width and height
* Always a multiple of a given minimum sized square
* Items can be placed in rows and columns
* Items should be easily replaceable due to their uniform sizing

#### Future Types
* Field coordinate selection
    * Picture of the current  seasons field
    * Click anywhere to select a position
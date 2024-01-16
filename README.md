# MotoEvents
---

An application that displays MotoGP and World Superbikes race schedules in
order of date and time.

Information is received by calling the Sportradar MotoGP v2 API. This allows the retreival of MotoGP and WSBK schedules, statistics, results etc. MotoEvents uses 
stage schedule data to curate a list of race days for both sports. The list in ordered by soonest first and presents the sport name, followed by the race location, and followed finally by the date of the year. 

The UI of MotoEvents was created with JavaFX, which allowed for a quick and clean build given the low amount of info being presented to the user.

---
### Architecture:

MotoEvents follows the architecture of a typical JavaFX aplication. It has a Main class and a Controller class, as well as an FXML file to configure the view. This 
architecture is reminiscent of the MVC pattern and the use of fxml is quite similar to what is seen in Android Development. In addition to the basics, I created API and MotoEvent classes to manage the retrieval and parsing of race information (see the source folder).

**_To run MotoEvents, simply clone the project and run the Main Class. (note: a trial development key is currently in use. This will expire on 15/04/24)_**

---

### User Interface:

<img width="625" alt="image" src="https://github.com/jamesclackett/MotoEvents/assets/55019466/dbaaf94b-6973-4383-aeed-80894530af03">



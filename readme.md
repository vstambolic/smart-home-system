<h1> Smart Home System </h1>

## Architecture

The system consists of 5 components:

- Music Player
- Alarm
- Planner
- User Service
- User Interface
	
The first four components are all mutually connected and communicate with each other using Java Message Service (JMS). Also, they share a mutual MySQL database with whom they communicate using Java Persistence API (JPA). User Interface is a JavaFX application which communicates with User Service using RESTful web services it provides. MySQL, JMS and JPA services must be configured before running the system.

"User Interface" also uses Bing Maps API ([Bing Maps V8 Web Control](https://docs.microsoft.com/en-us/bingmaps/v8-web-control/) and [Bing Maps REST Services](https://docs.microsoft.com/en-us/bingmaps/rest-services/)) for displaying maps and calculating travel distance, and [Spotify Web API](https://developer.spotify.com/documentation/web-api/) for searching and embedding Spotify tracks. Necessary API keys must be embedded in the source code on marked places before running the system.

## Features

Alarm

- Fetching user's alarms from the database.
- Turning user's alarms on and off.
- Setting new alarm to ring at the desired time.
- Setting periodic alarms.
- Configuring desired alarm ringtone (Spotify track)

Music Player

- Searching for and playing Spotify tracks.
- Storing and fetching Spotify tracks that user had saved in the database.

<p align="center">
	<img src="https://user-images.githubusercontent.com/61201104/126882944-6a882975-8263-46e5-9986-e65860a95a12.JPG" width="80%">
</p>

Planner
- Saving user's events in the database with its start time, duration and location.
- Organizing user's events so they don't overlap.
- Setting reminder for event.
- Calculating travel distance between events' locations using Bing Maps API.

<p align="center">
	<img src="https://user-images.githubusercontent.com/61201104/126882945-cf772234-46e6-41b4-8973-d2b68a965431.JPG" width="80%">
</p>

User Service

- Provides REST API for the User Interface component
- Authentication and authorization services.

User Interface

- Provides communication between user and the rest of the system without communicating with the database directly.
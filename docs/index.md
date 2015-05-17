CollabTodo
=============

**Hint:** Best viewed in Firefox / Internet explorer by opening ```index.html```

## 1. Introduction

CollabTodo is simply put a Colloborative Todo application with the added 
advantage of being fully decentralized and auto syncing to multiple devices in the background.

**Note:** Requires Android 2.3+ and Multicast enabled network

----------------------------------------------------------------------------------------------------

## 2. Features

  * Create Lists
  * Add Entries to List
  * Delete Lists
  * Delete Entries from List
  * Assign priority to Entry
  * Move Entry between Lists
  * Change Priority of Entry
  * Dynamic Entry sorting with priority
  * Real time changes from any device
  * A gorgeous UI & intutive UX

---------------------------------------------------------------------------------------------------

## 3. A Quick Demo

Requirements:

  * Install app
  	At least on 2 Android devices
  	
  * Connect to Network
  	Devices should be in the same network with multicast support


Running:

  * Open application
  	On both devices. Kill if opened before - gives a fresh start
  	
  * Pull navigation
  	A gesture from ```Left-edge to right```
  	
  * Create list
  	```Add List``` button in navigation
  	 
  * Add entries
  	Use the floating ```+``` button
  	
  * Have fun!
  	It's intutive.

-----------------------------------------------------------------------------------------------------

## 4. Importing code

  * The Project is based on [Android studio](http://developer.android.com/tools/studio/index.html). 
  * The complete Android studio project is available in ```/code``` directory
  * Import project and voila! You have everything you need.
-----------------------------------------------------------------------------------------------------

## 5. Code structure

**Note:** The core logic is structured into 3 layers

  * <b>UI layer</b>
    All user interface logic. This communicates only with ```Bridge layer```
    
  * <b>Bridge layer</b>
    Forms a communication bridge between the ```UI layer``` and ```Network layer```
    
  * <b>Network layer</b>
    Contains umundo and other support logic to run the backend. Communicates only with ```Bridge layer```
    

**Note:** All 3 layers are implemented in a total of 8 packages 

  * <b>database</b> (Bridge)
  	Handles all database related operations like ```save```, ```fetch``` and ```delete``` on objects
  	
  * <b>dialog</b> (UI)
  	Contains all dialogs used in the application
  	
  * <b>fragment</b> (UI)
  	Contains all fragment classes that are used
  	
  * <b>helper</b> (Bridge)
  	```DeviceUuidFactory``` : Unqiue device id generation
  	```GsonExclude``` : A Gson exclusion strategy to work well with Sugar Database
  	```JsonFactory``` : Converts objects to Json strings and vice versa after applying required strategies
  	```NumberFactory``` : For generating random numbers, strings or hashs fitting certain criteria
  	```Procedures``` : <b>The glueing component of Bridge.</b> Contains ```Local``` and ```Remote``` procedures. More about this below.
  		
  	
  * <b>main</b> (UI & Network)
  	```ApplicationClass``` : Network layer. Initializes backend when application starts and also handles all communication
  	```Interfaces``` : Bridge layer. Interfaces to communicate between fragments
  	
  * <b>models</b> (Bridge)
  	All models or data representations used in the application and network
  	
  * <b>params</b>
  	```Local``` : Parameters specific to a device
  	```Global``` : Parameters related to the network and devices. Example: ```Remote method id```
  	
  * <b>testsuite</b>
  	This contains classes that generate random data for testing. This was particularly handy in developing components independently.
  	


Additional info on ```Procedures```

  * <b>Local</b> :
  	Used for actions occured in the current device. Updates actions into local database. Notifies all remote devices.
  
  * <b>Remote</b> :
  	Used for actions occured on some remote device. Receives changes, updates in local database and triggers UI refresh.
  

-----------------------------------------------------------------------------------------------------

## 6. Technologies used
This is a list of technologies used and the purpose they served

  * [Json](http://json.org/)
  	Data exchange format in the umundo network
  
  * [Google Gson](https://github.com/google/gson)
  	Conversion of objects to Json and viceversa
  
  * [Sugar ORM](https://github.com/satyan/sugar)
  	Objects database. Translates to RDBMS in the backend
  	
  * [Umundo](https://github.com/tklab-tud/umundo)
  	Backend for pubsub networking with all devices
  	
  * [Android v7 appcompact](https://developer.android.com/tools/support-library/features.html#v7-appcompat)
  	Backward compatibility of UI to pre 4.x devices
  	
  * [MaterialLibrary](https://github.com/rey5137/material/)
  	A library to bring material design UI to pre-Lolipop Android
  
  * [Material Design Icons](https://github.com/google/material-design-icons)
  	Well, the gorgeous icons
  	
  * [Material Colors](http://www.google.com/design/spec/style/color.html#color-color-palette)
  	For the pleasent color selection. Xml resources from [here](https://gist.github.com/daniellevass/b0b8cfa773488e138037)

-----------------------------------------------------------------------------------------------------

## 7. License

The source code is available under the Apache License V2.0

Refer License file for complete information.

© 2015, [Praveen Kumar Pendyala](https://github.com/praveendath92) and Contributors.


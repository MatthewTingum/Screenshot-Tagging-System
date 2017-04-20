SERVER INFORMATION:

Starting the Server

Install the latest Node.JS from https://nodejs.org/en/
Navigate to {Download Dir}\Server\ in a command prompt window
Enter “node server.js”

Starting the Database

Install the latest MongoDB from https://www.mongodb.com/
Navigate to to {MongoDB Install Dir}\bin in a command prompt window
Enter “mongod”

Accessing the Server

In a web browser navigate to “http://127.0.0.1:3000”
Here, make an account.  You need it to use the client application.

USING THE CLIENT:

Login Page:
1.	Make sure that you have made an account.
2.	Enter your login information and submit.

Main Menu:
1.	If you have had a session with World of Warcraft you would like to upload to the database, press the Upload button.
2.	If you want to browse your collected data, press the Search button.
3.	If you need help, press the help button.
4.	You can log out by pressing the login button.
5.	The file configuration button is not needed if you are using a windows computer and installed World of Warcraft in the default directory.  If this doesn’t apply to you, the first time you use this application, press this button and a window will be brought up for you to navigate the file system.  Please select the directory named ‘World of Warcraft’ in the location where the game is installed.
6.	The exit button will exit this application.

Search Screen:
1.	Select the category where you want to search data.
2.	Enter keyword to search.  If it is left blank, you will be able to browse all data
3.	Click Search to view search results.

ADDON:

Add SStagger folder to the ..\World of Warcraft\Interface\AddOns\ directory

Bind the addon to any key from the in-game key bindings menu. 


Output file is logged in a file called SStagger.lua in
World of Warcraft\WTF\Account\%WHATEVERYOURCHARNAMEIS%\SavedVariables
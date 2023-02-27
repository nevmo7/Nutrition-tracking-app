# Nutrition-tracking-app
Android app, created in android studio, to track nutrition intake.

The user starts the application on the home page where there are two buttons
Search button and bookmark button

if the user chooses search button the app will take the user to search activity
if the user chooses bookmark button the app will take the user to bookmark activity

In bookmark activity, all the food items the user has saved in the database
To save, the app uses firebase realtime database

In search activity, the user can enter a search term
On clicking the search button the user will be displayed with all the search results
provided by My fitness pal API (API endpoint: api.myfitnesspal.com/public/nutrition)

When the user clicks on onw of the search results the user will be displayed with the chart division of
nutritional contents (fat, carbohydrate and protein) in the food.

The user will be able to change the value of the volume to get nutritional content for the entered volume.

The user will also be able to save food items to the database by clicking on the save button

In the database a new child will be created with the value of the user id
The user id will be created when opening the app and saved to the local storage of the user
If there is already an user id given to the user this step will be skipped

In the new database entry a new food item will be created with the name and its nutritional content

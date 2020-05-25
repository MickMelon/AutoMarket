# Auto Market Mobile App
This was a simple Android app developed as part of a module for university. The app was developed in Java using Android Studio and was made to communicate with an external MySQL database in which an API was written for that in PHP.

The app is based on websites like Auto Trader where sellers can post adverts for their vehicles and buyers can browse all the adverts and contact the seller if they are interested. This makes use of Google Maps to automatically generate a path to the seller from the buyer's location.

The advert browser uses a `RecyclerView` that makes a list of all adverts, with each advert using a `CardView` layout. Uses `ViewModel` so data doesn't have to be fetched every time the orientation changes, which helps greatly with performance. The adverts are loaded from the database, with the images stored on a webserver. The code reads the images and converts them to `Bitmap` for them to be displayed on the app. The single advert page shows the advert image, description, price, and seller info. This also uses a `ViewModel` to handle orientation change. The seller info page uses a `MapView` to display the seller location. There are buttons for calling, sending email, visiting their website, and getting the route to the seller using Google's Direction API. There is a navigation drawer that works both vertically and horizontally. If the user is not logged in, it will only show the login and register options. Both of these options opens up a form that incorporates form validation to ensure that the user is entering legal data, but the data they have entered is further verified by the server so there is no funny business. When the user is logged in, the navigation drawer shows options for posting an advert, viewing profile, and logging out. The form for posting an advert also incorporates form validation and includes an image picker to allow the user to select an image from their gallery to be uesd on the advert. This image is then stored on the web server.

[Click here for a Video Demo](https://www.youtube.com/watch?v=obgQ-CZ0nDA)

## Technologies Used
- Java
- PHP
- MySQL
- MVC
- Android
- Composer

Number Guesser
=============================

This is a simple Android App built in [Android Kotlin](https://kotlinlang.org/docs/android-overview.html) that tracks the number of times a number input by the user matches the random number generated. 

The application also records the time in milliseconds between the matches of the same input number. 


## Building the Number Guesser App

First, clone the repo:

`git clone https://github.com/timndichu/NumberGuesserTask`


Building the sample then depends on your build tools.

### Android Studio (Recommended)

* Open Android Studio and select `File->Open...` or from the Android Launcher select `Import project (Eclipse ADT, Gradle, etc.)` and navigate to the root directory of the project.
* Select the directory or drill in and select the file `build.gradle` in the cloned repo.
* Click 'OK' to open the the project in Android Studio.
* A Gradle sync should start, but you can force a sync and build the 'app' module as needed.


## Running the Number Guesser App

### Downloading and Installing the apk file
You can find the [apk file here](./apk/number_guesser.apk)

### Android Studio
Connect an Android device to your development machine.
* Select `Run -> Run 'app'` (or `Debug 'app'`) from the menu bar
* Select the device you wish to run the app on and click 'OK'


## Using the Number Guesser App

On opening the application, to begin playing, click on `"Start Guessing"` button
<br>
The Text View and Button (`which were initially disabled`) will now be enabled for user interaction
<br>
A random number between 1 and 20 is generated and you have to try guess what that number is.

* If the number guessed is less than the random number, `"Number too low, try again"` is displayed to the user
* If the number guessed is greater than the random number, `"Number too high, try again"` is displayed to the user
* If the user correctly guesses the random number: 
    * `"Correct Number Guessed"` is displayed to the user;
    * The submit button and text input are disabled
    * The time elapsed to guess the number is displayed to the user
    * Number of times the user has guessed correctly is updated
    * The number guessed and the time elapsed is `stored in a Mutable List`
* To play again after guessing correctly, click on `"Play Again"` button that will appear at the top of the screen

## Architecture
The Number Guesser is a fairly simple app with only one Activity and one Class

### Classes and objects
The Number guesser only has one data class containing the number guessed and the time taken to guess it.
```kotlin
package com.timndichu.numberguesser

data class NumberGuessed(val number: Int, val timeElapsed: Int) 
```
This class is then instantiated within the MainActivity as an object.
<br>
On guessing correctly, the number guessed and the time taken are stored in this object and pushed to a Mutable List

## CI/CD using Docker (Optional, to download all tools)

Using an Android Dockerfile installs Android components, sdk, gradle, and after that, we can build our Android App, test, jacoco report, lint, etc. 

### Running the docker file
```
$docker build -t android-build:android-gradle
```
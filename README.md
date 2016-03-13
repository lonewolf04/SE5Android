# SE5Android
Android source codes (College Bus Tracker : SE5)

Project folder for the android side of the project.
There are two interfaces : driver and student
Each interface has a corresponding registration page and upon succesful registration
an authentication screen with otp field is displayed (not enabled).
For now all users are given successful authentication and the page shifts to the Google maps interface which
displays the corresponding route.
Any additions required are mentioned in the source files.

In order to run the project Android Studio must be installed.
(http://developer.android.com/sdk/index.html)

Choose the option to import a project on launching Android Studio.

The gradle will build.This will take a while.

One gradle has been successfully built the app can run either on emulator or an android device
If a phone is to be used follow the instructions here: http://developer.android.com/tools/device.html
If emulator is to be enabled click on the avd manager icon and set up the virtual device.
Once this is done download the HAXM intel installer and install it.
Link for emulator is provided below.
http://developer.android.com/tools/devices/emulator.html

The code involves server-client communication hence, internet connection is required.

An API key is required for google maps.
It is preferable to get a new key for the imported project.
Instructions on how to obtain the api key is provided in the google_maps_api.xml file.
When the key is obtained ,replace the existing key with the newly obtained one.

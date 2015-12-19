# OSGi - Test

*Prototype of a Scala-based, JavaFX app, powered by OSGi*


This application tries to combine, in a simple but useful way, several modern technologies:

* **OSGi** - the project only relies on standard APIs, so its bundles can equally work on *Equinox* (the reference implementation) or any other compatible product, such as *Apache Felix*

* **Scala**, whose core library is actually an OSGi bundle

* **JavaFX 8**, which is *not* an OSGi bundle, and therefore requires *dedicated exports* provided by the *system bundle*

* **Gradle**, to enjoy simple and elegant *build automation* and *compile-time version management*

This project was created as an example for the presentation [Introduction to OSGi](http://www.slideshare.net/giancosta86/introduction-to-osgi-56290394).


## Using the application

1. Start your browser and navigate to the [releases area](https://github.com/giancosta86/OSGi-Test/releases) of the GitHub project

2. If you have [MoonDeploy](https://github.com/giancosta86/moondeploy), just click on **App.moondeploy** file and open it; otherwise, download and extract the zip archive, then run a script from the **bin** sub-directory

3. A JavaFX window will appear: try clicking the *Show message* button as many times as you wish

4. Return to the [releases area](https://github.com/giancosta86/OSGi-Test/releases) and download *hotswap-message-new-1.5.jar*

5. Click the *Update message bundle...* button

6. Select the downloaded jar: OSGi will update the bundle without restarting the application

7. Try clicking *Show message* again: from now on, only the new message will be shown


## Subprojects

* **hello-osgi**: a basic, standalone example showing how to use a bundle with *exported* and *private* packages, as well as a simple *bundle activator*

* **hotswap-boot**: bootstrap module, provides the JavaFX application, starts the OSGi framework and registers the startup bundles

* **hotswap-gui**: OSGi bundle whose activator shows and hides the application's main window

* **hotswap-message**: OSGi bundle exporting a basic Scala object, whose package is imported by *hotswap-gui*

* **hotswap-message-new**: slightly modified version of the above bundle, to demonstrate OSGi's flexibility


The projects are all elegantly coordinated by Gradle - for example, the startup bundles are stored as resources of the *hotswap-boot* project, and they are provided:

* by the Maven Central repository (the Scala core)

* other subprojects (*hotswap-gui*, *hotswap-message*)

which is why dedicated relationship and dependency declarations have been introduced.


## Further references

* [Introduction to OSGi](http://www.slideshare.net/giancosta86/introduction-to-osgi-56290394), on SlideShare

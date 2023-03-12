Burp suite Extension common library
=============

Language/[日本語](Readme-ja.md)

This library is a collection of libraries often used to create extensions to PortSwigger Burp Suite, which is a product of PortSwigger.

## About the latest version

The main repository (master) may contain code under development.
Please download the stable release version from the following.

* https://github.com/raise-isayan/BurpExtensionCommons/releases

Please use the following versions

* Burp suite v2023.1.2 or less than
  * YagraExtender v2.2.14.0 or less than

* Burp suite v2023.1.2 or above
  * YagraExtender v3.0.0 or above (beta version)
  * YagraExtension v2.2.14.0 or less (currently available)

## How to use

Please import BurpExtensionCommons.jar in the release folder as a Java library and use it.

## Features

* API interface of Burp sute Extension
* ListModel and TableModel with movable Item position
* A useful wrapper for handling HTTP Requests and HTTP Responses

### Java
* JRE (JDK) 17 (Open JDK is recommended) (https://openjdk.java.net/)

### Burp suite
* v2023.1.2 or higher (http://www.portswigger.net/burp/)

### Development environment
* NetBean 16 (https://netbeans.apache.org/)
* Gradle 7.5 (https://gradle.org/)

## build
 gradlew release

## Use Library

* Google gson (https://github.com/google/gson)
  * Apache License
  * https://github.com/google/gson/blob/master/LICENSE

* juniversalchardet (https://code.google.com/archive/p/juniversalchardet/)
  *  Mozilla Public License 1.1
  * https://www.mozilla.org/en-US/MPL/


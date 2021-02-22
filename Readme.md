Burp suite Extension common library
=============

Language/[Japanese](Readme-ja.md)

This library is a collection of libraries often used to create extensions to PortSwigger Burp Suite, which is a product of PortSwigger.

## How to use

Please import BurpExtensionCommons.jar in the release folder as a Java library and use it.

## Features

* API interface of Burp sute Extension
* ListModel and TableModel with movable Item position
* A useful wrapper for handling HTTP Requests and HTTP Responses

## Java
* JRE (JDK) 11 (Open JDK is recommended) (https://openjdk.java.net/)

## Supported Versions
* Burp suite v2.1

## build
 gradlew release

## Use Library

* Google gson (https://github.com/google/gson)
  * Apache License
  * https://github.com/google/gson/blob/master/LICENSE

* juniversalchardet (https://code.google.com/archive/p/juniversalchardet/)
  *  Mozilla Public License 1.1
  * https://www.mozilla.org/en-US/MPL/

## development environment

* NetBean 12.2 (https://netbeans.apache.org/)
* Gradle 6.8.1 (https://gradle.org/)


Burp suite Extension 共通ライブラリ
=============

Language/[English](Readme-ja.md)

このライブラリは、PortSwigger社の製品であるBurp SuiteのExtensionを作成するためによく利用するライブラリをまとめたものになります。

## 使用方法

release フォルダの BurpExtensionCommons.jar をJavaのライブラリとしてインポートしてご利用ください。

## 機能

* Burp sute Extension のAPIインタフェース
* Item位置を移動可能なListModelおよびTableModel
* HTTP Request、HTTP Response を扱うのに便利なラッパー

## Java
* JRE(JDK) 11 以上(Open JDK を推奨)(https://openjdk.java.net/)

## 対応バージョン
* Burp suite v2020

## ビルド
 gradlew release

## 利用ライブラリ

* Google gson (https://github.com/google/gson)
  * Apache License
  * https://github.com/google/gson/blob/master/LICENSE

* juniversalchardet (https://code.google.com/archive/p/juniversalchardet/)
  *  Mozilla Public License 1.1
  * https://www.mozilla.org/en-US/MPL/

## 開発環境

* NetBean 12.2 (https://netbeans.apache.org/)
* Gradle 6.8.1 (https://gradle.org/)


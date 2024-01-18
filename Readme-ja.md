Burp suite Extension 共通ライブラリ
=============

Language/[English](Readme-ja.md)

このライブラリは、PortSwigger社の製品であるBurp SuiteのExtensionを作成するためによく利用するライブラリをまとめたものになります。

## 最新版について

メインのリポジトリ(master)には開発中のコードが含まれている場合があります。
安定したリリース版は､以下よりダウンロードしてください。

* https://github.com/raise-isayan/BurpExtensionCommons/releases

利用するバージョンは以下のものをご利用ください

* Burp suite v2023.1.2 より前のバージョン
   * BurpExtensionCommons v0.5.5.0 以前

* Burp suite v2023.1.2 より後のバージョン
   * BurpExtensionCommons v3.0.0 以降
   * BurpExtensionCommons v0.5.5.0 以前(現時点で利用可)

## 使用方法

release フォルダの BurpExtensionCommons.jar をJavaのライブラリとしてインポートしてご利用ください。

## 機能

* Burp sute Extension のAPIインタフェース
* Item位置を移動可能なListModelおよびTableModel
* HTTP Request、HTTP Response を扱うのに便利なラッパー

### Java
* JRE(JDK) 17 (Open JDK を推奨)(https://openjdk.java.net/)

### Burp suite
* v2023.1.2以上 (http://www.portswigger.net/burp/)

### 開発環境
* NetBean 20 (https://netbeans.apache.org/)
* Gradle 7.5 (https://gradle.org/)

## ビルド
 gradlew release

## 利用ライブラリ

* Google gson (https://github.com/google/gson)
  * Apache License
  * https://github.com/google/gson/blob/master/LICENSE

* juniversalchardet (https://code.google.com/archive/p/juniversalchardet/)
  *  Mozilla Public License 1.1
  * https://www.mozilla.org/en-US/MPL/


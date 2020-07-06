# GraphEditor
[![Latest Release](https://img.shields.io/github/release/Epictigu/GraphEditor?label=download)](https://github.com/Epictigu/GraphEditor/releases/)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

Der GraphEditor ist eine Erweiterung des GraphVisualization-Programmes.

Während es vorher nur möglich war bereits existierende Graphen in einem Fenster anzeigen zu lassen,
können diese nun auch nach Belieben manipuliert werden.

Im Folgenden werden die einzelnen besonderen Features des Editors vorgestellt.                                  
Für eine Grundvorstellung besuchen Sie bitte: <a href="https://github.com/Epictigu/GraphVisualization">GraphVisualization</a>

## Überarbeitete Dateiauswahl
![screenshot](https://s7.gifyu.com/images/tp3_02.gif)

Wie aus dem vorherigen Projekt bereits bekannt lassen sich über ein Dateiauswahlmenü vorerstellte ".gdi"-Dateien einlesen.
Zusätzlich dazu wurde nun eine ".gdip"-Datei mit eingebracht, welche automatisch beim Speichern erstellt wird.
Beim Einlesen hat der Nutzer die Möglichkeit diese mit dazu zu nehmen oder einzelne Parameter selber zu setzen.

Die zusätzliche Datei ermöglicht ohne Veränderung des Standardformates mehrere visuelle Funktionen mit einzuimplementieren.
Darunter wird zum Beispiel die Position der einzelnen Knoten, das Aussehen sowie die Größe abgespeichert.

Sollte ein Graph ohne ".gdip"-Datei eingelesen werden, was selbstverständlich standardmäßig vorkommt, kann es zu Positionsproblemen kommen.
Damit konnte die Limitierung an Knoten entfernt werden, wodurch nun alle Graphen einlesbar sein sollten.
Wenn der zur Verfügung stehende Platz nicht ausreicht, lassen sich die Knoten durch einen Slider auf der rechten Seite manuell verkleinern.
Außerdem steht natürlich wieder die Möglichkeit eines größeren Fensters zur Verfügung.

## Knoten/Kanten hinzufügen
![screenshot](https://s7.gifyu.com/images/tp3_01c4eb43b430ef51d7.gif)

Um einem grundlegenden Editor gerecht zu werden, wurden selbstverständlich die Standardfunktionen zum Hinzufügen eines Knotens oder einer Kante implementiert.
Dies geschieht über ein Auswählen der Option auf der linken Seite oder durch eine Nutzung der Tastenkombination <Alt + 1-6>.
Ein einfaches Klicken mittem im Fenster fügt einen Knoten, direkt nach der Angabe des Knotennamens, an der gewünschten Position hinzu.

Das Hinzufügen von Kanten funktioniert ähnlich. Nur da diese auf Knoten basieren ist es nicht möglich einfach irgendwo im Fenster eine neue Kante zu platzieren.
Stattdessen müssen nacheinander die beiden gewünschten Knoten angeklickt werden was die gewünschte Kante hinzufügt. Auch hier erscheint ein kleines Fenster in dem eine Kantenlänge angegeben werden muss, da jede Kante ihre eigens gespeicherte Länge besitzt.

Sollten Sie einmal nicht weiter wissen welcher Knopf im Editorfenster was macht, können Sie einfach über den gewünschten Knopf die Maustaste halten um einen Tooltip zu dem diesbezüglichen Knopf zu bekommen.

## Knoten/Kanten verschieben/bearbeiten
![screenshot](https://s7.gifyu.com/images/tp3_036ba811fb077a6452.gif)

Natürlich besteht auch weiterhin die Möglichkeit die einzelnen Knoten zu verschieben.
Direkt über den ersten Knopf im Editorfenster auf der rechten Seite lassen sich Knoten wie gewohnt über einen gedrückten linken Mausklick verschieben.

Als Zusatz ist es nun auch möglich über einen rechtsklick auf einen Knoten die einzelnen Knotendetails anzusehen und zu bearbeiten.
Zudem ist es dabei auch möglich einen nicht gewünschten Knoten wieder zu löschen.

Ähnlich funktioniert die Bearbeitung von Kanten (3. Knopf im Editorfenster), wobei wieder 2 Knoten ausgewählt werden um die Kante zwischen den beiden detailgerecht aufzurufen.
Auch hier ist es natürlich möglich eine Kante bei Bedarf zu löschen.

Um die einzelnen Kantenlängen angezeigt zu bekommen ist es zu empfehlen diese im Menüpunkt "Settings" einzuschalten.
Wem dies aber zu unübersichtlich ist, der hat die Möglichkeit eine genaue Auflistung von Knoten und Kanten im Menüpunkt "File->Datatable" angezeigt zu bekommen.

## Knoten zusammenlegen
![screenshot](https://s7.gifyu.com/images/tp3_04.gif)

Auf Wunsch haben wir ebenfalls eine Funktion der Zusammenlegung von Knoten mit eingebracht.
Wie bereits vom Kanten hinzufügen/auswählen werden dabei zwei einzelne Knoten vorher angeklickt.

Sollte der Nutzer eine Zusammenlegung der Knoten bestätigen werden alle Kanten der beiden Knoten auf einen einzelnen fokussiert, während der andere von den beiden entfernt wird.

## Breitensuche
![screenshot](https://s7.gifyu.com/images/tp3_05.gif)

Um eine genauere Entfernung von Knoten ohne Einbeziehung der spezifischen Länge zu ermöglichen, wurde die Breitensuche mit in das Programm implementiert.
Nachdem das Tool ausgewählt wurde, kann ein einzelner Knoten angeklickt werden um diesen als Startpunkt der Breitensuche auszuwählen.
Für alle Knoten die nicht mit dem Startknoten verbunden sind wird ein Unendlichkeits-Symbol eingetragen. 

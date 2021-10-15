# ProjectInfo901 - Réalisation d'un intergiciel

> Hugo Hersemeule & Tom Kubasik

Pour ce TP, nous avons décidé de réaliser une librairie Kotlin qui peut donc être utilisée pour une application Android native en Kotlin.

Afin d'illustrer le projet, nous avons aussi réalisé une application Android permettant à un utilisateur d'envoyer des messages privés, de broadcast un message à d'autres utilisateurs de l'application.
Il y a aussi un système de section critique avec un jeton envoyé en anneau. En effet, seul l'utilisateur qui a demandé la section critique va pouvoir envoyer des messages.
Ayant essayé de faire quelque de chose respectant les bonnes pratiques de l'android, nous avons manqué de temps pour implementer les autres fonctionnalités.

Le middleware se trouve dans le module "middlewareinfo901" et l'application dans "app" 

Nous avons réalisé une petite vidéo afin pour que vous puissiez observer facilement cette application.
[Lien youtube vers une démo de l'application](https://www.youtube.com/watch?v=IV7eBbZWWCY)

Afin de simuler le système de bus, nous avons réalisé un serveur node avec les websockets.
[Lien vers le repository du serveur node](https://github.com/Owydoo/node-server-info901/blob/master/index.js)

## 


<br />
<p align="center">
  <h3 align="center">Polyville active</h3>

  <p align="center">
  Groupe AL-IAM1
   <br />
  Bruel Martin, Lanoux Nicolas, Lecavelier Maëva, Striebel Florian
   <br />
    <br />
    Une application de découverte des magasins alentours
    <br />
    <a href="https://github.com/PNS-PS7and8/ps7-20-21-al-iam1/issues">Issues</a>
    ·
    <a href="https://github.com/PNS-PS7and8/ps7-20-21-al-iam1/projects/1">Backlog</a>
    ·
    <a href="#installation">Installation</a>
  </p>
  <p align="center">
</p>



## Sommaire

* [A propos du projet](#a-propos-du-projet)
* [Installation](#installation)
  * [Prérequis](#prérequis)
  * [Mise en place](#mise-en-place)
* [Contribution](#contribution)


## A propos du projet
Notre projet est un système de découverte de points d’intérêts (commerces, musées, cinéma, plage, etc…) et les personnes qui se trouvent autour de cet endroit.
Les utilisateurs peuvent connaître les points d’intérêt connecté sur le même réseau qu'eux grâce à une application mobile. Ils peuvent aussi recevoir des notifications à proximité des centres d’intérêt correspondant au contexte actuel (météo, horaire...). 
Une API est également mis à disposition afin de connaitre l'influence des magasins en fonction des jours.

## Installation
Téléchargez la dernière version depuis la liste des [releases ](https://github.com/PNS-PS7and8/ps7-20-21-al-iam1/releases).

### Prérequis  

 Mobile :
 
 - Android 10 (physique)
 - Android studio
 
 Store :
 
 - java 11
 - maven
 - bluetooth
 - docker

### Mise en place

 1. Cloner le repo
 
 ```sh
git clone https://github.com/PNS-PS7and8/ps7-20-21-al-iam1.git
cd ps7-20-21-al-iam1
```

 2. Démarrer l'API (linux / wsl2)
 
  ```sh
cd api
docker build -t ps7-20-21-al-iam1/api .
docker run -it -p 8080:8080 ps7-20-21-al-iam1/api
```

Après avoir lancer l'API, vous pouvez vous rendre sur ce [lien](#http://localhost:8080/swagger-ui/index.html) pour accéder à la **documentation**.

3. Vérification du lancement de l'API (test cucumber)

  ```sh
cd ../mock_api
mvn clean package
```

4. Démarrer le serveur du magasin  

Le **bluetooth** doit être activé aux lancement du serveur mais peut-être désactivé par la suite

  ```sh
cd ../store
mvn clean package
mvn exec:java -Dexec.mainClass="upnpService.Main"
```

5. Lancer l’application mobile

Pour la première fois, connectez votre téléphone à l'ordinateur et activez le mode debug. Compilez et lancez l'application depuis **Android studio**.
Pensez à donner l'autorisation de localisation à l'application.

## Contribution

Afin de bien organisé le projet chaque nouvelle fonctionnalité doivent respecter les contraintes suivantes :

1. Création d'une [Issue](#https://github.com/PNS-PS7and8/ps7-20-21-al-iam1/issues) associée à la fonctionnalité. Cette issue doit respecter la **Definition of ready**:
	
	-   Une description
	-   Des critères d’acceptation
	-   Un test d’acceptation (minimum en langage naturel ou un schéma)
	-   Une estimation en points
	-   Un tag Moscow

2. Création d'une branche depuis **develop**. Cette branche doit se nommer **feature/\<nomDeLaFeature>**

3. Lorsque que la fonctionnalité est prête, une **pull request** peut être créée depuis la branche vers develop, tous les membres doivent êtres ajouté en reviewer. Afin de valider l'issue, celle-ci doit respecter la **Definition of done**:

	-   Les tests d’acceptation passent
	-   La feature a été revue par un autre membre du groupe
	-   La User Story est intégrée sur la branche commune
	-   Les nouveaux et les anciens tests unitaires passent
	-   Le build complet passe sur la CI

4. La branche peut être merge et l'issue peut être fermée 

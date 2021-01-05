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
* [Ajouter une API météo](#implement-your-own-weather-api)
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

## Implement your own weather API
You can easily add your favorite weather API following these few lines.

### How does it work ?
![UML diagram](https://cdn.discordapp.com/attachments/793493027121266718/795706041341575178/Screenshot_from_2021-01-04_18-30-59.png)
[Click here for better quality](https://viewer.diagrams.net/?highlight=0000ff&edit=_blank&layers=1&nav=1&title=archtecture_polyville.drawio#R7VxtU9s4EP41mYEPZCw7tpOPeaPlLhAugev1vplEJLo6VsZWSuivv5Vl%2bVWEGCLa6ZhhSLTWSpb20frRrkzLGm72n0Jvu76mS%2by3TGO5b1mjlmlajovgg0uehcR0XVdIViFZChnKBHPyAydCI5HuyBJHhYqMUp%2bRbVG4oEGAF6wg88KQPhWrPVK/2OvWW%2bGKYL7w/Kr0C1mytZB2TTeTf8ZktZY9I6cnrmw8WTkZSbT2lvQpJ7LGLWsYUsrEt81%2biH0%2be3Je%2buOnxWx2O1gO7v/b4r9Wd5uofyEau6yjkg4hxAE7bdO2Kdr%2b7vm7ZMK%2bYBg4Dvu3Vy0%2bTMeHLgfy03uIWOiBmUxnlciTz3iG2LOc9uiJbHwvgNLgkQZsnlyxeBs%2bWQXwfQGjwSEIvuOQEbBYP7nA6BakizXxlxPvme74mCPmLb7J0mBNQ/IDmvV8uIRAAJdDloDPdAo15lwTxAZIQxxBnVs5kagkuvb2hYoTL2KJYEF939tG5CEeBlfceOGKBAPKGN0klZK5hOHgfQl9r5gOpXiClYjpBrPwGfSSVky7I9pJFuFFz0kw%2bZRButNzhGydg7NpJBW9ZBmt0sYzqMCXBC11kGNVkNMyYQaMbxjusg9f5iwkwQoQ0jKtfT%2benkEMEi6%2bn004vHIV70O/ULcEKJhSFhs6pN/wkPoUkDMKqEAY8f2SSILMx4/sRYhFW28BPU/iOqNOJpklM8hFFHQf/Xjhr8lyiQMOD8o85gkscMNvKQlYPMP2AH5hzodG227ZcONDKKOsDL%2b8esiGNOBLicSwwAC0Jxyxd2FIrOUqiKTndo6DTKejCzEdBWJKVvZJbD1hZemv0ZtMvAFj%2bTiz6R03%2begCVexuVe1uKWzsew/Yv6URYYTy9kNRt2T7n2Ze90iP0NVlXfsFfwAm8ftbcrakOz6ZpuF7jC8L4BSphAbnwhX0w9B7npCIyQfOhM967mlT9CUPO3hGgCt5pXEhkf7I2AWERUmHiR9yB3i/wFtuWpiY/rXnP9Jwg3nbY3mh5Y4qNyCfh598%2buD5yaPz7I/59Gb68B%2bnM6YBj5IttIrrj9Di9AmzY1rlOFOo3uHN9qCenKWSqhyW7Dpu3ngSpSGQxERd3rtaO%2b49tQODUlGt8fAndgHd41wAko739E7AqTiB6RYHeUp5HE9EDU98A0%2b0SjTRVtFE1FMgwtJGE13VYyH2wEWW2DiDus7AqU33VKY3tdG9bkP39JnXPnJla6N7vRfoXtHfn5VJV5FrCRdwFSxoGAI/uYc6ie5B1lXq47xSAagPbys6%2b07J8vzwdlRWVdJDrt/sSnXiWMFZVDh2dVEW%2bYisRsAGhDWMRTNjsd1jKIsMYn0MZXFQQ1n0%2bALHUIPhAGVRmV4bZXGq0fCGspzMvCrKojKvLsrivBSxLvh7PYyl2MVHEhb118Z1nRjbChqjwrY%2bGlMNro/3DAfLqGJsEPZ5YhVKDz7lDGIAooRiIEcULwnvP57/NdtIMgJzFD7/w%2bVt05Llr/xi20CpYCS5hig950u3OCQwYs6OhHBPmGjQTkpfc1eylnhBNiRGhJeVxO8xVASmgu7CBX6VspoGMC9Yl4cq2mpQ5FCAJJ8Jse8x8r14yyoYJM3dUhF0lSlAs0SUHKfEf8SwErV8HrjUktV9rSUx7kpLMS7TUb4DqtVMgV6ourDqilg13d8Fq9J2vxJW7TJWbRO1ZcCgLlwd22qjrpH%2boGLTrmm0DcPqpT8fC%2bVqvJsnPZL8RuX0BA52m%2bbkxEfuL5HRMYqA6aoSpUi1y0DaNpiKmPjQbPWNwl/T%2bDy9O9uQJHu58fYF2qhS%2bNKfXdfTuL6ajOppDD9fTSZfa%2bpM6/ZyORuP/x2/qBN6wYrnFAtJTLG5aVjue1juG3IKqtgM0rZDb5IKGu2LOkf6Rm1b9GpW4QKK6TM1O02QeoZMUnIrcrM8477irOgjjNiBFLbMh3b5f/Pbifhd4NBjuxCrtvjAM0r3KE48SC%2bVIwaNi9IIYVVCQQFhGes7OYYlv8hhOD3gOODnlYC5NTkFjZzvApklztfrVf2aaX0o53OVSQXuieLz4wfifa%2bdp%2bUn3g5el%2b7sheuKxEbjn96OTbd2jkOJxK4uBiUjkA2D0mFehI70NLoYlPtSkuOJsPXEY1nWAPzGeen8ffp4KrmIWJcGOd30wO4xuofyFUf1PQMfmalzj1lH%2b0/8nCmDNz1WNz5cXE3IxO8pNG7x1OumStuU66anjbYd8XZCLhy9JN6GBsu7NQla%2bSi0CD3LELXJbZQPSicRZJ4uMXMx5AujbVjuwUByGs/mdQ27lQtog6DTab0xoF2WtTQHrKWD%2bpUC1h2nFLA2eyUeeHRyxbHaTs9Jo9EyQCwb7qBu2%2bp0snC2XexHc7zaVb2koSHnwjGKTpt0QbnlkiVgfk7SRb4C%2byqG3Vd2Knwpy1iZBlxfIKOc65O3XhfYF%2bl7YbnMy1HYzZqSFenjY4T14Luaj3n5od1suk%2b56ba65U13p7rVQa4qWmxr23QrXz5oNt2/H7usnbdQIlHfprtJW2g0r2rTrbavLvMqX4ZInzxn5a3mebPET4%2bB6gZSiQFL1wayW4371zsHYlRQ0RwFOSFDQahXYiiOofAbjiot0NEGmmpaYH43nV3n6MCsf3WTK85vpl9yxeur%2bV3%2b6v3NzddceTiZ3o/mOcH9zWh8eXUzHuVkn6f5JvghkkIHk3xdcfwjL5gWrouDG3nBdDpqyM17YN2tnVFQg1jboYxuk1LQaV/FoQy1gWubF4rZv68Se/Dsv4BZ4/8B)

### Add you own API

 

 - Create your class which will be your API object. It should extend `WeatherAPI` class.
 
 -   It'll generate methods to implement (see comments on `WeatherAPI` to understand each methods)
 
 - You'll need to create your URL to make the HTTP call, for this, you can use the `StringUrlBuilder` and add methods to it if you need. For the two given cases, the `StringUrlBuilder` is used in the constructor to create the URL. 

> `buildURL` method is where the final ULR is made, you'll need to see you're weather API to see how you can make request to it. 



## Contribution

Afin de bien organiser le projet chaque nouvelle fonctionnalité doit respecter les contraintes suivantes :

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


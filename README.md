# Saisie

### Import dans Eclipse

#### Avec plugin Gradle

Le plugin Gradle Integration for Eclipse peut s'installer depuis le menu *Help > Eclipse Marketplace*
A partir de là, on peut directement importer le projet dans Eclipse depuis l'entrée *Import > Gradle Projet > Gradle project*
Démarrage du projet : Gradle run directement sur projet Library.

#### Sans plugin Gradle

Pour construire le projet Eclipse, lancer la commande suivante depuis un terminal :

```
docker run -ti --rm -v $(pwd):$(pwd) -v $HOME/.gradle:$HOME/.gradle -e GRADLE_USER_HOME=$HOME/.gradle -w $(pwd) java:8 sh -c './gradlew eclipse'
```
A partir de là, on peut directement importer le projet dans Eclipse depuis l'entrée *Import > General > Existing Projects Into Workspace*
Démarrage du projet : Click droit sur le fichier *Saisie.launch* à la racine du projet puis *Run As > Saisie*

### Connexion base de données

Les éléments de connexion par défaut sont définis dans le fichier src/main/resources/config.properties. Tous les éléments sont redéfinissables
en tant que variable d'env pour surcharger les valeurs contenues dans le fichier.

### Exécution des tests unitaires

Il faut disposer d'une base PG par exemple en lançant un conteneur Docker :
```
docker run -d --name=saisieTestDB -e DB_NAME=saisie 192.168.1.71:5000/inativ/db-jenkins
```

La commande suivante exécute les tests unitaires par Gradle :
```
docker run -ti --rm -v $(pwd):$(pwd) -v $HOME/.gradle:$HOME/.gradle --link saisieTestDB:saisieTestDB -e GRADLE_USER_HOME=$HOME/.gradle -w $(pwd) java:8 sh -c './gradlew test -i'
```

**Attention** Penser à bien supprimer le conteneur saisieTestDB précédemment créé.

### Vérifications des versions des dépendances

Pour ne pas avoir à vérifier manuellement la "fraîcheur" de nos dépendances, il existe un plugin Gradle testant automatiquement si nos deps sont à jour ou pas. Source : http://blog.octo.com/en/keep-your-gradle-dependencies-up-to-date-seamlessly/
Il suffit de lancer la commande suivante qui fournit un rapport sur la console:
```
docker run -ti --rm -v $(pwd):$(pwd) -v $HOME/.gradle:$HOME/.gradle -e GRADLE_USER_HOME=$HOME/.gradle -w $(pwd) java:8 sh -c './gradlew dependencyUpdates -Drevision=release'
```


# Craftmania

Craftmania est un jeu de gestion et de crafting dans lequel le joueur collecte des ressources, fabrique des objets et automatise sa production grâce à différentes machines.

---

# Prérequis

* **Java 21** ou une version supérieure
* **Gradle** (inclus via le wrapper `gradlew`)

Vérifier la version de Java :

```bash
java -version
```

---

# Lancer le jeu

Exécuter la commande suivante à la racine du projet :

```bash
./gradlew run
```

---

# Comment jouer

## Menu principal

Depuis le menu principal, il est possible de :

* **Lancer une nouvelle partie**
* **Charger une sauvegarde existante**
* **Modifier les paramètres du jeu**

⚠️ Lancer une nouvelle partie **n'écrase pas les sauvegardes existantes** tant que vous n’effectuez pas une sauvegarde par la suite.

---

# Déplacements

Deux méthodes sont disponibles :

* Utiliser les touches **ZQSD**
* **Cliquer sur une case vide** pour que le joueur s’y déplace automatiquement

Pour interagir avec un élément de la carte, le joueur doit être **adjacent** à celui-ci puis **cliquer dessus**.

---

# Interactions avec les éléments de la carte

Selon l’objet sélectionné, différentes actions sont possibles :

### Ressources

* **Récolter** la ressource

### Marché

* Ouvre une interface permettant **d’acheter ou de vendre des ressources**

### Machines

* Ouvre **l’interface de la machine**

---

# Barre supérieure

La barre supérieure permet d’accéder rapidement à certaines actions :

* **Quit** : revenir au menu principal
* **Save** : sauvegarder la partie
* **Inventory** : ouvrir l’inventaire du joueur

---

# Inventaire et Crafting

L’inventaire affiche toutes les ressources possédées par le joueur.

Dans la **zone inférieure de l’interface**, il est possible de :

- Sélectionner une **recette** ce qui lance sa fabrication

La fabrication :

* **consomme les ressources nécessaires**
* **produit la ressource cible**

---

# Les Machines

Il existe **deux types de machines** :

* **Usines**
* **Récolteuses**

Chaque machine existe en plusieurs variantes :

* **Simple** : version de base
* **Fast** : production plus rapide
* **XL** : capacité de stockage plus grande
* **Weak** : fragile mais peu coûteuse, il y a une chance sur huit qu'elle se casse et il faut aller la réparer dans son interface
* **Poly** : configurable plusieurs fois

---

## Usines

Les **usines** permettent d’**automatiser les crafts du joueur**.

Fonctionnement :

1. choisir une **recette** dans l’interface de configuration
2. déposer les **ressources nécessaires** dans la machine
3. la production se lance **automatiquement**

On peut **re-déposer les ressources** autant de fois que nécéssaire mais 
⚠️ si elle n'est pas de type **Poly** la configuration ne peut se faire que une fois

---

## Récolteuses

Les **récolteuses** automatisent la **collecte de ressources**.

Fonctionnement :

1. placer la machine **à proximité d’une ressource**
2. sélectionner dans l’interface **la ressource à récolter**
3. la machine récoltera automatiquement la ressource

---

# Détails d’implémentation

Le projet utilise une architecture **MVC (Model-View-Controller)**. 

## Design Patterns utilisés

* **Static Factory**
  Utilisé pour les classes possédant des constructeurs avec arguments.

* **Abstract Factory**
  Utilisé pour la création des **Factories** et **Harvesters**.

* **Observer / Observable**
  Permet la communication et la propagation de changements entre certaines classes.

---

# Utilisation des Enums

Les `enum` sont utilisés pour plusieurs rôles importants :

### Asset Loader

Certaines `enum` servent de **chargeur d’assets** (`views/utilities`).

Au démarrage du jeu :

* les images sont **chargées en mémoire**
* les classes d’affichage peuvent ensuite les **réutiliser facilement**

---

### Ressources

Définition des ressources du jeu :

```
models/resources/ResourceEnum.java
```

---

### Recettes

Définition des recettes de crafting :

```
models/resources/RecipeEnum.java
```


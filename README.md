# Projet "Persistance" - M1 MIAGE · Patrons et Composants


## Auteurs (Groupe 2 - 4)
- Quentin FOURNIER
- Emmanuel GUEISSAZ

## Prérequis
Pour pouvoir exécuter ce projet, vous aurez besoin de :
- Un environnement de développement Java
- JDK 17 installé sur votre ordinateur

## Installation
1. Téléchargez ou clonez ce dépôt sur votre ordinateur
2. Ouvrez le projet dans votre environnement de développement Java
__3. Ajouter les libraire (.jar) __: 
     3.1 Eclipse :
      3.1.1 clique droit sur le projet 
      3.1.2 Sélectionner Properties
      3.1.3 Sélectionner "Java build Path"
      3.1.4 selectionner "Classpath" dans l'onglet "librarie"
      3.1.5 Cliquer sur "Add Jars" 
      3.1.6 Selectionner les deux librairies dans le dossier "lib" du projet
      3.1.7 Valider
      3.1.8 Cliquer sur "Apply"
3. Exécutez le fichier App.java pour lancer l'application

## Patrons
### Simple factory
Pour ce projet nous avons utilisé le patron Simply Factory. Celui-ci permet de détacher du code un “switch case” qui permet de créer un objet Circle, Square ou Triangle. L’avantage de ce patron est qu’il va éviter la répétition de code et si l’on souhaite ajouter une nouvelle forme, il suffira d’ajouter la nouvelle forme (class correspondante) dans la factory.

#### Diagramme de séquence
<img width="609" alt="image" src="https://user-images.githubusercontent.com/94804326/210050000-6c7d0f89-efd0-4a7c-a8d5-f4bede70044f.png">

#### Diagramme de classe
<img width="593" alt="image" src="https://user-images.githubusercontent.com/94804326/210050113-b2669298-916b-4b4f-979c-d7ea38f3cc47.png">


### Composite
Le groupement des formes n’est pas forcément très intuitif. Pour se faire, il faut cliquer sur le
bouton “groupement” <img width="43" alt="image" src="https://user-images.githubusercontent.com/94804326/210050383-2e8c991c-3adf-4529-b439-e9037e01b8e0.png">, puis sélectionner une à une les formes à grouper. À la fin de la sélection, il suffit de cliquer sur le bouton et les formes associées resteront groupées. Si une forme appartenait déjà à un groupe, elle est simplement déplacée vers le nouveau groupe. Pour qu’une forme n'appartienne plus à aucun groupe, il suffit donc de la sélectionner seule.

#### Diagramme de classe
<img width="399" alt="image" src="https://user-images.githubusercontent.com/94804326/210050286-33825eb5-6231-49fe-81ae-22f3219c437a.png">

#### Diagramme objet
<img width="148" alt="image" src="https://user-images.githubusercontent.com/94804326/210050317-994b6bf4-d23f-4fb7-928d-82c3a7efd8df.png">


### Visitor
Pour l’export, le visiteur est plutôt pratique, en effet si l’on souhaite ajouter une nouvelle forme dans notre application, il suffira d’implémenter visitor est d’ajouter la méthode visit(nouvelleForme étoile) afin d’ajouter la possibilité d’export un nouveau type de forme.

#### Diagramme de séquence
<img width="564" alt="image" src="https://user-images.githubusercontent.com/94804326/210050498-233bbdaf-763e-4aa2-a2a6-d70f4d44cda8.png">

#### Diagramme de classe
<img width="630" alt="image" src="https://user-images.githubusercontent.com/94804326/210050524-07f2a6c9-5100-4aad-aef3-f108deb3d743.png">


### Command
Le manque de temps ne nous a pas permis de terminer correctement la partie Undo/Redo de l’interface. Le patron Command n’a donc pas été implémenté. Le undo et le redo laissent régulièrement apparaître des formes fantômes.
Le plus simple que nous ayons trouvé pour implémenter cette fonctionnalité est d’enregistrer les formes (extendant JLabel) dans une liste à chaque modification de la page.

### Conclusion patrons
L’utilisation des patrons dans un code permet d’augmenter la maintenabilité de celui- ci. Cependant, certains patrons comme le visiteur, s’ils ne sont pas connus par la personne qui maintient le code, rendent la relecture plus fastidieuse.

## Composants
Cette partie consiste à créer un composant, puis à l'échanger avec un autre groupe.

### Groupe binôme
L'autre groupe est composé de :
 - Albane MAUBLANC
 - Mériam BARKAOUI

### Diagramme de class complet avec nouvelle forme 
![exported_from_idea drawio (1)](https://user-images.githubusercontent.com/69394186/210080355-02529577-fefe-4f97-a820-884ee595dda9.png)


### Contenu du package :
 - Les formes (Square.java, Triangle.java...)
 - Les interfaces Visiteurs et l'interface SimpleShape

### Difficultés rencontrées lors de l'utilisation de l'autre package
 - Utilisation d'une autre méthode pour représenter les formes qui nécessite une nouvelle approche de représentation :
    - Utilisation de graphics2d pour leur part.
    - Utilisation de jLabel pour notre part.
 - Le package met en place une gestion différente des groupes, qui permet par exemple d'avoir des groupes de groupe à une profondeur infini. Nous avions fait le choix de ne pas pouvoir faire de groupes de groupes et de différentier chaque forme pour notre part puisque le résultat est le même pour l'utilisateur. Celui-ci ne voit qu'un groupe bouger.
    - L'import et l'export changent en conséquence.
    
### Diagramme de composant
<img width="434" alt="image" src="https://user-images.githubusercontent.com/94804326/210079355-c9f0452e-539c-4b6f-bef1-e0a87b2c5c16.png">



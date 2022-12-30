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
Cette partie consiste à crée un composant, puis à l'échanger avec un autre groupe.

### Groupe binome
L'autre groupe est composé de :
  - Albane 
  - Mériam

Le composant utlisé contient les formes (Square.java, Triangle.java...) 

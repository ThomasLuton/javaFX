# Event Planner
Event planner est une application qui permet la gestion d'évenements, 
grâce à Event Planner on peut créer des évenements ou réserver et payer une place d'un évenement
## Création de compte
Pour créer un compte, il faut :
* une adresse email valide
* un nom (attention le nom doit correspondre à celui du payeur)
* une mot de passe contenant 8 caractères, au moins 1 nombre et au moins 1 caractère spécial
* choisir si l'on veut être organisateur ou client
## Client
Le client peut :
* Réserver une place à un évenement
* Payer les places qu'il a réservé
* Annuler une réservation sauf si l'évenement a lieu dans les 24h
## Organisateur :
L'organisateur peut :
* Créer un évenement de type Concert, Spectacle ou Conférence
* Pour chaque évenement, il peut créer autant de catégories que nécessaire
* Il a également accès à une vue détaillée de ses évenements

# Installation

## Base de données
L'application utilise une base de données hébergée sur PostgreSQL.

Pour l'initialiser, il faut inserer le fichier schema.ddl.sql disponible dans /resources

Si besoin, il est possible de changer l'url, l'utilisateur et le mot de passe pour accèder à la base dans la classe DataBaseConnection
## Démarrage
* Faire un maven clean install
* Run PlannerApplication

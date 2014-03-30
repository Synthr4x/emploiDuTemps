emploiDuTemps
=============

Mini projet java utilisant des composants Swing, et JCalendar afin d'organiser intelligemment un emploi du temps.

Utilisation du plugin JCalendar pour le choix ergonomique des dates a réserver sur l'emploi du temps en tant que professeur avec 4 types de réservations ; amphithéatre/TD/TP/réunion répartis sur 4 créneaux différents.
L'attribution de la salle ce fait dynamiquement pour éviter tout conflit et n'est plus possible s'il n'y a pas de salles disponible à ce créneau pour le type demandé.
Il est possible de supprimer une réservation à une date.

Sérialisation et désérialisation XML et en byteCode java de l'emploi du temps.

Une version évoluée a ensuite été développée avec une gestion des utilisateurs prenant celle-ci comme base, avec 2 types d'utilisatuers différents ; professeurs et étudiants.
Les étudiants ne peuvent que consulter l'emploi du temps, et les professeurs ont plusieurs niveaux ; responsable de matière, encadrant de TP/TD, et chef de département/directeur.
le responsable de matière peut gérer touts les cours en rapport avec sa matière (ajout/suppression)
l'encadrant de TP/TD ne peut que ajouter des créneaux pour des TP/TD.
et le chef de département/directeur peut administrer toutes les matières de l'emploi du temps

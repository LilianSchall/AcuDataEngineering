# Projet de Data Engineering: ACU Unité Spéciale

<div align="center">
    <a href="https://github.com/LilianSchall/DataEngineeringGp6">
        <img src="images/architecture.png" alt="Architecture" width="500"
        height="250">
    </a>
</div>

## Définition du contexte métier

La piscine de C est une épreuve phare de l'EPITA. Suite à son rayonnement et son succès, cette épreuve devient un examen national se déroulant sur l'ensemble du territoire français.

Les étudiants passant cet examen réalisent des exercices depuis leur ordinateur portable et transmettent leur réalisation à un service tier (nommé la moulinette). Une fois leurs résultats obtenus, les ordinateurs portables de ces étudiants émettent à nos services les données suivantes:
- La date d'envoi (nommée le timestamp)
- L'identifiant de l’étudiant
- Sa position géographique (*id est* sa latitude et longitude)
- L'identifiant de l'exercice
- Le résultat (en %) obtenu par l’étudiant sur l'exercice reference (nommée la métrique)

En fonction du pourcentage de réussite par l’étudiant, une patrouille spéciale d'Assistants C/UNIX (surnommés les ACUs) est déployée sur le site de l’échec (*id est* la position de l’étudiant) pour "corriger" l’étudiant et le remettre sur le **droit** chemin de la réussite scolaire.

## Description de l'architecture des services


## Contributeurs

Ce projet est mené par le groupe 6 de la promotion 2025 SCIA a l'EPITA.
Les membres du groupe sont les suivants:
- [Lilian Schall](https://github.com/LilianSchall) <lilian.schall@epita.fr> 
- [Guillaume Lalire](https://github.com/GuillaumeLalire) <guillaume.lalire@epita.fr>
- [Julien Schaffauser](https://github.com/JulienSchaff) <julien.schaffauser@epita.fr>
- [Emile Merle](https://github.com/Echidori) <emile.merle@epita.fr>

# Sans protection

## Principe du challenge
Challenge de pwn niveau débutant consitant à l'exploitation d'un buffer overflow pour se faire un shell sur un binaire sans aucune protection à l'exception de l'ASLR.

## Niveau du challenge et période 
Challenge de niveau easy. Période 82-22.

## Scénario CTFd
Agent, maintenant que vous avez commencé à faire vos preuves, il est temps de vous envoyer sur des missions plus délicates. Avant cela, nous allons tester vos capacités à vous créer un accès à distance sur une machine faisant tourner un binaire faiblement protégé. Montrez nous une fois de plus que vous êtes un allié de confiance.

Auteur: `Soremo`

## Commande de compilation 
 "gcc -o data/fragile fragile.c -no-pie -fno-stack-protector -z execstack"


## Auteur du challenge
Discord : アレックス (Alexandre)#9212 
Pseudo : Soremo

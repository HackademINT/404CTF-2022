# Un agent compromis
You need to find in the capture-réseau.pcapng the following things

1) The exfiltration script used
2) The file that have been exfiltrated
3) The flag that is in one of those files

The network was unstable during the network capture. Some packets may have been lost.

1/3
```
Nous avons surpris un de nos agents en train d'envoyer des fichiers confidentiels depuis son ordinateur dans nos locaux vers Hallebarde. Malheureusement, il a eu le temps de finir l'exfiltration et de supprimer les fichiers en question avant que nous l'arrêtions.

Heureusement, nous surveillons ce qu'il se passe sur notre réseau et nous avons donc une capture réseau de l'activité de son ordinateur. Retrouvez le fichier qu'il a téléchargé pour exfiltrer nos fichiers confidentiels.

Auteur : Typhlos#9037
```

2/3
```
Maintenant, nous avons besoin de savoir quels fichiers il a exfiltré.

Format du flag : 404CTF{fichier1,fichier2,fichier3,...} Le nom des fichiers doit être mis par ordre alphabétique.

Auteur : Typhlos#9037
```

3/3
```
Il semblerait que l'agent compromis a effacé toutes les sauvegardes des fichiers qu'il a exfiltré. Récupérez le contenu des fichiers.

Le réseau était un peu instable lors de la capture, des trames ont pu être perdues.

Auteur : Typhlos#9037
```
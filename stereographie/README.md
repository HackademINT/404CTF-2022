# Stereographie

Vous pouvez changer le flag avec `construct.py`

Les coefficients de Fourier peuvent vous servir à refaire les .wav au cas où.
Pour cela vous pouvez vous aider du fichier `coefs_to_function.py` pour avoir la fonction qui à un certain `t` associe `(x+yj)` (où `j**2 = -1`)
Cette fonction sera 1-périodique (modulo les erreurs de précision bien sûr).
Il suffit ensuite de mettre la partie réelle et la partie imaginaire (`x` et `y`) de ce résultat dans les canaux L/R d'un wav.

Une solution possible du challenge est donnée avec `solution.py`, sinon vous pouvez utiliser un oscilloscope (physique ou logiciel, par exemple celui là qui fonctionne très bien : https://github.com/kritzikratzi/Oscilloscope/ ).

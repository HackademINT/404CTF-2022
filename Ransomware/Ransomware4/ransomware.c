/* 
 * Bonjour, agent ! 
 * Nous avons réussi à reconstituer ce code depuis le binaire du ransomware qui se trouvait sur nos machines. 
 * Hélas, la clé de chiffrement est aléatoire et le département crypto dit que c'est sans espoir.
 * Vous pensez pouvoir faire quelque chose ?
 * Quelques informations : le ransomware tournait sur nos machines qui possèdent une architecture AMD x86_64, avec un système d'exploitation Linux. 
 * Aussi, nous n'avons pas réussi à récupérer le seed utilisé, donc nous avons fait en sorte que vous puissiez le choisir pour expérimenter.
 * Bonne chance !
 */

#include <stdio.h>
#include <stdlib.h>
 
// Code récupéré du ransomware Hackllebarde
int main(int argc, char** argv)
{
	// Cette partie du code a été rajoutée pour remplacer le seed qui a été perdu
	if (argc != 2) {
		perror("Nombre d'arguments invalide !");
		exit(1);
	}
	// peut échouer, mettez les bons arguments !
	int seed = strtol(argv[1], NULL, 10);
	// à partir de ce point, tout le code est celui récupéré et reconstitué du ransomware.
	// (excepté les commentaires)
	char array[8];
 	initstate(seed, array, 27);
	FILE* file = fopen("./flag.pdf", "rb");
	FILE* encryptedfile = fopen("./flag.pdf.enc", "wb");
	if (file == NULL || encryptedfile == NULL) {
		perror("Files cannot be opened ! Hackllebarde ransomware have failed :-(");
		exit(1);
	}
	int key, len;
	char data[4];
	char* keychar;
	while ((len = fread(&data, sizeof(char), 4, file)) == 4) {
		// on ne peut rien faire contre une clé 100% aléatoire !!!
		key = rand();
		keychar = (char*)&key;
		for(int i=0; i<len; i++) {
			data[i] ^= keychar[i];
		}
		fwrite(&data, sizeof(char), 4, encryptedfile);
	}
	fclose(file);
	fclose(encryptedfile);
	puts("Hackllebarde ransomware is a success ! :-D");
	return(0);
}	

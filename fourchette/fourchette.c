#include<stdlib.h>
#include<string.h>
#include<stdbool.h>
#include<stdio.h>
#include<unistd.h>
#include<sys/wait.h>
#include<signal.h>
#include<time.h>

// gcc test.c -o test -s

void success(int parent_pid, int child_pid, unsigned long long timestamp) {
    puts("Succès !");
    // do some stuff with the pid to get the flag
	// key is timestamp[:5] + pid + timestamp[5:] completing with 0
	// so key is [0]18868 [0]919 [0]72174
	// or 0xab9ab3283dae
	char* key = calloc(8, 1);
	long long part1 = (timestamp / 100000) * 10000000000;
	long long part2 = child_pid * 1000000;
	long long part3 = timestamp - (part1 / 100000);
	// printf("part 1 : %lld\npart 2 : %lld\npart3 : %lld\n", part1, part2, part3);
	long long key_int = part1 + part2 + part3;
	// printf("int : %llx\n", key_int);
	for (int i=0; i<6; i++) {
		key[i] = (key_int >> (8 * (5 - i))) & 0x0000000000ff; 
	}
	/* debug
	for (int i=0; i<6; i++) {
		printf("%x-", key[i]);
	} */
	char flag[] = "\xff\xbd\xd2\x5b\x1d\xcd\xd9\xef\x93\x45\x59\xdc\xd9\xe8\xb3\x1c\xd\x9a\xe8\xce\xf5\x53\x6e\xd7\xe6\xea\x87\x77\x51\x9d\xf8\xc5\xd5\x18\x4f\xe5\xd8\xc5\xfd\x18\x53\x91\xd6";
	// decrypt 
	for (int i=0; i<strlen(flag); i++) {
		// xor an even number of times (447)
		for (int j=0; j<1341; j+=3) {
			flag[i] ^= key[i % 6] & 0xff;
		}
	}
    printf("Flag : %s\n", flag);
	//puts("ok");
	free(key);
	sleep(1);
    // kill the parent, childs without the good PID will die alone 
    kill(parent_pid, SIGKILL);
}

int bizarre() {
	int* nothing = NULL;
	for (int i=0; i<24; i++) {
		int* nothing = malloc(sizeof(int));
		memcpy(nothing, rand(), sizeof(int));
		free(nothing);
	}
	return nothing;
}

int build_target(int argc, char** argv, int pid) {
	// return 919 no matter what...
	int result = rand();
	for (int i=1; i<argc; i++) {
		if (atoi(argv[i]) == pid) {
 			result = atoi(argv[i % (2 * i)]);
		}
	}
	// do some weird stuff to get 919 (919 is the 157th prime number)
	// 919 = 1337 - 418 ;-) 
	bool supprimes[1000];

  	supprimes[0] = true;   // 0 n'est pas premier
  	supprimes[1] = true;   // 1 n'est pas premier
  	for (int i=2; i<1000; i++) {
    	supprimes[i] = false;
	}
  	for (int i=2; i<33; i++) {
    	if (!supprimes[i]) {
        	int multiple = 2 * i;
        	while (multiple < 1000) {
          		supprimes[multiple] = true;
          		multiple += i;
        	}
    	}
	}
	int save = result;
	int compteur = 0;
	int target = 157;
	for (int i=0; i<1000; i++) {
		if (!supprimes[i]) {
			compteur++;
		}
		if (compteur == target && result == save) {
			result = i;
		}
	}
	return result;
}

unsigned long long get_timestamp() {
	// utilise un overflow avec un fichier texte pour écrire le timestamp cible
	// timestamp : 1886872174
	FILE* ts = fopen("inutile.txt", "r");
	if (ts == NULL) {
		puts("inutile.txt non trouvé, quel dommage !! ;-)");
		exit(1);
	}
	unsigned long long timestamp = 0;
	char data[512] = {0};
	for(int i=0; i<52; i++) {
		fread(&data[10*i], 1, 10, ts);
	}
	//fread(data, 1, 530, ts);
	return timestamp;
}

int main(int argc, char** argv) {
    int parent_pid = getpid();
	int target = build_target(argc, argv, parent_pid);
	int child_pid = rand();
	int timestamp = rand();
    int pid = child_pid % 7 + 19;
    while (1) {
        pid = fork();
	//	printf("%d\n", getpid());
        if (pid == 0) {
			int current_time = time(NULL);
			timestamp = get_timestamp();
			child_pid = getpid();
            if (child_pid != target || timestamp != current_time) {
                puts("Echec...");
				sleep(1);
				int status = rand();
				for (int i=0; i<100000; i++) {
					status = bizarre();
				}
                exit(status);
            }
            else {
                break;
            }
        }
    }
    success(parent_pid, child_pid, timestamp);
}

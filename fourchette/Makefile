CC=clang
CFLAGS=-Wall -s -fno-stack-protector -Wl,-z,relro,-z,now
DFLAGS=-Wall -ggdb -fno-stack-protector -Wl,-z,relro,-z,now

build:
	$(CC) fourchette.c -o fourchette $(CFLAGS)
debug:
	$(CC) fourchette.c -o fourchette $(DFLAGS)
clean:
	rm -f fourchette

CC = gcc
CFLAGS = -Wall -fPIC
LDFLAGS = -shared
LIBRARY_NAME = libTwisk.so

all: client libTwisk.so main

client: client.c
	$(CC) $(CFLAGS) -c client.c -o client.o

libTwisk.so: programmeC.o client.o
	$(CC) $(LDFLAGS) programmeC.o client.o -o $(LIBRARY_NAME)

main: main.c
	$(CC) $(CFLAGS) -L. main.c -o main -lTwisk

clean:
	rm -f client.o libTwisk.so main

CC = gcc
CFLAGS = -Wall -Werror
LIBRARY = libTwisk.so
CLIENT_SOURCE = client.c
CLIENT_OBJECT = client.o

all: $(LIBRARY)

$(CLIENT_OBJECT):
	$(CC) $(CFLAGS) -fPIC -c -o $(CLIENT_OBJECT) $(CLIENT_SOURCE)

$(LIBRARY): $(CLIENT_OBJECT)
	$(CC) $(CFLAGS) -shared -o $(LIBRARY) $(CLIENT_OBJECT)

clean:
	rm -f $(CLIENT_OBJECT) $(LIBRARY)

.PHONY: all clean

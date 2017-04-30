JCC=javac
FILES=$(wildcard *.java)
TARGET=SameGame.java

all: $(FILES)
	$(JCC) $(TARGET)

test:
	java SameGame

clean :
	rm *.class
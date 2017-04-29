JCC=javac
FILES=$(wildcard *.java)
TARGET=SameGame.java

all: $(FILES)
	$(JCC) $(TARGET)

clean :
	rm *.class
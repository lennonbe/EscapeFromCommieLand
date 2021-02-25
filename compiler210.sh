echo "Compiling code"
javac -cp jsfml/jsfml.jar -d . *.java
echo "Running the code"
java -cp ./:jsfml/jsfml.jar BoringGame.Driver
echo "Done"




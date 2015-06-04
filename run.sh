#!/bin/bash


mkdir -p generated-facts
mkdir -p analysis-logic
mkdir -p queries
mkdir -p optimized-spiglet
inputFile="$1"
generatedFactsDir="./generated-facts/"
analysisLogicDir="./analysis-logic/"
queriesDir="./queries/"
optimizedSpiglet="./optimized-spiglet/"
optLevel="$2"

java -jar iso-0.1-SNAPSHOT.jar $inputFile $generatedFactsDir $analysisLogicDir $queriesDir $optimizedSpiglet $optLevel

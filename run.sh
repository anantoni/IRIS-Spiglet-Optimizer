#!/bin/bash

inputFile="$1"
generatedFactsDir="./generated-facts/"
analysisLogicDir="./analysis-logic/"
queriesDir="./queries/"
optimizedSpiglet="./optimized-spiglet/"
optLevel="$2"

java -jar target/iso-0.1-SNAPSHOT.jar $inputFile $generatedFactsDir $analysisLogicDir $queriesDir $optimizedSpiglet $optLevel
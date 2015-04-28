#/bin/bash

function outputBuildComment() {
 echo "************************************"
 echo ""
 echo " " $1
 echo ""
 echo "************************************"
}

function run_assembly() {

        clean

        outputBuildComment "Building assembly"
        sbt assembly

        outputBuildComment "Running assembly"
        java -jar target/service-rural-house.jar server RuralHouseServiceConfiguration.yml
}

function run_dev() {
        outputBuildComment "Running dev"
        sbt "run server RuralHouseServiceConfiguration.yml"
}



REPO_DIR="$( cd "$( dirname "${BASH_SOURCE:-$0}" )" && pwd )"
cd $REPO_DIR

if [ "$1" = "-a" ]; then
        run_assembly
else
	run_dev
fi

#!/bin/bash

java -cp ./lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:.dbFiles/liga_siatkowki --dbname.0 liga_siatkowki

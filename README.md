Programs in this repo use scala/spark. Following are general instructions to install/run the programs. If any program require special instructions/Please refere to README.md files specific to those programs.

* Steps for installing Spark on local desktop

1. Download Apache Spark :http://www.apache.org/dyn/closer.lua/spark/spark-1.6.0/spark-1.6.0-bin-hadoop2.6.tgz
2. Create a directory called myspark in your home directory mkdir ~/myspark160
3. Copy the Spark distribution file to myspark160	
	cd ~/myspark160
	mv ~/Downloads/spark-1.6.0-bin-hadoop2.6.tgz  ~/myspark160
3. Extract the Spark distribution file	 
	tar -xzvf spark-1.6.0-bin-hadoop2.6.tgz	you should see directory spark-1.6.0-bin-hadoop2.6
4. Start Spark shell in local mode	 cd spark-1.6.0-bin-hadoop2.6
  ./bin/spark-shell	 Should see scala> prompt at the end


* Steps for installing Spark notebook:

1. You can download it from http://spark-notebook.io/ or follow this link - http://spark-notebook.io/dl/zip/0.6.2/2.11/1.6.0/2.7.1/true/true.
2. Move the zip file into ~/~/myspark160 and unzip it
3. Since the unzipped folder name is kind of long, you may want to rename it to something shorter like sparknb
4. Go into "sparknb" folder
5. To startup spark-notebook server, the command ./bin/spark-notebook
	For Windows users, if the above command didn't work, you may want to try 
	    ./bin/spark-notebook -Dmanager.tachyon.enabled=false
6. Point your browser to http://localhost:9000

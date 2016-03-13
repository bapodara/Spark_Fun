This project does sentiment analysis using twitter streaming API and spark streaming.
It ignores the words included into "stop-words.txt" file in resources directory. All the positivewords and negative words are included into pos-words.txt and neg-words.txt filein resources folder. The program simply counts positive words and negative words in each tweet and marks it as positive, negative or neutral based on the word counts. It does sentimate aggregation every 10 seconds and 30 seconds.


### How to run it?

1. Replace twitter keys/tokens in to env.sh file
2. Run ./sbt/sbt assembly from the project directory to build it using scala build tool.
3. run ./submitsh to run the project.



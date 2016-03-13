import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.twitter._
import twitter4j.internal.json.StatusJSONImpl
import java.util.Date

/**
 * 
 * Use this as starting point for performing Tsentiment analysis on tweets from Twitter
 *
 *  To run this app
 *   sbt package
 *   $SPARK_HOME/bin/spark-submit --class "TwitterStreamingApp" --master local[*] ./target/scala-2.10/twitter-streaming-assembly-1.0.jar
 *      <consumer key> <consumer secret> <access token> <access token secret>
 */

object TwitterStreamingApp {
 
    val sparkConf = new SparkConf().setAppName("TwitterPopularTags")
    // to run this application inside an IDE, comment out previous line and uncomment line below
    //val sparkConf = new SparkConf().setAppName("TwitterPopularTags").setMaster("local[*]")
 
    val sc = new SparkContext(sparkConf)
  	val stopwords = sc.textFile("./src/main/resources/stop-words.txt").collect().toSet
  	val positivewords = sc.textFile("./src/main/resources/pos-words.txt").collect().toSet
  	val negativewords = sc.textFile("./src/main/resources/neg-words.txt").collect().toSet

  	
  def main(args: Array[String]) {
    
    if (args.length < 4) {
      System.err.println("Usage: TwitterStreamingApp <consumer key> <consumer secret> " +
        "<access token> <access token secret> [<filters>]")
      System.exit(1)
    }

  	val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = args.take(4)
    val filters = args.takeRight(args.length - 4)
    
    // Set the system properties so that Twitter4j library used by twitter stream
    // can use them to generate OAuth credentials
    System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)


      	
    val ssc = new StreamingContext(sc, Seconds(2))
    val stream = TwitterUtils.createStream(ssc, None, filters)

    val hashTags = stream.flatMap(status => {
      val lang = status.getUser().getLang()
      if (lang == "en") {
        //printTweet(lang, status.getText.replaceAll("""[\p{Punct}]""", ""), status.getCreatedAt)
        printTweetSentiment(status.getText.replaceAll("""[\p{Punct}]""", ""),status.getCreatedAt)        
      }      
      status.getText.replaceAll("""[\p{Punct}]""", "").split(" ").filter(!stopwords.contains(_)).map(x=>replacesentiment(x))
      
    })

    val topCounts10 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(10)).map{case (topic, count) => (count, topic)}.transform(_.sortByKey(false))                    
    
    // Print sentiments
    topCounts10.foreachRDD(rdd => {
      val topList = rdd.take(10)
      println("\nTweet Sentiment in last 10 seconds:".format(rdd.count()))
      topList.foreach{case (count, tag) => println("%s (%s words)".format(tag, count))}
    })
                     
   

    val topCounts30 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(30))
                     .map{case (topic, count) => (count, topic)}
                     .transform(_.sortByKey(false))

    topCounts30.foreachRDD(rdd => {
      val topList = rdd.take(10)
      println("\nTweet Sentiment in last 30 seconds:".format(rdd.count()))
      topList.foreach{case (count, tag) => println("%s (%s words)".format(tag, count))}
    })
    

    ssc.start()
    ssc.awaitTermination()
  }
  
  def printTweet(lang : String, text: String, createdAt: Date) {
    println(s"$createdAt\t$lang\t$text")
  }
  // function to convert words to sentiments
  def replacesentiment(text:String): String ={
    
    if (positivewords.contains(text)){
     return "POSITIVE" 
   }else if (negativewords.contains(text)){
         return "NEGETIVE"
   }else{
       return "NEUTRAL"
   }
  }
  // function to print line sentiments
  def printTweetSentiment( text: String, createdAt: Date) {
   val a = text.split(" ").filter(!stopwords.contains(_))
   print(s"----->\t")
   var poswords = 0
   var negwords = 0
   var sentiment = ""
   def processLine(word: String) {
      if (positivewords.contains(word) )
        poswords+=1
      else if(negativewords.contains(word) )
        negwords+=1
    }
   a.foreach{processLine}
   if (poswords+negwords ==0){
     sentiment = "NEUTRAL"
   }else if (poswords > negwords ){
        sentiment = "POSITIVE"
   }else{
       sentiment = "NEGATIVE"
   }
   println(s"$text   Sentimate= $sentiment positive words:$poswords Negative words: $negwords" )
   (sentiment, poswords, negwords)
  }
  
  
}

package cs.purdue.edu.spatialrdd.main

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by merlin on 11/16/15.
 */
/**
 * this class is used for sampling the spatial data
 */
object SpatialSampleRDD {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("Test for Spark SpatialRDD").setMaster("local[2]")

    //val conf = new SparkConf().setAppName("Test for Spark SpatialRDD")

    val spark = new SparkContext(conf)

    require(args.length==3)

    val inputfile=args(0)
    val outputfile=args(1)
    val fraction=args(2)

    val datardd=spark.textFile(inputfile)

    val locationRDD=datardd.map{
      line=>
        val arry=line.split(",")
        try {
          (arry(2)+","+arry(3)+","+arry(5))
        }catch
          {
            case e:Exception=>
            //println("input format error")
          }
    }.filter(_!=null)

    println("data size")
    println(locationRDD.count())

    val samplerdd=locationRDD.sample(false,fraction.toDouble)

    println("sample data size")
    println(samplerdd.count())

    samplerdd.saveAsTextFile(outputfile)

    spark.stop()

  }
}
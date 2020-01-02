package word;
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;



public class Driver extends Configured implements Tool {

	  public static void main(String args[]) throws Exception {
	    int res = ToolRunner.run(new Driver(), args);
	    System.exit(res);
	  }
	  
	  public int run(String[] args) throws Exception {
		    Path inputPath = new Path(args[0]);
		    Path outputPath = new Path(args[1]);

		    Configuration conf = getConf();
		    Job job = new Job(conf, this.getClass().toString());
		    job.setJarByClass(Driver.class);

		    FileInputFormat.setInputPaths(job, inputPath);
		    FileOutputFormat.setOutputPath(job, outputPath);

		    job.setJobName("WordCount");

		    job.setMapperClass(MapWord.class);
		    job.setCombinerClass(ReduceWord.class);
		    job.setReducerClass(ReduceWord.class);
		    job.setMapOutputKeyClass(Text.class);
		    job.setMapOutputValueClass(IntWritable.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(IntWritable.class);
		    job.setInputFormatClass(TextInputFormat.class);
		    job.setOutputFormatClass(TextOutputFormat.class);

//		    job.setJar("WordCountf.jar");
		    

		   return job.waitForCompletion(true) ? 0 : 1;
		  }
	  
	  
	  
}
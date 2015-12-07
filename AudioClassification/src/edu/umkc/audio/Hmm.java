package edu.umkc.audio;

import jAudioFeatureExtractor.AudioFeatures.*;
import jAudioFeatureExtractor.jAudioTools.AudioSamples;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class Hmm {
	
	public static String DataTrainingPath = "data\\genres\\training\\";
	public static String DataTestingPath = "data\\genres\\training\\";
    
    
    public static void DataPrepare() throws Exception
    {
  
       //  String[] classes = {"blues", "classical", "country", "disco", "hiphop", "jazz", "metal", "pop", "reggae", "rock"};
    		String[] classes = {"blues", "classical","country","disco", "hiphop"};
    		
    		File f = new File(DataTrainingPath+"blues.seq");
    		f.delete();
    		f = new File(DataTrainingPath+"classical.seq");
    		f.delete();
    		f = new File(DataTrainingPath+"country.seq");
    		f.delete();
    		f = new File(DataTrainingPath+"disco.seq");
    		f.delete();
    		f = new File(DataTrainingPath+"hiphop.seq");
    		f.delete();
    		
    		
    		
    		for (int i = 0; i < classes.length; i++)  {
         //    File folder = new File("data/training/" + classes[i]);
         	File folder = new File(DataTrainingPath + classes[i]);
             if (folder.isDirectory()) {
                 File[] files = folder.listFiles();
                 for (int j = 0; j < files.length; j++) {
                    makeData(files[j], classes[i]);
                    
                 }
             }
         }


    }
    public static void main(String args[]) {

        try {
        		DataPrepare();
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void makeData(File file, String str) throws Exception {
        AudioSamples samples = new AudioSamples(file, file.getPath(), false);
        double[] samplesInfo = samples.getSamplesMixedDown();
        try {
        	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(DataTrainingPath+str+".seq", true)));
        	
            for(int i=0;i<30;i+=3) 
            {
            	double a = samplesInfo[i]+1;
            	double b = samplesInfo[i+1]+1;
            	double c = samplesInfo[i+2]+1;
            	out.print("[ " + a + " " +b +" " +c +" ] ;");
            //	out.print("[ " a + " ] ");
            }
            
            out.print("\n");
            out.close();
            
        }catch (IOException e) {
           
        }
              
       }
}

    

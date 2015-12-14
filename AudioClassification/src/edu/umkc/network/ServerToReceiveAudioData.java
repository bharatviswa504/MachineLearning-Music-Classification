package edu.umkc.network;

import jAudioFeatureExtractor.AudioFeatures.AreaMoments;
import jAudioFeatureExtractor.AudioFeatures.BeatHistogram;
import jAudioFeatureExtractor.AudioFeatures.BeatHistogramLabels;
import jAudioFeatureExtractor.AudioFeatures.BeatSum;
import jAudioFeatureExtractor.AudioFeatures.Compactness;
import jAudioFeatureExtractor.AudioFeatures.ConstantQ;
import jAudioFeatureExtractor.AudioFeatures.FeatureExtractor;
import jAudioFeatureExtractor.AudioFeatures.FractionOfLowEnergyWindows;
import jAudioFeatureExtractor.AudioFeatures.LPC;
import jAudioFeatureExtractor.AudioFeatures.MFCC;
import jAudioFeatureExtractor.AudioFeatures.MagnitudeSpectrum;
import jAudioFeatureExtractor.AudioFeatures.Moments;
import jAudioFeatureExtractor.AudioFeatures.PeakFinder;
import jAudioFeatureExtractor.AudioFeatures.PowerSpectrum;
import jAudioFeatureExtractor.AudioFeatures.RMS;
import jAudioFeatureExtractor.AudioFeatures.SpectralCentroid;
import jAudioFeatureExtractor.AudioFeatures.SpectralFlux;
import jAudioFeatureExtractor.AudioFeatures.SpectralRolloffPoint;
import jAudioFeatureExtractor.AudioFeatures.SpectralVariability;
import jAudioFeatureExtractor.AudioFeatures.StrongestBeat;
import jAudioFeatureExtractor.AudioFeatures.ZeroCrossings;
import jAudioFeatureExtractor.jAudioTools.AudioSamples;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import edu.umkc.audio.*;
import edu.umkc.audio.MultiFeatureExtraction.AudioFeature;


public class ServerToReceiveAudioData {
	public static String DataTrainingPath = "F:\\workspace\\AudioClassification\\data\\genres\\training\\";
	public static String DataTestingPath = "F:\\workspace\\AudioClassification\\data\\genres\\testing\\";
	
	  public enum AudioFeature {
	        Spectral_Centroid(1),
	        Spectral_Rolloff_Point(2),
	        Spectral_Flux(3),
	        Compactness(4),
	        Spectral_Variability(5),
	        Root_Mean_Square(6),
	        Fration_of_Low_Energy_Windows(7),
	        Zero_Crossings(8),
	        Strongest_Beat(9),
	        Beat_Sum(10),
	        MFCC(11),
	        ConstantQ(12),
	        LPC(13),
	        Method_of_Moments(14),
	        Peak_Detection(15),
	        Area_Method_of_MFCCs(16);

	        private final int value;

	        AudioFeature(int value) {

	            this.value = value;
	        }

	        public int getValue() {
	            return value;
	        }

	    }

    public static void main(String[] args) throws Exception {
    	
    	//ServerSocket listener = new ServerSocket(9090);
        try {
            while (true) {
               
            	 String serverAddress = "10.205.0.115";
                 Socket socket = new Socket(serverAddress, 1234);
                 InputStream is = socket.getInputStream();
                 
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                System.out.println("Socket is waiting");
                String name = br.readLine();
                System.out.println("Socket received data file is:" +name);
                String[] split = name.split(".");
                
                
                
                //Sending to spark
                
          /*      String serverAddress = "localhost";
                Socket s = new Socket(serverAddress, 9090);
                
                PrintWriter out =
                        new PrintWriter(s.getOutputStream(), true);
                    OutputStream os = s.getOutputStream();
                    
                    out.println(genre);
                    
                    s.close();
                    out.flush();
                    out.close();*/
                        
            }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        finally
        {
        	//listener.close();
        }
        
        /*
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        String length = br.readLine();
        long lengths = Long.parseLong(length);
        File f = new File("1.au");
        f.delete();
       System.out.println(br.readLine());
        
       is.close();
       s.close();
   
       //Opening 2nd socket to receive data
       Socket s1 = new Socket(serverAddress,9090);
        InputStream is1 = s1.getInputStream();
       
        FileOutputStream fos = new FileOutputStream("1.au");
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte [] bytearray  = new byte [(int)lengths];
        System.out.println("Byte Array Length is:" + bytearray.length);
        int bytesRead = is1.read(bytearray,0,bytearray.length);
        System.out.println("bytes Read Intially is:"+bytesRead);
        int currentTot = bytesRead;

        do 
        {
          bytesRead = is1.read(bytearray, currentTot, (bytearray.length-currentTot));
          System.out.println("Bytes Read " + bytesRead + "current Tot " +currentTot);
          if(bytesRead >= 0) currentTot += bytesRead;
        } 
        while(bytesRead > 0);
        
        	System.out.println("Storing to file");
        	System.out.println("File Length is: "+currentTot);
            bos.write(bytearray, 0 , currentTot);
        
        bos.flush();
        bos.close();
        is1.close();
        s1.close();
        
        
        BufferedReader reader = new BufferedReader(new FileReader(DataTrainingPath+"test.arff"));
        Instances trainingInstances = new Instances(reader);
        reader.close();
        
        classify(trainingInstances,new File("1.au"));

      
        
        */

    }
    
    public static String classify(Instances train,File file) throws Exception {
        FastVector atts = new FastVector();
        String[] classes = {"blues", "classical", "country", "disco", "hiphop", "jazz", "metal", "pop", "reggae", "rock"};
        double[] val;
        FastVector attValsRel = new FastVector();

        //Setting attributes for the test data
            Attribute attributeZero=new Attribute("Zero_Crossings");
            atts.addElement(attributeZero);
            Attribute attributeLPC=new Attribute("LPC");
            atts.addElement(attributeLPC);
            Attribute attributeCentroid=new Attribute("Spectral_Centroid");
            atts.addElement(attributeCentroid);
            Attribute attributeRollOff=new Attribute("Spectral_Rolloff_Point");
            atts.addElement(attributeRollOff);
            Attribute attributePeakDetection=new Attribute("Peak_Detection");
            atts.addElement(attributePeakDetection);
            Attribute attributeStrongestBeat=new Attribute("Strongest_Beat");
            atts.addElement(attributeStrongestBeat);
            Attribute attributeBeatSum = new Attribute("Beat_Sum");
            atts.addElement(attributeBeatSum); 
            Attribute attributeRMS = new Attribute("RMS");
            atts.addElement(attributeRMS);
            Attribute attributeConstantQ = new Attribute("ConstantQ");
            atts.addElement(attributeConstantQ);
            Attribute attributeMFT = new Attribute("MagnitudeFFT");
            atts.addElement(attributeMFT);
            Attribute attributeMFCC = new Attribute("MFCC");
            atts.addElement(attributeMFCC);
            
            
            for (int i = 0; i < classes.length; i++)
                attValsRel.addElement(classes[i]);
            atts.addElement(new Attribute("class", attValsRel));
        // Adding instances to the test dataset

            Instances test = new Instances("AudioSamples", atts, 0);
             val = makeData(file, null, attValsRel, test.numAttributes());
            Instance instance = new Instance(test.numAttributes());
            instance.setValue(attributeZero, val[0]);
            instance.setValue(attributeLPC,val[1]);
            instance.setValue(attributeCentroid, val[2]);
            instance.setValue(attributeRollOff, val[3]);
            instance.setValue(attributePeakDetection,val[4]);
            instance.setValue(attributeStrongestBeat, val[5]);
            instance.setValue(attributeBeatSum, val[6]); 
            instance.setValue(attributeRMS, val[7]);
            instance.setValue(attributeConstantQ, val[8]);
            instance.setValue(attributeMFT, val[9]);
            instance.setValue(attributeMFCC, val[10]);
            
            test.add(instance);
        //Setting the class attribute
            test.setClassIndex(test.numAttributes() - 1);
            System.out.println(test);
        //Trainging the classifier with train dataset
            train.setClassIndex(test.numAttributes()-1);
            FilteredClassifier  classifier=new FilteredClassifier();
            classifier.setClassifier(new NaiveBayes());
            classifier.buildClassifier(train);

            double pred=0.0;
        //Classifying the test data with the train data
            for (int i = 0; i < test.numInstances(); i++) {
                 pred = classifier.classifyInstance(test.instance(i));
                System.out.println("===== Classified instance =====");
                System.out.println("Class predicted: " + test.classAttribute().value((int) pred));
              
            }
            
            return test.classAttribute().value((int) pred);

    }
    
    public static double[] makeData(File file, String str, FastVector attValsRel, int noOfAttributes) throws Exception {
        AudioSamples samples = new AudioSamples(file, file.getPath(), false);
        if(str==null)
        {
            double[] val = new double[noOfAttributes-1];
            double[] f = feature(samples, AudioFeature.Zero_Crossings);
            val[0] = f[0];
            double[] f1 = feature(samples, AudioFeature.LPC);
            val[1] = f1[0];
            double[] f2 = feature(samples, AudioFeature.Spectral_Centroid);
            val[2] = f2[0];
            double[] f3 = feature(samples, AudioFeature.Spectral_Rolloff_Point);
            val[3] = f3[0];
            double[] f4 = feature(samples, AudioFeature.Peak_Detection);
            val[4] = f4[0];
            double[] f5 = feature(samples,AudioFeature.Beat_Sum);
            val[5] = f5[0]; 
            double[] f6 = feature(samples,AudioFeature.Strongest_Beat);
            val[6] = f6[0];
            double[] f7 = feature(samples,AudioFeature.Root_Mean_Square);
            val[7] = f7[0];
            double[] f8 = feature(samples,AudioFeature.Root_Mean_Square);
            val[8] = f8[0];
            double[] f9 = feature(samples,AudioFeature.Method_of_Moments);
            val[9] = f9[0];
            double[] f10 = feature(samples,AudioFeature.MFCC);
            val[10] = f10[0];

            
            return val;
        }
        else {
            double[] val = new double[noOfAttributes];
            double[] f = feature(samples, AudioFeature.Zero_Crossings);
            val[0] = f[0];
            double[] f1 = feature(samples, AudioFeature.LPC);
            val[1] = f1[0];
            double[] f2 = feature(samples, AudioFeature.Spectral_Centroid);
            val[2] = f2[0];
            double[] f3 = feature(samples, AudioFeature.Spectral_Rolloff_Point); 
            val[3] = f3[0];
            double[] f4 = feature(samples, AudioFeature.Peak_Detection);
            val[4] = f4[0];
            double[] f5 = feature(samples,AudioFeature.Beat_Sum);
            val[5] = f5[0]; 
            double[] f6 = feature(samples,AudioFeature.Strongest_Beat);
            val[6] = f6[0];
            double[] f7 = feature(samples,AudioFeature.Root_Mean_Square);
            val[7] = f7[0];
            double[] f8 = feature(samples,AudioFeature.Root_Mean_Square);
            val[8] = f8[0];
            double[] f9 = feature(samples,AudioFeature.Method_of_Moments);
            val[9] = f9[0];
            double[] f10 = feature(samples,AudioFeature.MFCC);
            val[10] = f10[0];
            if (str != null)
                val[11] = attValsRel.indexOf(str);
            return val;
        }

    }
    
    public static double[] feature(AudioSamples audio, AudioFeature i) throws Exception {
        /**
         * 1. Spectral Centroid
         * 2. Spectral Rolloff Point
         * 3. Spectral Flux
         * 4. Compactness
         * 5. Spectral Variability
         * 6. Root Mean Square
         * 7. Fration of Low Energy Windows
         * 8. Zero Crossings
         * 9. Strongest Beat
         * 10. Beat Sum
         * 11. MFCC
         * 12. ConstantQ
         * 13. LPC
         * 14. Method of Moments
         * 15. Peak Detection
         * 16. Area Method of MFCCs
         *
         */
        FeatureExtractor featureExt;
        double[] samples = audio.getSamplesMixedDown();
        double sampleRate = audio.getSamplingRateAsDouble();
        double[][] otherFeatures = audio.getSamplesChannelSegregated();
        double[][] windowSample;
        switch (i.getValue()) {
            case 1:
                featureExt = new SpectralCentroid();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);

            case 2:
                featureExt = new PowerSpectrum();
                otherFeatures[0] = featureExt.extractFeature(samples, sampleRate, otherFeatures);
                featureExt = new SpectralRolloffPoint();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);

            case 3:
                windowSample = audio.getSampleWindowsMixedDown(2);
                featureExt = new MagnitudeSpectrum();
                otherFeatures[0] = featureExt.extractFeature(windowSample[0], sampleRate, otherFeatures);
                otherFeatures[1] = featureExt.extractFeature(windowSample[1], sampleRate, otherFeatures);
                featureExt = new SpectralFlux();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);

            case 4:
                featureExt = new MagnitudeSpectrum();
                otherFeatures[0] = featureExt.extractFeature(samples, sampleRate, otherFeatures);
                featureExt = new Compactness();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);

            case 5:
                featureExt = new MagnitudeSpectrum();
                otherFeatures[0] = featureExt.extractFeature(samples, sampleRate, otherFeatures);
                featureExt = new SpectralVariability();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);

            case 6:
                featureExt = new RMS();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);
            case 7:
                featureExt = new RMS();
                windowSample = audio.getSampleWindowsMixedDown(100);
                for (int j = 0; j < 100; j++) {
                    otherFeatures[j] = featureExt.extractFeature(windowSample[j], sampleRate, null);
                }
                featureExt = new FractionOfLowEnergyWindows();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);
            case 8:
                featureExt = new ZeroCrossings();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);
            case 9:
                featureExt = new BeatHistogram();
                double[][] otherFeature = new double[2][];
                otherFeature[0] = featureExt.extractFeature(samples, sampleRate, otherFeatures);
                featureExt = new BeatHistogramLabels();
                otherFeature[1] = featureExt.extractFeature(samples, sampleRate, otherFeatures); 
                featureExt = new StrongestBeat();
                return featureExt.extractFeature(samples, sampleRate, otherFeature);
            case 10:
                featureExt = new BeatHistogram();
                otherFeatures[0] = featureExt.extractFeature(samples, sampleRate, otherFeatures);
                featureExt = new BeatSum();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);
            case 11:
                featureExt = new MagnitudeSpectrum();
                otherFeatures[0] = featureExt.extractFeature(samples, sampleRate, otherFeatures);
                featureExt = new MFCC();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);
            case 12:
                featureExt = new ConstantQ();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);
            case 13:
                featureExt = new LPC();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);
            case 14:
                featureExt = new MagnitudeSpectrum();
                otherFeatures[0] = featureExt.extractFeature(samples, sampleRate, otherFeatures);
                featureExt = new Moments();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);
            case 15:
                featureExt = new MagnitudeSpectrum();
                otherFeatures[0] = featureExt.extractFeature(samples, sampleRate, otherFeatures);
                featureExt = new PeakFinder();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);
            case 16:
                featureExt = new MagnitudeSpectrum();
                windowSample = audio.getSampleWindowsMixedDown(100);
                for (int j = 0; j < 100; j++) {
                    otherFeatures[j] = featureExt.extractFeature(windowSample[j], sampleRate, null);
                }
                featureExt = new AreaMoments();
                return featureExt.extractFeature(samples, sampleRate, otherFeatures);

            default:
                return null;

        }
    }



}


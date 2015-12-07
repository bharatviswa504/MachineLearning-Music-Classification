package edu.umkc.audio;

import jAudioFeatureExtractor.AudioFeatures.*;
import jAudioFeatureExtractor.jAudioTools.AudioSamples;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
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
import java.util.Random;


public class MultiFeatureExtraction {
	
	public static String DataTrainingPath = "data\\genres\\training\\";
	public static String DataTestingPath = "data\\genres\\testing\\";
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

    static FilteredClassifier classifier;
    
    public static void DataPrepare() throws Exception
    {
    	 FastVector atts = new FastVector();  
         atts.addElement(new Attribute("Zero_Crossings"));
         atts.addElement(new Attribute("LPC"));
         atts.addElement(new Attribute("Spectral_Centroid"));
         atts.addElement(new Attribute("Spectral_Rolloff_Point"));
         atts.addElement(new Attribute("Peak_Detection"));
         atts.addElement(new Attribute("Beat_Sum"));
         atts.addElement(new Attribute("Strongest_Beat"));
         atts.addElement(new Attribute("RMS"));
         atts.addElement(new Attribute("ConstantQ"));
         atts.addElement(new Attribute("MagnitudeFFT"));
    
         String[] classes = {"blues", "classical", "country", "disco", "hiphop", "jazz", "metal","pop","reggae","rock"};
       //  String[] classes = {"blues"};

         double[] val;
         FastVector attValsRel = new FastVector();
         for (int i = 0; i < classes.length; i++)
             attValsRel.addElement(classes[i]);
         atts.addElement(new Attribute("class", attValsRel));
         Instances instances = new Instances("AudioSamples", atts, 0);
         for (int i = 0; i < classes.length; i++) {
         //    File folder = new File("data/training/" + classes[i]);
         	File folder = new File(DataTrainingPath + classes[i]);
             if (folder.isDirectory()) {
                 File[] files = folder.listFiles();
                 for (int j = 0; j < files.length; j++) {
                     val = makeData(files[j], classes[i], attValsRel, instances.numAttributes());
                     instances.add(new Instance(1.0, val));
                 }
             }
         }

         System.out.println(instances);
         
         BufferedWriter writer = new BufferedWriter(new FileWriter(DataTrainingPath +"test.arff"));
         writer.write(instances.toString());
         writer.flush();
         writer.close();

    }
    public static void main(String args[]) {

        try {

             DataPrepare();
        	
            BufferedReader reader = new BufferedReader(new FileReader(DataTrainingPath+"test.arff"));
            Instances trainingInstances = new Instances(reader);
            reader.close();
          
            //Building confusion Matrix and Other Metrics
            wekaAlgorithms(trainingInstances);
            
            
            //Testing
            classify(trainingInstances,new File(DataTestingPath+"classical//classical.00010.au"));

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            
            System.out.println(f9[0]);
            
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
            
            System.out.println(f9[0] +str);
            
            
            if (str != null)
                val[10] = attValsRel.indexOf(str);
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
            	
            	double[][] otherFeature = new double[3][];
            	
            	featureExt = new RMS();
            	/*String [] dependecies3 = featureExt.getDepenedencies();
            	if(dependecies3!=null)
            	for(int k=0;k<dependecies3.length;k++)
                	System.out.println(dependecies3[k]);*/
               otherFeature[0]= featureExt.extractFeature(samples, sampleRate, null);
               
                featureExt = new BeatHistogram();
                /*String [] dependecies1 = featureExt.getDepenedencies();
                for(int k=0;k<dependecies1.length;k++)
                	System.out.println(dependecies1[k]);*/
                double[][] RmsFeat = new double[1][];
                RmsFeat[0] = otherFeature[0];
                otherFeature[1] = featureExt.extractFeature(samples, sampleRate, RmsFeat);
                
                featureExt = new BeatHistogramLabels();
                /*String [] dependecies2 = featureExt.getDepenedencies();
                for(int k=0;k<dependecies2.length;k++)
                	System.out.println(dependecies2[k]);*/
                double[][] beatHistogramFeat = new double[1][];
                beatHistogramFeat[0] = otherFeature[1];
                otherFeature[2] = featureExt.extractFeature(samples, sampleRate, beatHistogramFeat); 
                featureExt = new StrongestBeat();
                
                double[][] StrongestBeatFeat = new double[2][];
                StrongestBeatFeat[0]=otherFeature[1];
                StrongestBeatFeat[1]=otherFeature[2];
                /*String[] dependencies = featureExt.getDepenedencies();
                  for(int k=0;k<dependencies.length;k++)
                	System.out.println(dependencies[k]);*/
                return featureExt.extractFeature(samples, sampleRate, StrongestBeatFeat); 
            /*	 featureExt = new BeatHistogram();
                 double[][] otherFeature = new double[2][];
                 otherFeature[0] = featureExt.extractFeature(samples, sampleRate, otherFeatures);
                 featureExt = new BeatHistogramLabels();
                 otherFeature[1] = featureExt.extractFeature(samples, sampleRate, otherFeatures); 
                 featureExt = new StrongestBeat();
                 return featureExt.extractFeature(samples, sampleRate, otherFeature); */
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

    public static void wekaAlgorithms(Instances data) throws Exception {
         classifier = new FilteredClassifier();         // new instance of tree
      // classifier.setClassifier(new NaiveBayes());
      //  classifier.setClassifier(new J48());
         //classifier.setClassifier(new RandomForest());
         
         	classifier.setClassifier(new ZeroR());
     //   classifier.setClassifier(new NaiveBayes());
        // classifier.setClassifier(new IBk());
        data.setClassIndex(data.numAttributes() - 1);
        Evaluation eval = new Evaluation(data);
       

        int folds = 10;
        eval.crossValidateModel(classifier, data, folds, new Random(1));

        System.out.println("===== Evaluating on filtered (training) dataset =====");
        System.out.println(eval.toSummaryString());
        System.out.println(eval.toClassDetailsString());
        double[][] mat = eval.confusionMatrix();
        System.out.println("========= Confusion Matrix =========");
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {

                System.out.print(mat[i][j] + "  ");
            }
            System.out.println(" ");
        }
    }

    public static void classify(Instances train,File file) throws Exception {
        FastVector atts = new FastVector();
        String[] classes = {"blues", "classical", "country", "disco", "hiphop", "jazz"};
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
            
            test.add(instance);
        //Setting the class attribute
            test.setClassIndex(test.numAttributes() - 1);
            System.out.println(test);
        //Trainging the classifier with train dataset
            classifier=new FilteredClassifier();
            classifier.buildClassifier(train);

        //Classifying the test data with the train data
            for (int i = 0; i < test.numInstances(); i++) {
                double pred = classifier.classifyInstance(test.instance(i));
                System.out.println("===== Classified instance =====");
                System.out.println("Class predicted: " + test.classAttribute().value((int) pred));
            }

    }

}



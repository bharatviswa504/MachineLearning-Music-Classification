package edu.umkc.audio;
import jAudioFeatureExtractor.jAudioTools.AudioSamples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationVector;
import be.ac.ulg.montefiore.run.jahmm.OpdfMultiGaussianFactory;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationSequencesReader;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationVectorReader;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import be.ac.ulg.montefiore.run.jahmm.learn.KMeansLearner;

public class HMMModelBuilder {
	
	public static Hmm<ObservationVector> learntHmmBlues = null;
	public static Hmm<ObservationVector> learntHmmClassical = null;
	public static Hmm<ObservationVector> learntHmmCountry = null;
	public static Hmm<ObservationVector> learntHmmDisco = null;
	public static Hmm<ObservationVector> learntHmmHipHop = null;

	public static void buildModel() throws Exception
	{
		int x=10;
		//Blues Hmm Model
		OpdfMultiGaussianFactory initBlues = new OpdfMultiGaussianFactory(
				1);

		Reader learnReaderBlues = new FileReader("data\\genres\\training\\blues.seq");
		List<List<ObservationVector>> learnSequencesBlues = ObservationSequencesReader
				.readSequences(new ObservationVectorReader(1),
						learnReaderBlues);
		learnReaderBlues.close();

		KMeansLearner<ObservationVector> kMeansLearnerBlues = new KMeansLearner<ObservationVector>(
				x, initBlues, learnSequencesBlues);
		// Create an estimation of the HMM (initHmm) using one iteration
		// of the
		// k-Means algorithm
		Hmm<ObservationVector> initHmmBlues = kMeansLearnerBlues.iterate();
		// Use BaumWelchLearner to create the HMM (learntHmm) from
		// initHmm

		BaumWelchLearner baumWelchLearnerBlues = new BaumWelchLearner();
		learntHmmBlues = baumWelchLearnerBlues.learn(initHmmBlues,
				learnSequencesBlues);
		
		//Classical Hmm Model
		OpdfMultiGaussianFactory initClassical= new OpdfMultiGaussianFactory(
				1);

		Reader learnReaderClassical = new FileReader("data\\genres\\training\\classical.seq");
		List<List<ObservationVector>> learnSequencesClassical = ObservationSequencesReader
				.readSequences(new ObservationVectorReader(1),
						learnReaderClassical);
		learnReaderClassical.close();

		KMeansLearner<ObservationVector> kMeansLearnerClassical = new KMeansLearner<ObservationVector>(
				x, initClassical, learnSequencesClassical);
		// Create an estimation of the HMM (initHmm) using one iteration
		// of the
		// k-Means algorithm
		Hmm<ObservationVector> initHmmClassical = kMeansLearnerClassical.iterate();
		// Use BaumWelchLearner to create the HMM (learntHmm) from
		// initHmm

		BaumWelchLearner baumWelchLearnerClassical = new BaumWelchLearner();
		 learntHmmClassical = baumWelchLearnerBlues.learn(initHmmClassical,
				learnSequencesClassical);
		 
		 //Country Hmm Model
		 OpdfMultiGaussianFactory initCountry = new OpdfMultiGaussianFactory(
					1);
		 Reader learnReaderCountry = new FileReader("data\\genres\\training\\country.seq");
			List<List<ObservationVector>> learnSequencesCountry = ObservationSequencesReader
					.readSequences(new ObservationVectorReader(1),
							learnReaderCountry);
			learnReaderCountry.close();

			KMeansLearner<ObservationVector> kMeansLearnerCountry = new KMeansLearner<ObservationVector>(
					x, initCountry, learnSequencesCountry);
			// Create an estimation of the HMM (initHmm) using one iteration
			// of the
			// k-Means algorithm
			Hmm<ObservationVector> initHmmCountry = kMeansLearnerCountry.iterate();
			// Use BaumWelchLearner to create the HMM (learntHmm) from
			// initHmm

			BaumWelchLearner baumWelchLearnerCountry = new BaumWelchLearner();
			 learntHmmCountry = baumWelchLearnerCountry.learn(initHmmCountry,
					learnSequencesCountry);
			 
			 //Disco Hmm Model
			 OpdfMultiGaussianFactory initDisco = new OpdfMultiGaussianFactory(
						1);
			 Reader learnReaderDisco = new FileReader("data\\genres\\training\\disco.seq");
				List<List<ObservationVector>> learnSequencesDisco = ObservationSequencesReader
						.readSequences(new ObservationVectorReader(1),
								learnReaderDisco);
				learnReaderDisco.close();

				KMeansLearner<ObservationVector> kMeansLearnerDisco = new KMeansLearner<ObservationVector>(
						x, initDisco, learnSequencesDisco);
				// Create an estimation of the HMM (initHmm) using one iteration
				// of the
				// k-Means algorithm
				Hmm<ObservationVector> initHmmDisco = kMeansLearnerDisco.iterate();
				// Use BaumWelchLearner to create the HMM (learntHmm) from
				// initHmm

				BaumWelchLearner baumWelchLearnerDisco = new BaumWelchLearner();
				 learntHmmDisco = baumWelchLearnerDisco.learn(initHmmDisco,
						learnSequencesDisco);
				 
				 //Disco Hmm Model
				 OpdfMultiGaussianFactory initHipHop = new OpdfMultiGaussianFactory(
							1);
				 Reader learnReaderHipHop = new FileReader("data\\genres\\training\\hiphop.seq");
					List<List<ObservationVector>> learnSequencesHipHop = ObservationSequencesReader
							.readSequences(new ObservationVectorReader(1),
									learnReaderHipHop);
					learnReaderHipHop.close();

					KMeansLearner<ObservationVector> kMeansLearnerHipHop = new KMeansLearner<ObservationVector>(
							x, initHipHop, learnSequencesHipHop);
					// Create an estimation of the HMM (initHmm) using one iteration
					// of the
					// k-Means algorithm
					Hmm<ObservationVector> initHmmHipHop = kMeansLearnerHipHop.iterate();
					// Use BaumWelchLearner to create the HMM (learntHmm) from
					// initHmm

					BaumWelchLearner baumWelchLearnerHipHop = new BaumWelchLearner();
					 learntHmmHipHop = baumWelchLearnerHipHop.learn(initHmmHipHop,
							learnSequencesHipHop);
					 
					 


		
	}
	public static String DataTestingPath="data\\genres\\testing\\";
	
	public static void test() throws Exception
	{
	
		  File testFile = new File("data\\genres\\test.seq");
          testFile.delete();
          
		 File file = new File(DataTestingPath+"country\\country.00019.au");
		AudioSamples samples = new AudioSamples(file, file.getPath(), false);
	        double[] samplesInfo = samples.getSamplesMixedDown();
	        try {
	        	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data\\genres\\test.seq", true)));
	        	
	            for(int i=0;i<150;i+=1) 
	            {
	            	double a = samplesInfo[i]+1;
	            //	double b = samplesInfo[i+1]+1;
	            //	double c = samplesInfo[i+2]+1;
	            	out.print("[ " +a + " ]; ");
	            
	            //	out.print("[ " + a + " " +b +" " +c +" ] ;");
	            }
	            
	            out.print("\n");
	            out.close();
	           

	            Reader testReader = new FileReader(testFile);
	    		List<List<ObservationVector>> testSequences = ObservationSequencesReader
	    				.readSequences(new ObservationVectorReader(), testReader);
	    		testReader.close();
	            
	    		double classicalProb =0.0;
	    		double bluesProb =0.0;
	    		double countryProb =0.0;
	    		double discoProb =0.0;
	    		double hiphopProb=0.0;
	    		
	    		for(int i=0;i<testSequences.size();i++) 
	    		{
	    			List<ObservationVector> l=testSequences.get(i);
	    			bluesProb=learntHmmBlues.probability(testSequences.get(i));
	    			classicalProb=learntHmmClassical.probability(testSequences.get(i));
	    			countryProb=learntHmmCountry.probability(testSequences.get(i));
	    			discoProb=learntHmmDisco.probability(testSequences.get(i));
	    			hiphopProb=learntHmmHipHop.probability(testSequences.get(i));
	    			System.out.println(bluesProb);
		    		System.out.println(classicalProb);
		    		System.out.println(countryProb);
		    		System.out.println(discoProb);
		    		System.out.println(hiphopProb);
	    		}
	    		
	    		if(bluesProb >classicalProb  && bluesProb >countryProb && bluesProb > hiphopProb && bluesProb >discoProb)
	    			System.out.println("Blues");
	    		else if(classicalProb >bluesProb &&  classicalProb >countryProb && classicalProb > hiphopProb  && classicalProb >discoProb)
	    			System.out.println("Classical");
	    		else if(countryProb >bluesProb &&  countryProb >classicalProb && countryProb > hiphopProb &&  countryProb >discoProb)
	    			System.out.println("country");
	    		else if(discoProb >bluesProb &&  discoProb >classicalProb && discoProb > hiphopProb &&  discoProb >countryProb)
	    			System.out.println("Disco");
	    		else if(hiphopProb >bluesProb &&  hiphopProb >classicalProb && hiphopProb > discoProb &&  hiphopProb >countryProb)
	    			System.out.println("HipHop");
	    		
	        }catch (IOException e) {
	          e.printStackTrace(); 
	        }
	       
	}
	
	
	public static void main(String args[]) throws Exception
	{
		HMMModelBuilder.buildModel();
		HMMModelBuilder.test();
	}
	
}

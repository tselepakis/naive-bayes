package lt_part02;

import java.math.BigDecimal;
import java.math.RoundingMode;



public class Main {
	public static void main(String... args) {
		
		NaiveBayes nb1 = new NaiveBayes("stop", "part1", 9, false);
		NaiveBayes nb2 = new NaiveBayes("stop", "part2", 9, false);
		NaiveBayes nb3 = new NaiveBayes("stop", "part3", 9, false);
		NaiveBayes nb4 = new NaiveBayes("stop", "part4", 9, false);
		NaiveBayes nb5 = new NaiveBayes("stop", "part5", 9, false);
		NaiveBayes nb6 = new NaiveBayes("stop", "part6", 9, false);
		NaiveBayes nb7 = new NaiveBayes("stop", "part7", 9, false);
		NaiveBayes nb8 = new NaiveBayes("stop", "part8", 9, false);
		NaiveBayes nb9 = new NaiveBayes("stop", "part9", 9, false);
		NaiveBayes nb10 = new NaiveBayes("stop", "part10", 9, false);

		nb1.estimateCategory();
		nb2.estimateCategory();
		nb3.estimateCategory();
		nb4.estimateCategory();
		nb5.estimateCategory();
		nb6.estimateCategory();
		nb7.estimateCategory();
		nb8.estimateCategory();
		nb9.estimateCategory();
		nb10.estimateCategory();
		
		nb1.print();
		nb2.print();
		nb3.print();
		nb4.print();
		nb5.print();
		nb6.print();	
		nb7.print();		
		nb8.print();		
		nb9.print();		
		nb10.print();
		
		BigDecimal bd1 = new BigDecimal(nb1.precision);
		BigDecimal bd2 = new BigDecimal(nb1.precision);
		BigDecimal bd3 = new BigDecimal(nb1.precision);
		BigDecimal bd4 = new BigDecimal(nb1.precision);
		BigDecimal bd5 = new BigDecimal(nb1.precision);
		BigDecimal bd6 = new BigDecimal(nb1.precision);
		BigDecimal bd7 = new BigDecimal(nb1.precision);
		BigDecimal bd8 = new BigDecimal(nb1.precision);
		BigDecimal bd9 = new BigDecimal(nb1.precision);
		BigDecimal bd10 = new BigDecimal(nb1.precision);
		BigDecimal deka = new BigDecimal(10);
		BigDecimal macroPrecision = bd1.add(bd2).add(bd3).add(bd4).add(bd5).add(bd6).add(bd7).add(bd8).add(bd9).add(bd10).divide(deka);
		System.out.println("Mean Precision: "+macroPrecision.setScale(2, RoundingMode.CEILING));
	
		bd1 = new BigDecimal(nb1.recall);
		bd2 = new BigDecimal(nb1.recall);
		bd3 = new BigDecimal(nb1.recall);
		bd4 = new BigDecimal(nb1.recall);
		bd5 = new BigDecimal(nb1.recall);
		bd6 = new BigDecimal(nb1.recall);
		bd7 = new BigDecimal(nb1.recall);
		bd8 = new BigDecimal(nb1.recall);
		bd9 = new BigDecimal(nb1.recall);
		bd10 = new BigDecimal(nb1.recall);
		BigDecimal macroRecall = bd1.add(bd2).add(bd3).add(bd4).add(bd5).add(bd6).add(bd7).add(bd8).add(bd9).add(bd10).divide(deka);
		System.out.println("Mean Recall: "+macroRecall.setScale(2, RoundingMode.CEILING));
		
	}


}

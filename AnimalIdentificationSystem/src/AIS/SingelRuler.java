package AIS;

import java.util.ArrayList;
import java.util.List;


public class SingelRuler {
	// storage all the ruler that will be used in this algorithm
	// storage a single ruler
	int newFeature;
	// IFS means Inference Feature Set
	List<Integer> IFS = new ArrayList<Integer>();
	public SingelRuler(int []a) {
		if(a != null) {
			newFeature = a[0];
			for(int i=1 ; i<a.length ; i++) {
				IFS.add(a[i]);
			}
		}
	}
	
	
}

package AIS;

import java.util.concurrent.LinkedBlockingQueue;

public class RulerSet {
	// storage all the ruler
	// storage the ruler to be test in the next loop
	boolean ChangeFlag;
	int [][]ruler;
	LinkedBlockingQueue<SingelRuler> rulerSet = new LinkedBlockingQueue<SingelRuler>();
	
	
	public RulerSet(int [][]ruler) {
		ChangeFlag = false;
		if(ruler!=null) {
			this.ruler = ruler;
			//sRuler = new SingelRuler[sRuler.length];
//			for(int i=0 ; i<sRuler.length ; i++) {
//				sRuler[i] = new SingelRuler(this.ruler[i]);
//			}
			for(int i=0 ; i<ruler.length ; i++) {
				SingelRuler srRuler = new SingelRuler(ruler[i]);
				rulerSet.add(srRuler);
			}
		}
	}
	
	public boolean addSRT(SingelRuler ruler) {
		/* add an ruler is not used in this loop to SRT
		 * true: add successfully
		 * false: add failed
		 * */
		if(rulerSet.isEmpty()==true)return false;
		if(rulerSet.contains(ruler))return false;
		if(rulerSet.add(ruler)==true) {
			ChangeFlag = true;
			return true;
		}
		return false;
	}
	
	public boolean satisfySingelRuler(SingelRuler sRuler , InputFeature iFeature) {
		/* judge single ruler satisfy
		 * true: the ruler and the feature can get a new feature,
		 *  the feature that we used in this ruler set its isUsed to true
		 * false: the feature is not enough to get a new feature
		 * */
		boolean singleFeatureSatisfy = true;
		if(iFeature==null)
			return false;
		if(iFeature.IF.isEmpty()==true)
			return false;
		for (int i=0 ; i<sRuler.IFS.size() ; i++) {
			// for each ruler to get one of its new feature condition
			singleFeatureSatisfy = false;
			int featureID = sRuler.IFS.get(i);
			for(Feature feature : iFeature.IF) {
				// for each feature to judge
				if(feature.featureID == featureID) {
					singleFeatureSatisfy = true;
					break;
				}
			}
			if(singleFeatureSatisfy == false)
				// we judge in each loop
				// return false when a condition can not satisfy
				return false;
		}
		return true;
	}
}

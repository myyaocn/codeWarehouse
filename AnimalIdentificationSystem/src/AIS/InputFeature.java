package AIS;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;

public class InputFeature {
	// storage the input feature and the feature that we can release
	// by an ArrayList
	// IF storage the input feature
	// length is the size of the feature
	int lengthOfFeature;
	List<Feature> IF;
	public InputFeature(int length) {
		lengthOfFeature = length;
		IF = new ArrayList<Feature>();
	}
	
	public boolean addFeature(int i) {
		/* return true if the feature can be added
		 * return false that the feature can not be add
		 * re-write the arrayADD function with other name
		 * */
		Feature newFeature = new Feature();
		newFeature.isGoal = false;
		newFeature.isUsed = false;
		newFeature.featureID = i;
		if(i<lengthOfFeature && i>=0) {
			if(isInArray(i)==false) {
				IF.add(newFeature);
				return true;
			}else
				System.out.println("该特征已添加，请继续添加别的特征\n");
			
		}
		return false;
	}
	
	public void addDeductionFeature(int i) {
		Feature newFeature = new Feature();
		newFeature.isUsed = false;
		newFeature.featureID = i;
		if(i<lengthOfFeature) {
			newFeature.isGoal = false;
		}else {
			newFeature.isGoal = true;
		}
		if(isInArray(i)==false) {
			IF.add(newFeature);
		}
	}
	
	public boolean isInArray(int i) {
		/* judge the feature i in the IF arrayList
		 * 	true: the feature is in the IF arrayList
		 *  false: the feature is not in the IF arrayList 
		 *  */
		if(IF.isEmpty()==true) {
			return false;
		}
		for (Feature feature : IF) {
			if(feature.featureID==i)
				return true;
		}
		return false;
	}
	
	public boolean getAGoal() {
		// judege we get a goal finally or not
		boolean goalFlag = false;
		for(Feature feature : IF) {
			if(feature.isGoal==true && goalFlag==true)
				// which means we get two goal, is a mistake animal
				return false;
			if(feature.isGoal==true && goalFlag==false)
				goalFlag = true;
		}
		if(goalFlag == true)
			return true;
		return false;
	}
	
	public boolean getNullGoal() {
		for(Feature feature : IF) {
			if(feature.isGoal==true)
				return false;
		}
		return true;
	}
	
	public void findNoUsedFeature(InputFeature iFeature , InputFeature noUsedFeature) {
		for(int i=0 ; i<iFeature.IF.size() ; i++) {
			Feature feature = iFeature.IF.get(i);
			if(feature.isUsed==false)
				noUsedFeature.addDeductionFeature(feature.featureID);
		}
	}
	
	public Feature goal(Feature []fSet) {
		if(getAGoal()==true) {
			for(int i=0 ; i<IF.size() ; i++) {
				Feature feature = IF.get(i);
				if(feature.isGoal==true) {
					return fSet[feature.featureID];
				}
			}
		}
		return null;
	}
	
	public boolean allFeatureSatisfy(InputFeature iF , InputFeature judgeSet , RulerSet rs , int goal) {
		if(iF==null)return false;
		boolean setNewFlag = true;
		judgeSet.addDeductionFeature(goal);
		while(setNewFlag==true) {
			// find the goal all the feature
			setNewFlag = false;
			for(SingelRuler sr : rs.rulerSet) {
				// for each ruler in rulerSet
				if(judgeSet.IF!=null) {
					for(int i=0 ; i<judgeSet.IF.size() ; i++) {
						// for each feature in judgeSet
						Feature feature = judgeSet.IF.get(i);
						if(feature.featureID == sr.newFeature) {
							for(int j=0 ; j<sr.IFS.size() ; j++) {
								// for the satisfied ruler , add its feature
								int featureID = sr.IFS.get(j);
								if(judgeContain(judgeSet, featureID)==false) {
									setNewFlag = true;
									judgeSet.addDeductionFeature(featureID);
								}
							}
						}
					}
				}
			}
			if(setNewFlag==false)break;
		}
		for(int i=0 ; i<iF.IF.size() ; i++) {
			Feature feature = iF.IF.get(i);
			if(feature.isUsed==true)
				continue;
			else {
				if(featureInJudgeSet(judgeSet, feature.featureID)==true)
					continue;
				else return false;
			}
		}
		return true;
	}
	
	private boolean judgeContain(InputFeature judgeSet , int featureID) {
		for(int i=0 ; i<judgeSet.IF.size() ; i++) {
			Feature feature = judgeSet.IF.get(i);
			if(feature.featureID == featureID)
				return true;
		}
		return false;
	}
	
	private boolean featureInJudgeSet(InputFeature judgeSet , int featureID) {
		for(int i=0 ; i<judgeSet.IF.size() ; i++) {
			Feature feature = judgeSet.IF.get(i);
			if(featureID==feature.featureID)
				return true;
		}
		return false;
	}
	
}

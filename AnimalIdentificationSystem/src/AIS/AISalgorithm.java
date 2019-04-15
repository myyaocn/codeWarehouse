package AIS;

import java.util.Scanner;

public class AISalgorithm {
	String []feature;
	String []goal;
	int [][]ruler;
	public AISalgorithm(String []feature , String []goal , int [][]ruler) {
		this.feature = feature;
		this.goal = goal;
		this.ruler = ruler;
	}
	
	public void start() {
		int num;
		// srt means the the set of the ruler to be tested
		// deductionRulers storage all the ruler to be used in this system
		RulerSet srt = new RulerSet(null);
		RulerSet deductionRules = new RulerSet(ruler);
		InputFeature inputFeature = new InputFeature(feature.length);
		Feature[] fSets = new Feature[feature.length + goal.length];
		// SingelRuler[] rulers = new SingelRuler[ruler.length];
		Scanner sc = new Scanner(System.in); 
		// judge Set
		InputFeature judgeSet = new InputFeature(feature.length);
		
		// for all the feature to storage in this system
		for(int i=0 ; i<feature.length;i++) {
			fSets[i] = new Feature();
			fSets[i].isGoal = false;
			fSets[i].featureID = i;
			fSets[i].isUsed = false;
			fSets[i].feature = new String(feature[i]);
		}
		for(int i=feature.length ; i<fSets.length ; i++) {
			fSets[i] = new Feature();
			fSets[i].feature = goal[i-feature.length];
			fSets[i].isGoal = true;
			fSets[i].featureID = i;
			fSets[i].isUsed = false;
		}
		
		for(int i=0 ; i<feature.length ; i++) {
			System.out.print(i + " " + feature[i] + "        ");
			if(i%5==4)
				System.out.println();
		}
		
		System.out.println("请输入要添加的特征，输入-1表示结束：");
		num = sc.nextInt();
		while(num!=-1) {
			if(inputFeature.isInArray(num)==false)
				inputFeature.addFeature(num);
			else System.out.println("该特征已添加，请输入其他的特征，输入-1表示结束：");
			num = sc.nextInt();
			if(num==-1)break;
		}
		System.out.println("输入完成，进行启发式推理");
		// the first loop, find the satisfy ruler
		for(SingelRuler sRuler : deductionRules.rulerSet) {
			if(deductionRules.satisfySingelRuler(sRuler, inputFeature)==true) {
				inputFeature.addDeductionFeature(sRuler.newFeature);
			}
			else {
				srt.addSRT(sRuler);
				srt.ChangeFlag = true;
			}
		}
		// the second loop, end when the changeflag = false
		while(srt.ChangeFlag==true) {
			srt.ChangeFlag = false;
			for(SingelRuler sRuler : srt.rulerSet) {
				if(srt.satisfySingelRuler(sRuler, inputFeature)==true) {
					inputFeature.addDeductionFeature(sRuler.newFeature);
					srt.ChangeFlag = true;
				}
			}
			if(srt.ChangeFlag==false)break;
		}

		// System.out.println(inputFeature.getAGoal());
		if(inputFeature.getAGoal()==true) {
			Feature goal = inputFeature.goal(fSets);
			if(goal!=null) {
				int goalID = goal.featureID;
				if(inputFeature.allFeatureSatisfy(inputFeature , judgeSet , deductionRules , goalID)==true) {
					System.out.println("此次推理出的动物是："+goal.feature);
				}else {
					System.out.println("条件输入错误，无法判断动物类型");
				}
			}
		}else if(inputFeature.getNullGoal()==true) {
			// can not get a goal, so we find its feature
			System.out.println("条件不足，无法推出动物类型");
			InputFeature noUsedFeature = new InputFeature(inputFeature.lengthOfFeature);
			noUsedFeature.findNoUsedFeature( inputFeature , noUsedFeature );
			if(noUsedFeature.IF.isEmpty()==true)
				System.out.println("条件不足，无法推出动物类型");
			else {
				System.out.println("输入条件不足，但推理机已推出该动物有下列的特征：");
				for(Feature feature : noUsedFeature.IF) {
					System.out.print(fSets[feature.featureID].feature+" ");
				}
				System.out.println();
			}
		}else {
			// more than one animal
			System.out.println("条件输入错误，无法推出动物类型");
		}
		
		System.out.println("推理完成");
	}
}

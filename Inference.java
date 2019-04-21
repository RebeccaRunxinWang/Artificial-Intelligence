import java.util.*;
import java.io.*; 

public class Inference {
    /*
    sample[0]: Weather 
    0 - sunny 
    1 - cloudy
    2 - rain 
    3 - snow

    sample[1]: Tired
    0 - True 
    1 - False

    sample[2]: Happy
    0 - True 
    1 - False
    */

    //generate samples for reject sampling
    public int[] sample(){
    	int[] sample = new int[3];
    	double r1 = Math.random();
    	if(r1<0.4){
    		sample[0]=0; //sunny
    	} else if(r1<0.7){
    		sample[0]=1; //cloudy
    	} else if(r1<0.9){
    		sample[0]=2;  //rain
    	} else {
    		sample[0]=3;  //snow
    	}
        double r2 = Math.random();
        if(r2<0.3){
    		sample[1]=0; //true
    	} else {
    		sample[1]=1; //false
    	}
    	// 0:happy, 1:unhappy
    	if(sample[0]==0&& sample[1]==0) {
    		double r3 = Math.random();
    		if(r3<0.5) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==0 && sample[1]==1){
    		double r4 = Math.random();
    		if(r4<0.7) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==1 && sample[1]==0){
    		double r5 = Math.random();
    		if(r5<0.1) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==1 && sample[1]==1){
    		double r6 = Math.random();
    		if(r6<0.3) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==2 && sample[1]==0){
    		double r7 = Math.random();
    		if(r7 <0.1) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==2 && sample[1]==1){
    		double r8 = Math.random();
    		if(r8< 0.2) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==3 && sample[1]==0){
    		double r9 = Math.random();
    		if(r9<0.6) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==3 && sample[1]==1){
    		double r10 = Math.random();
    		if(r10<0.8) sample[2] = 0;
            else sample[2] = 1;
    	}
    	return sample;
    }



    // generate samples for likelihood weighting, return the sample and the w
    public double[] likehoodsample(int we, int t, int h){
    	double w =1.0;
      // The first three numbers represent the sample, the last is weight for this sample
    	double[] sample = new double[4];
    	sample[0]=we;
    	sample[1]=t;
    	if(we==0) w=w*0.4;
    	else if(we==1) w=w*0.3;
    	else if(we==2) w=w*0.2;
    	else if(we==3) w=w*0.1;
      // no evidence
    	else { 
    	double r1 = Math.random();
    	if(r1<0.4){
    		sample[0]=0; //sunny
    	} else if(r1<0.7){
    		sample[0]=1; //cloudy
    	} else if(r1<0.9){
    		sample[0]=2;  //rain
    	} else {
    		sample[0]=3;  //snow
    	}
    	}
        
      if(t==0) w=w*0.3;
    	else if(t==1) w=w*0.7;
      // no evidence
    	else {	
        double r2 = Math.random();
        if(r2<0.3){
    		sample[1]=0; //true
    	} else {
    		sample[1]=1; //false
    	}
    	}
        
      if(h==0) {
          if(sample[0]==0 && sample[1]==0) w=w*0.5;
          else if(sample[0]==0 && sample[1]==1) w=w*0.7;
          else if(sample[0]==1 && sample[1]==0) w=w*0.1;
          else if(sample[0]==1&& sample[1]==1) w=w*0.3;
          else if(sample[0]==2&& sample[1]==0) w=w*0.1;
          else if(sample[0]==2&& sample[1]==1) w=w*0.2;
          else if(sample[0]==3&& sample[1]==0) w=w*0.6;
          else if(sample[0]==3&& sample[1]==1) w=w*0.8;
        }
    	else if(h==1) {
          if(sample[0]==0 && sample[1]==0) w=w*0.5;
          else if(sample[0]==0 && sample[1]==1) w=w*0.3;
          else if(sample[0]==1 && sample[1]==0) w=w*0.9;
          else if(sample[0]==1&& sample[1]==1) w=w*0.7;
          else if(sample[0]==2&& sample[1]==0) w=w*0.9;
          else if(sample[0]==2&& sample[1]==1) w=w*0.8;
          else if(sample[0]==3&& sample[1]==0) w=w*0.4;
          else if(sample[0]==3&& sample[1]==1) w=w*0.2;
    	}
      // no evidence
    	else {	
         if(sample[0]==0&& sample[1]==0) {
    		double r3 = Math.random();
    		if(r3<0.5) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==0 && sample[1]==1){
    		double r4 = Math.random();
    		if(r4<0.7) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==1 && sample[1]==0){
    		double r5 = Math.random();
    		if(r5<0.1) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==1 && sample[1]==1){
    		double r6 = Math.random();
    		if(r6<0.3) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==2 && sample[1]==0){
    		double r7 = Math.random();
    		if(r7 <0.1) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==2 && sample[1]==1){
    		double r8 = Math.random();
    		if(r8< 0.2) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==3 && sample[1]==0){
    		double r9 = Math.random();
    		if(r9<0.6) sample[2] = 0;
            else sample[2] = 1;
    	} else if (sample[0]==3 && sample[1]==1){
    		double r10 = Math.random();
    		if(r10<0.8) sample[2] = 0;
            else sample[2] = 1;
    	}

    	}
    	sample[3]=w;
    	return sample;
   
    }

    public void reject(int n){
       // i
       double rain =0;
       for(int i=0; i<n; i++){
       	 int[] s = sample();
       	 if(s[0]==2) rain++;
       }
       System.out.println("i: "+ rain/n);

       //ii
       double tired =0;
       double valid =0;
       for(int i=0; i<n; i++){
       	 int[] s = sample();
       	 if(s[2]==1) {
       	 	valid++;
            if(s[1]==0) {tired++;
            	 //System.out.println(Arrays.toString(s));
            }
           
         }
       }
       System.out.println("ii: "+ tired/valid);

       //iii
       double happy =0;
       double valid3 =0;
       for(int i=0; i<n; i++){
       	 int[] s = sample();
       	 if(s[0]==0) {
       	 	valid3++;
            if(s[2]==0) {happy++;
            	 //System.out.println(Arrays.toString(s));
            }
           
         }
       }
       System.out.println("iii: "+ happy/valid3);


       //iv
       double snow =0;
       double valid4 =0;
       for(int i=0; i<n; i++){
       	 int[] s = sample();
       	 if(s[2]==0 && s[1]==1) {
       	 	valid4++;
            if(s[0]==3) {snow++;
            	 //System.out.println(Arrays.toString(s));
            }
           
         }
       }
       System.out.println("iv: "+ snow/valid4);

    }

    public void like(int n){
       
       //i. P(​rain​)
       double sum1=0;
       double rain=0;
       for(int i=0; i<n; i++){
       	  double[] s = likehoodsample(-1,-1,-1);
          sum1=sum1+s[3];
          if(s[0]==2) rain=rain+s[3];
       }
       System.out.println("i: "+ rain/sum1);

       //ii. P(​tired​ | ​¬happy)​
       double sum2=0;
       double tired=0;
       for(int i=0; i<n; i++){
       	  double[] s = likehoodsample(-1,-1,1);
          sum2=sum2+s[3];
          if(s[1]==0) tired=tired+s[3];
       }
       System.out.println("ii: "+ tired/sum2);
 
       //iii. P(​happy​ | ​sunny​)
       double sum3=0;
       double happy​=0;
       for(int i=0; i<n; i++){
       	  double[] s = likehoodsample(0,-1,-1);
          sum3=sum3+s[3];
          if(s[2]==0) happy​=happy​+s[3];
       }
       System.out.println("iii: "+ happy/sum3);

       //iv. P(​snow​ | ​happy​, ​¬tired)​
       double sum4=0;
       double snow=0;
       for(int i=0; i<n; i++){
       	  double[] s = likehoodsample(-1,1,0);
          sum4=sum4+s[3];
          if(s[0]==3) snow= snow+s[3];
       }
       System.out.println("iv: "+ snow/sum4);



    }

    public static void main(String[] args){
    	if(args[0].equals("reject")) {
    		new Inference().reject(Integer.parseInt(args[1]));
    	}
    	else if(args[0].equals("like")) {
    		new Inference().like(Integer.parseInt(args[1]));
    	}
    }






}
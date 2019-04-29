import java.util.*;
import java.io.*; 

public class CongressTree {
  // Nay:0, Yea:1
 private static String classvalue1;
 private static String classvalue2;
 public Node DecisionTreeLearning(List<Example> examples, List<String> attributes, List<Example> parent_examples, int depth, int limit) {
    if(examples.size()==0) return new Node(plurality(parent_examples)); //return result node
    else if(!sameclass(examples).equals("NotSame")) return new Node(sameclass(examples)); //return result node
    else if(attributes.size()==0 || depth>=limit) return new Node(plurality(examples)); //return result node
    else{
    	String A=attributes.get(0);
    	double argmax=0;
    	for(String a: attributes){
    		//System.out.println(a+".."+ig(a,examples));
    		if(ig(a,examples)>argmax) {
    			A=a;
    			argmax=ig(a,examples);
    		}
    	}
    	//System.out.println("*"+A+argmax+" "+depth);
    	Node root = new Node(A);
    	List<Example> exs0 = new ArrayList<Example>(); // left: 0
    	List<Example> exs1 = new ArrayList<Example>(); //right: 1
 	    for(int i=0; i<examples.size(); i++){
 	   	  Example ex = examples.get(i);
 		  if(ex.vote.get(A)==0) exs0.add(ex);
 		  else exs1.add(ex);
 	    }
 	    attributes.remove(A);
 	    Node subtree0 = DecisionTreeLearning(exs0,attributes,examples, depth+1, limit);
 	    root.left = subtree0;
 	    Node subtree1 = DecisionTreeLearning(exs1,attributes,examples, depth+1, limit);
 	    root.right = subtree1;
 	    return root;
    }

 }

 public double ig(String a, List<Example> examples){
 	List<Example> a0 = new ArrayList<Example>();
 	List<Example> a1 = new ArrayList<Example>();
 	for(int i=0; i<examples.size(); i++){
 		Example ex = examples.get(i);
 		if(ex.vote.get(a)==0) a0.add(ex);
 		else a1.add(ex);
 	}
    //System.out.println("(((("+a+a0.size());
 	//System.out.println("(((("+a+a1.size());
 	double ep = -(entropy(classvalue1,examples)+entropy(classvalue2,examples));
    //System.out.println(ep);
    double p0 = p(a, 0, examples);
    double p1 = p(a, 1, examples); 
    //System.out.println("p0"+p0);
    //System.out.println("p1"+p1);
    double e0 = -(entropy(classvalue1,a0)+entropy(classvalue2,a0));
    //System.out.println(e0);
    double e1 = -(entropy(classvalue1,a1)+entropy(classvalue2,a1));
    //System.out.println(e1);
    //System.out.print(a+" ");
    //System.out.println(ep - (p0*e0+p1*e1));
    return ep - (p0*e0+p1*e1);
 }

 public double entropy(String classvalue, List<Example> examples){
 	if(p(classvalue,examples)==0) return 0;
 	else return p(classvalue,examples)*Math.log(p(classvalue,examples))/ Math.log(2);
 }

 /*
   Return the plurality value of a label. 
 */
 
 public String plurality(List<Example> examples){
 	double countvalue1 = 0;
 	double total = examples.size();
 	String common = classvalue1;
 	for(Example ex:examples){
 		if(ex.party.equals(classvalue1)) countvalue1++;
 	}
    if(countvalue1/total <0.5) common =classvalue2;
    return common;
 }
 
 /*
   Return the same class or "NotSame" if the examples do not have a same class.
 */
 public String sameclass(List<Example> examples){
 	 String value = examples.get(0).party;
     for(Example ex:examples){
 		if(!ex.party.equals(value)) return "NotSame";
 	 }
 	 return value;
 }

  public double p(String v, List<Example> examples){
 	 double vcount = 0;
 	 double total = examples.size();
     for(Example ex:examples){
 		if(ex.party.equals(v)) vcount++;
 	 }
 	// System.out.println(v+vcount/total);
 	 return vcount/total;
 }

  public double p(String vote, int value, List<Example> examples){
 	 double t = 0;
 	 double total = examples.size();
     for(Example ex:examples){
 		if(ex.vote.get(vote)==value) t++;
 	 }
 	 return t/total;
 }

  public static void printtree(Node root, int depth){
  	if(root==null || root.value.equals(classvalue1) || root.value.equals(classvalue2)) return;
    for(int i=0; i<depth; i++){
  	   System.out.print('\t');
    }
    System.out.println(root.value);
    //&& root.left.value>=0  
    if(root.left!=null ){   
      printtree(root.left,depth+1);
    }
    // && root.right.value>=0
    if(root.right!=null) {
      printtree(root.right,depth+1);
    }
  }

  public static double accuracy(Node root, List<Example> test){
    double correct =0;
    double total = test.size();
  	for(Example ex: test){
     //System.out.println("count");
     Node current = root;
     while(!current.value.equals(classvalue1) && !current.value.equals(classvalue2)) {
       String label = current.value;
     //  System.out.println("ii "+current.value+" "+ex.vote.get(num));
       if(ex.vote.get(label)==0) {
       	current = current.left;
      // 	System.out.println("-- "+current.value+" ");
       }
       else {
       	current = current.right;
      // 	System.out.println("-- "+current.value+" ");
       }
     }
     if(current.value.equals(classvalue1) && ex.party.equals(classvalue1)) correct++;
     else if (current.value.equals(classvalue2) && ex.party.equals(classvalue2)) correct++;
     //System.out.println("case"+current.value+" "+ex.party);
    }

    return correct/total;
  }

  public static class Example {
   String party;
   Map<String, Integer> vote;
  }

  private class Node {
    String value;
    Node left;
    Node right;
 
    Node(String value) {
        this.value = value;
        right = null;
        left = null;
    }
  } 

 public static void main(String[] args) throws IOException  {
   BufferedReader br = new BufferedReader(new FileReader(args[0]));
   int depthlimit = Integer.parseInt(args[1]);
   //System.out.println(depthlimit);     
   List<Example> data = new ArrayList<Example>();
   List<String> attributes = new ArrayList<String>();
   String label = br.readLine(); // column labels
   String[] columns = label.split(",");
   for(int m=0; m<columns.length-1; m++){
   	attributes.add(columns[m]);
   }
   
   String line;
   while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        Example ex = new Example();
        Map<String, Integer> records = new HashMap<String, Integer>();
        for(int i=0; i<values.length-1; i++){ // for attribute i  
        	Integer v; 
        	double ran = Math.random();
        	if(ran<0.5) v=0;
        	else v=1;
        	if(values[i].equals("Nay")) v=0;
        	else if(values[i].equals("Yea")) v=1;
        	records.put(attributes.get(i), v);
        	//System.out.println(attributes.get(i)+" "+v);  
        } 
            ex.party = values[values.length-1];
            if(classvalue1==null) classvalue1 = ex.party;
            else if(classvalue2==null && !classvalue1.equals(ex.party)) classvalue2 = ex.party;
            ex.vote = new HashMap<String, Integer>(records);
            data.add(ex);
           for (String vote: ex.vote.keySet()){
            Integer value = ex.vote.get(vote);  
            //System.out.println(vote+ " " + value);
           }
            // System.out.println(ex.party);  
   }
   //5-fold
   List<Example> f1_train = new ArrayList<Example>();
   List<Example> f1_test = new ArrayList<Example>();
   List<Example> f2_train = new ArrayList<Example>();
   List<Example> f2_test = new ArrayList<Example>();
   List<Example> f3_train = new ArrayList<Example>();
   List<Example> f3_test = new ArrayList<Example>();
   List<Example> f4_train = new ArrayList<Example>();
   List<Example> f4_test = new ArrayList<Example>();
   List<Example> f5_train = new ArrayList<Example>();
   List<Example> f5_test = new ArrayList<Example>();
   for(int m=0; m<data.size(); m++){
   	if(m%5==0) f5_test.add(data.get(m));
   	else if(m%5==1) f4_test.add(data.get(m));
   	else if(m%5==2) f3_test.add(data.get(m));
   	else if(m%5==3) f2_test.add(data.get(m));
   	else f1_test.add(data.get(m));
   }
   
   f1_train.addAll(f2_test);
   f1_train.addAll(f3_test);
   f1_train.addAll(f4_test);
   f1_train.addAll(f5_test);
   f2_train.addAll(f1_test);
   f2_train.addAll(f3_test);
   f2_train.addAll(f4_test);
   f2_train.addAll(f5_test);  
   f3_train.addAll(f2_test);
   f3_train.addAll(f1_test);
   f3_train.addAll(f4_test);
   f3_train.addAll(f5_test); 
   f4_train.addAll(f2_test);
   f4_train.addAll(f3_test);
   f4_train.addAll(f1_test);
   f4_train.addAll(f5_test);
   f5_train.addAll(f2_test);
   f5_train.addAll(f3_test);
   f5_train.addAll(f4_test);
   f5_train.addAll(f1_test);

    Node root1 = new CongressTree().DecisionTreeLearning(f1_train, new ArrayList<String>(attributes), f1_train,0,depthlimit);
    Node root2 = new CongressTree().DecisionTreeLearning(f2_train, new ArrayList<String>(attributes), f2_train,0,depthlimit);
    Node root3 = new CongressTree().DecisionTreeLearning(f3_train, new ArrayList<String>(attributes), f3_train,0,depthlimit);
    Node root4 = new CongressTree().DecisionTreeLearning(f4_train, new ArrayList<String>(attributes), f4_train,0,depthlimit);
    Node root5 = new CongressTree().DecisionTreeLearning(f5_train, new ArrayList<String>(attributes), f5_train,0,depthlimit);
    printtree(root1,0);
    System.out.print("\n");
    printtree(root2,0);
    System.out.print("\n");
    printtree(root3,0); 
    System.out.print("\n");
    printtree(root4,0);  
    System.out.print("\n");
    printtree(root5,0);  
    System.out.print("\n");     
    double a1 = accuracy(root1, f1_test);
    double a2 = accuracy(root2, f2_test);
    double a3 = accuracy(root3, f3_test);
    double a4 = accuracy(root4, f4_test);
    double a5 = accuracy(root5, f5_test);
    System.out.println(a1+" "+a2+" "+a3+" "+a4+" "+a5+" "+(a1+a2+a3+a4+a5)/5);
   
 }

}
import java.util.*;
import java.io.*; 

public class Solver {
    private int numfront;
    private int expanded;

    public boolean bfs(Node start){
      numfront =0;
      expanded =0;
      Queue<Node> frontier = new LinkedList<Node>();
      Set<String> setf = new HashSet<String>();  
      if(isGoal(start)) {
      	    prints(start);
          	return true;
      }
      frontier.add(start);
      numfront++;
      StringBuilder sb = new StringBuilder();
      for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
           sb.append(start.matrix[i][j]);
         }
      }          
      //System.out.println(sb.toString());
      setf.add(sb.toString());
      Set<String> explored = new HashSet<String>();
      while (!setf.isEmpty()) {
        Node node = frontier.remove();
        StringBuilder sb2 = new StringBuilder();
        for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
           sb2.append(node.matrix[i][j]);
         }
        }
     //   System.out.println(sb2.toString());
        setf.remove(sb2.toString());
        explored.add(sb2.toString());
        expanded++;
        Set<Node> expand = expand(node);
        for (Node n :expand){
            StringBuilder sb3 = new StringBuilder();
           for(int i=0; i<3; i++){
           for(int j=0; j<3; j++){
           sb3.append(n.matrix[i][j]);
           }
          }

          String ns = sb3.toString();
           //    System.out.println(ns);
          if(setf.contains(ns)==false && explored.contains(ns)==false) {
          	if(isGoal(n)) {
          		prints(n);
          		return true;
          	 }
          	else{
          	 frontier.add(n);
          	 numfront++;
             setf.add(ns);
          	}
          }
        }
      }
      System.out.println("No solution\n");
      System.out.println("path cost: ");
      System.out.println("frontier: "+numfront);
      System.out.println("expanded: "+expanded);
      return false;

    }

    public boolean ucost(Node start){
      numfront =0;
      expanded =0;
      PriorityQueue<Node> frontier = new PriorityQueue<Node>(new ucostComparator()); 
      Set<String> setf = new HashSet<String>();  
      frontier.add(start);
      numfront++;
      StringBuilder sb = new StringBuilder();
      for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
           sb.append(start.matrix[i][j]);
         }
      }          
      //System.out.println(sb.toString());
      setf.add(sb.toString());
      Set<String> explored = new HashSet<String>();
      while (!setf.isEmpty()) {
        Node node = frontier.poll();
      //  System.out.println(node.cost);
        if(isGoal(node)) {
            prints(node);
          	return true;
        }
        StringBuilder sb2 = new StringBuilder();
        for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
           sb2.append(node.matrix[i][j]);
         }
        }
      //  System.out.println(sb2.toString());
        setf.remove(sb2.toString());
        explored.add(sb2.toString());
        expanded++;
        Set<Node> expand = expand(node);
        for (Node n :expand){
            StringBuilder sb3 = new StringBuilder();
           for(int i=0; i<3; i++){
           for(int j=0; j<3; j++){
           sb3.append(n.matrix[i][j]);
           }
          }

          String ns = sb3.toString();
           //    System.out.println(ns);
          if(setf.contains(ns)==false && explored.contains(ns)==false) {
          	 frontier.add(n);
          	 numfront++;
             setf.add(ns);
          	
          } else if(setf.contains(ns)) {
            Node c = frontier.peek();
          	Iterator<Node> iterator= frontier.iterator();
		    while(iterator.hasNext()){
		     c =iterator.next();
		   StringBuilder sb4 = new StringBuilder();
           for(int i=0; i<3; i++){
           for(int j=0; j<3; j++){
           sb4.append(c.matrix[i][j]);
           }
          }
          String cs = sb4.toString();
      if(cs.equals(ns)) break;
      }
        if(c.cost > n.cost) {
          frontier.remove(c);
          frontier.add(n);
        }
          }
        }
      }
      System.out.println("No solution\n");
      System.out.println("path cost: ");
      System.out.println("frontier: "+numfront);
      System.out.println("expanded: "+expanded);
      return false;


    }

    public boolean greedy(Node start) {
      numfront =0;
      expanded =0;
      PriorityQueue<Node> frontier = new PriorityQueue<Node>(new greedyComparator()); 
      Set<String> setf = new HashSet<String>();  
      frontier.add(start);
      numfront++;
      StringBuilder sb = new StringBuilder();
      for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
           sb.append(start.matrix[i][j]);
         }
      }          
      //System.out.println(sb.toString());
      setf.add(sb.toString());
      Set<String> explored = new HashSet<String>();
      while (!setf.isEmpty()) {
        Node node = frontier.poll();
      //  System.out.println(node.cost);
        if(isGoal(node)) {
            prints(node);
          	return true;
        }
        StringBuilder sb2 = new StringBuilder();
        for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
           sb2.append(node.matrix[i][j]);
         }
        }
      //  System.out.println(sb2.toString());
        setf.remove(sb2.toString());
        explored.add(sb2.toString());
        expanded++;
        Set<Node> expand = expand(node);
        for (Node n :expand){
            StringBuilder sb3 = new StringBuilder();
           for(int i=0; i<3; i++){
           for(int j=0; j<3; j++){
           sb3.append(n.matrix[i][j]);
           }
          }

          String ns = sb3.toString();
           //    System.out.println(ns);
          if(setf.contains(ns)==false && explored.contains(ns)==false) {
          	 frontier.add(n);
          	 numfront++;
             setf.add(ns);
          	
          } else if(setf.contains(ns)) {
            Node c = frontier.peek();
          	Iterator<Node> iterator= frontier.iterator();
		    while(iterator.hasNext()){
		   StringBuilder sb4 = new StringBuilder();
           for(int i=0; i<3; i++){
           for(int j=0; j<3; j++){
           sb4.append(c.matrix[i][j]);
           }
          }
          String cs = sb4.toString();
         if(cs.equals(ns)) break;
          c =iterator.next();
        }
        if(c.cost> n.cost) {
          frontier.remove(c);
          frontier.add(n);
        }
          }
        }
      }
      System.out.println("No solution\n");
      System.out.println("path cost: ");
      System.out.println("frontier: "+numfront);
      System.out.println("expanded: "+expanded);
      return false;
    }


    public boolean astar(Node start) {
      numfront =0;
      expanded =0;
      PriorityQueue<Node> frontier = new PriorityQueue<Node>(new astarComparator()); 
      Set<String> setf = new HashSet<String>();  
      frontier.add(start);
      numfront++;
      StringBuilder sb = new StringBuilder();
      for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
           sb.append(start.matrix[i][j]);
         }
      }          
      //System.out.println(sb.toString());
      setf.add(sb.toString());
      Set<String> explored = new HashSet<String>();
      while (!setf.isEmpty()) {
        Node node = frontier.poll();
      //  System.out.println(node.cost);
        if(isGoal(node)) {
            prints(node);
          	return true;
        }
        StringBuilder sb2 = new StringBuilder();
        for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
           sb2.append(node.matrix[i][j]);
         }
        }
      //  System.out.println(sb2.toString());
        setf.remove(sb2.toString());
        explored.add(sb2.toString());
        expanded++;
        Set<Node> expand = expand(node);
        for (Node n :expand){
            StringBuilder sb3 = new StringBuilder();
           for(int i=0; i<3; i++){
           for(int j=0; j<3; j++){
           sb3.append(n.matrix[i][j]);
           }
          }
          String ns = sb3.toString();
           //    System.out.println(ns);
          if(setf.contains(ns)==false && explored.contains(ns)==false) {
          	 frontier.add(n);
          	 numfront++;
             setf.add(ns);
          } else if(setf.contains(ns)) {
          	Iterator<Node> iterator= frontier.iterator();
          	Node c = frontier.peek();
		        while(iterator.hasNext()){
		          StringBuilder sb4 = new StringBuilder();
              for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                 sb4.append(c.matrix[i][j]);
                }
              }
              String cs = sb4.toString();
			        if(cs.equals(ns)) break;
              c =iterator.next();
		        }
		      	if(c.cost > n.cost) {
              frontier.remove(c);
              frontier.add(n);
            }
          }
        }
      }
      
      System.out.println("No solution\n");
      System.out.println("path cost: ");
      System.out.println("frontier: "+numfront);
      System.out.println("expanded: "+expanded);
      return false;
    }


    class ucostComparator implements Comparator<Node>{ 
              
            // Overriding compare()method of Comparator  
                        // for descending order of cgpa 
            public int compare(Node n1, Node n2) { 
                if (n1.cost > n2.cost) 
                    return 1; 
                else if (n1.cost < n2.cost) 
                    return -1; 
                return 0; 
                } 
    }


    class greedyComparator implements Comparator<Node>{ 
              
            // Overriding compare()method of Comparator  
                        // for descending order of cgpa 
            public int compare(Node n1, Node n2) { 
                if (n1.dis > n2.dis) 
                    return 1; 
                else if (n1.dis < n2.dis) 
                    return -1; 
                return 0; 
                } 
    } 
 

    class astarComparator implements Comparator<Node>{ 
              
            // Overriding compare()method of Comparator  
                        // for descending order of cgpa 
            public int compare(Node n1, Node n2) { 
                if (n1.cost+n1.dis > n2.cost+n2.dis) 
                    return 1; 
                else if (n1.cost+n1.dis < n2.cost+n2.dis) 
                    return -1; 
                return 0; 
                } 
    } 
   

    public Set<Node> expand(Node node){
        Set<Node> expand = new HashSet<Node>();
        if(node.y-1>=0){
        int[][] newmatrix = new int[3][3];
        for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
             newmatrix[i][j] = node.matrix[i][j];
         }
        }
          newmatrix[node.x][node.y-1] = 0;
          newmatrix[node.x][node.y] = node.matrix[node.x][node.y-1];
       	  Node right = new Node(newmatrix, node.x, node.y-1, node.cost+node.matrix[node.x][node.y-1], "R");
          right.parent = node;
          expand.add(right);
          for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
               //     System.out.print(right.matrix[i][j]+" ");
            }
          }
               //    System.out.println("\n");
       }
       if(node.y+1<=2){
       	    int[][] newmatrix2 = new int[3][3];
            for(int i=0; i<3; i++){
              for(int j=0; j<3; j++){
                newmatrix2[i][j] = node.matrix[i][j];
              }
            }
          newmatrix2[node.x][node.y+1] = 0;
          newmatrix2[node.x][node.y] = node.matrix[node.x][node.y+1];
       	  Node left = new Node(newmatrix2, node.x, node.y+1, node.cost+node.matrix[node.x][node.y+1], "L");
          left.parent = node;
          expand.add(left);
       }
        if(node.x-1>=0){
       	    int[][] newmatrix3 = new int[3][3];
            for(int i=0; i<3; i++){
              for(int j=0; j<3; j++){
                  newmatrix3[i][j] = node.matrix[i][j];
              } 
            }
          newmatrix3[node.x-1][node.y] = 0;
          newmatrix3[node.x][node.y] = node.matrix[node.x-1][node.y];
       	  Node down = new Node(newmatrix3, node.x-1, node.y, node.cost+node.matrix[node.x-1][node.y], "D");
          down.parent = node;
          expand.add(down);
        }

        if(node.x+1<=2){
       	    int[][] newmatrix4 = new int[3][3];
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                  newmatrix4[i][j] = node.matrix[i][j];
                }
            }
          newmatrix4[node.x+1][node.y] = 0;
          newmatrix4[node.x][node.y] = node.matrix[node.x+1][node.y];
       	  Node up = new Node(newmatrix4, node.x+1, node.y, node.cost+node.matrix[node.x+1][node.y],"U");
          up.parent = node;
          expand.add(up);
        }
      // System.out.println("end ");
        return expand;
    }

    public boolean isGoal(Node node){
    	if(node.matrix[0][0]==0 && node.matrix[0][1]==1 && node.matrix[0][2]==2 && node.matrix[1][0]==3 &&
    		node.matrix[1][1]==4 && node.matrix[1][2]==5 && node.matrix[2][0]==6 && node.matrix[2][1]==7 
    		&& node.matrix[2][2]==8) return true;
    	else return false;
    }

    public void prints(Node node){
    	//System.out.println("print");
    	Node goal = node;
    	Stack<Node> s = new Stack<Node>();

    	while(node.parent!=null){
           s.push(node);
           node=node.parent;
    	}
    	s.push(node);
    	do {
    	   Node n = s.pop();
    	   for(int i=0; i<3; i++){
              for(int j=0; j<3; j++){
                  if(n.matrix[i][j]==0) System.out.print(". ");
                  else System.out.print(n.matrix[i][j]+" ");
              }
              System.out.print("\n");
           }
           System.out.print("\n");
    	} while(!s.empty());
    	System.out.println("path cost: "+goal.cost);
    	System.out.println("frontier: "+numfront);
    	System.out.println("expanded: "+expanded);
        
    }
    
    // compute the weighted Manhattan distance: cost*((xc-xg)+(yc-yg)) for each tile
    public static int distance(int[][] matrix){
    	int dis=0;
    	int[] arr = {0,1,2,1,2,3,2,3,4};
    	for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
              int c = matrix[i][j];
              dis = dis+c*(Math.abs(i+j-arr[c]));
            }
        }
        return dis;
    }


    private static class Node {
 	private int[][] matrix; // 3*3
 	private int x,y; // blank
 	private Node parent;
 	private int cost; 
 	private int dis;
 	private String action;

 	public Node(int[][] matrix, int x, int y, int cost, String action) {
        this.matrix = matrix;
        this.x = x;
        this.y = y;
        parent = null;
        this.cost = cost;
        this.dis = distance(matrix);
        this.action = action; 
    }
  } 

  public static void main(String[] args) throws IOException {
  	    File file = new File(args[1]); 
        BufferedReader br = new BufferedReader(new FileReader(file));
        int[][] matrix = new int[3][3];
        int x = -1;
        int y = -1;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){ 
              int c = br.read();
              while(c!=-1){
              	 if(c== (int)('.')) {
              	 	matrix[i][j] = 0;
              	 	x = i;
              	 	y = j;
              	    break;
              	  }
                  else if (c!=(int)(' ') && c!=(int)('\n')) {
                 	matrix[i][j] = Character.getNumericValue((char)c);
                 	break;
                  } else c = br.read();
               }
            }
        }
        Node start = new Node(matrix, x, y, 0, null);
        if(args[0].equals("bfs")) {
          new Solver().bfs(start);
        } else if(args[0].equals("ucost")) {
          new Solver().ucost(start);
        } else if(args[0].equals("greedy")) {
          new Solver().greedy(start);
        } else if(args[0].equals("astar")) {
          new Solver().astar(start);
        } 

  }

 }


   

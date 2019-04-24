import java.util.*;
import java.io.*; 

public class Resolver {
   /*
   apply the resolution rules and print the true/false and count 
   */
   public void resolution(Set<Set<Integer>> kb, Set<Set<Integer>> a) {
      int count =0;
      // the clauses set
      Set<Set<Integer>> list = new HashSet<Set<Integer>>();
      list.addAll(kb);
      list.addAll(a);
      // if the kb and a are empty: return false 0
      if(list.size()==0){
        System.out.println("false "+count);
        return;
      }      
      // the new set
      Set<Set<Integer>> newc = new HashSet<Set<Integer>>();
      // the explored set of pairs of clauses
      Set<Set<Set<Integer>>> explored = new HashSet<Set<Set<Integer>>>();
      while(true){
        for(Set<Integer> s1: list){
          for(Set<Integer> s2: list) {
             if(s1.equals(s2)) continue;
             Set<Set<Integer>> cur = new HashSet<Set<Integer>>(); 
             cur.add(s1);
             cur.add(s2);
             if(explored.contains(cur)) continue;
             else explored.add(new HashSet<Set<Integer>>(cur));
             Set<Integer> news = new HashSet<Integer>();   //new clause
             news.addAll(s1);
             news.addAll(s2); 
             // full new clause
             for (Integer p: s1) {
                if(s2.contains(-p)) {
                  news.remove(p);
                  news.remove(-p);
                  count++;
                  if(news.isEmpty()) {
                     System.out.println("true "+count);
                     return;
                  }
                  if(!list.contains(news)) { // don't add duplicate clauses
                      boolean tautology = false; // check tautology
                      for(Integer e: news){
                         if(news.contains(-e)) {
                           tautology = true;
                           break;
                         }
                      }
                  if(!tautology) newc.add(new HashSet<Integer>(news)); // add to kb
                  }
                  news.add(p);
                  news.add(-p); 
                }
             }
          }
        }
      if(list.containsAll(newc)) {
        System.out.println("false "+count);
        return;
      }
      list.addAll(newc);
   }
   }

   /*
   negate the alpha using recursion 
   */

   public static Set<Set<Integer>> negation( List<Set<Integer>> a) {

    Set<Set<Integer>> result = new HashSet<Set<Integer>>();
    if(a.size()==0) return result;
    else if(a.size()==1) {
      Set<Integer> setq = new HashSet<Integer>();
      for(Integer m : a.get(0)){
           setq.add(-m);
           result.add(new HashSet<Integer>(setq));
           setq.remove(-m);
      }
      return result;
    }

    Set<Integer> s1 = a.remove(0); 
    Set<Integer> s2 = a.remove(0); 
    Set<Integer> set = new HashSet<Integer>();
    for(Integer k1 : s1){
      set.add(-k1);
      for (Integer k2: s2) { 
        set.add(-k2);
        result.add(new HashSet<Integer>(set)); 
        set.remove(-k2);
      }
      set.remove(-k1);
    }
    return negation(result,a);
   }


   /*
   help recursively negate alpha
   */

    public static Set<Set<Integer>> negation(Set<Set<Integer>> currentset, List<Set<Integer>> a) {
       if(a.size()==0)  return currentset;    
       Set<Set<Integer>> result = new HashSet<Set<Integer>>();
       Set<Integer> newset = a.remove(0); 
       for(Set<Integer> set : currentset){
          for (Integer k1: newset) { 
          set.add(-k1);
          if(!set.contains(k1) && set.contains(-k1)) result.add(new HashSet<Integer>(set));  
          set.remove(-k1);
          }
       }
       return negation(result,a);
   }

   /*
   convert the input file to Set<Set<Integer>>
   */

   public static Set<Set<Integer>> convertfileset(String s)  throws IOException {
     File filekb = new File(s); 
     BufferedReader br = new BufferedReader(new FileReader(filekb)); 
     String st; 
     int neg=1;
     Set<Set<Integer>> listkb = new HashSet<Set<Integer>>();
     Set<Integer> set;
     while ((st = br.readLine()) != null) {
        set = new HashSet<Integer>();
          int i=0;
          while(i<st.length()){
            if(st.charAt(i)==' ') i++;
            else if (st.charAt(i)=='-') {
                 neg=-1;
                 i++;
            } else {
               set.add(neg*st.charAt(i));
              // System.out.println("kb: "+neg*st.charAt(i));
               neg=1;
               i++;
            }
          }
          listkb.add(set);
     }
     return listkb;
   }

   /*
   convert the input file to List<Set<Integer>> (for negation)
   */

   public static List<Set<Integer>> convertfilelist(String s) throws IOException {
     File file = new File(s); 
     BufferedReader br2 = new BufferedReader(new FileReader(file)); 
     String st2; 
     int neg2=1;
     List<Set<Integer>> lista = new ArrayList<Set<Integer>>();
     Set<Integer> set2;
     while ((st2 = br2.readLine()) != null) {
          set2 = new HashSet<Integer>();
          int j=0;
          while(j<st2.length()){
            if(st2.charAt(j)==' ') j++;
            else if (st2.charAt(j)=='-') {
                 neg2=-1;
                 j++;
            } else {
               set2.add(neg2*st2.charAt(j));
              // System.out.println("a: "+neg2*st2.charAt(j));
               neg2=1;
               j++;
            }
          }
          lista.add(new HashSet<Integer>(set2));
     }
     return lista;
   }

   public static void main(String[] args) throws IOException  {
    String skb;
    String salpha;
    boolean needneg=false;
    if(args[0].equals("--noneg")) {
      skb= args[1];
      salpha= args[2];
    } else {
      skb= args[0];
      salpha= args[1];
      needneg=true;
    }
    Set<Set<Integer>> kb = convertfileset(skb);
    Set<Set<Integer>> a;
    if(needneg) {
       a = negation(convertfilelist(salpha));
    } else {
      a = convertfileset(salpha);
    }
    new Resolver().resolution(kb,a);

 }

}
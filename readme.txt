Solver.java
   A solver for weighted 8-puzzle problem

   Description of the heuristic used for Greedy Best-First and A*: 
   I used the sum of weighted Manhattan distance: cost*((xc-xg)+(yc-yg)) for each title for Greedy Best-First and A*. 

   Findings from examining the performance metrics on different test cases: 
   I found that the Uniform-Cost and A* are optimal on all test cases. The results of bfs are close to Uniform-Cost and A* in most cases. Greedy performs badly on this problem. However, in terms of frontier and expanded nodes, greedy has a smaller number than other methods. 
   For cases that have no solution, the frontier and expanded are both the number of possible board states, which is 181440.
   
Inference.java 
   A small program that uses approximate inference to determine the value of some probabilities:

   sample(): to generate samples for reject sampling. 

   likehoodsample(int we, int t, int h): generate samples for likelihood weighting, return the sample and the w. The parameters are the evidence values, -1 represents no evidence, positive values represent evidence. 

   reject(int n): The rejection-sampling method that returns an estimate of P(X|e). The parameter is the number of samples created. 

   like(int n): The likelihood-weighting method that returns an estimate of P(X|e). The parameter is the number of samples created. 

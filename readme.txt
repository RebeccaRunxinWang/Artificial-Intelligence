Description of the heuristic used for Greedy Best-First and A*: 
   I used the sum of weighted Manhattan distance: cost*((xc-xg)+(yc-yg)) for each title for Greedy Best-First and A*. 

Findings from examining the performance metrics on different test cases: 

   I found that the Uniform-Cost and A* are optimal on all test cases. The results of bfs are close to Uniform-Cost and A* in most cases. Greedy performs badly on this problem. However, in terms of frontier and expanded nodes, greedy has a smaller number than other methods. 

   For cases that have no solution, the frontier and expanded are both the number of possible board states, which is 181440. 

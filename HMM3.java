


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HMM3 {
	public static void main(String[] args) throws IOException 
	{
		//String fileName = "hmm3_01.in";
        //BufferedReader fromFile = null;
            //FileReader fr = new FileReader(fileName);
            //fromFile = new BufferedReader(fr);

            BufferedReader fromFile = new BufferedReader(new InputStreamReader(System.in));
            
            String lineStr = null;
            int N=0, K=0, T=0;
            lineStr = fromFile.readLine();//read 1st line for A (NxN)
            
            Double[][] A = createMatrix(lineStr);
            
            lineStr = fromFile.readLine();//read 2nd line for B (NxK)
            Double[][] B = createMatrix(lineStr);
            N = B.length;
            K = B[0].length;

            
            lineStr = fromFile.readLine();//read 3rd line for initial
            Double[] initial = createInitialVector(lineStr);//1xN vector,visit element like initial[0][i];

            lineStr = fromFile.readLine();//read 4th line for observation sequence
            int[] obSequence = createObjSequenceVector(lineStr);//1xT vector,visit element like obSequence[0][i];
            T = obSequence.length;
            
            Double[][] delta = new Double[T][N];
            int[][]path = new int[N][T];
            
            
            //compute vector delta1,     
            //System.out.println("\nprint the delta matrix:");
            for(int i=0;i<N;i++){
            	delta[0][i] = initial[i]*B[i][obSequence[0]];
            	//System.out.print(delta[0][i]+"  ");
                path[i][0]=i;
            }
            //System.out.println();
            
            //compute matrix delta
            
             
            for(int t=1;t<T;t++){
            	
            	int[][] newpath = new int[N][T];
            	
            	for(int j=0;j<N;j++){
            		double max = 0.0;
            		int state;
            		
            		for(int l=0;l<N;l++){
            			double temp = delta[t-1][l]*A[l][j]*B[j][obSequence[t]];
            			if(temp>max){
            				max = temp;
            				state=l;
            			System.arraycopy(path[state], 0, newpath[j], 0, t);
            			newpath[j][t]=j;
            			}
            				
            		}
            		delta[t][j] = max;
            		//System.out.print(delta[t][j]+"  ");
            	}
            	path = newpath;
            	//System.out.println();
            }

            //find final state in hidden state sequence
//            double finalProb = 0.0;
//            int finalState = 0;
//            for(int r=0;r<N;r++){
//            	if(delta[T-1][r]>finalProb) {
//            		finalProb = delta[T-1][r];
//            		finalState = r;
//            	}
//            }
//            
//            System.out.println("finalState is"+finalState);
            double prob = -1;
            int state = 0;
            for (int y=0;y<N;y++)
            {
                if (delta[T - 1][y] > prob)
                {
                    prob = delta[T - 1][y];
                    state = y;
                }
            }
            //System.out.println("the status sequence is:");
            for (int r : path[state])
            {
                System.out.print(r + " ");
            }
     


	}
	
	public static int[] createObjSequenceVector(String lineStr){
		
		String[] lineStrs = lineStr.substring(2).split(" ");
		int[] vector = new int[lineStrs.length];
		//System.out.println("\nprint observation sequence vector:");
		for(int i=0;i<lineStrs.length;i++) {
			vector[i] = Integer.parseInt(lineStrs[i]);
			//System.out.print(vector[i]);
          	//System.out.print("  ");
         }
		return vector;
	}
	
	
	public static Double[] createInitialVector(String lineStr){
		
		
		String[] lineStrs = lineStr.substring(4).split(" ");
		Double[] vector = new Double[lineStrs.length];
		//System.out.println("\nprint initial vector:");
		
		for(int i=0;i<lineStrs.length;i++) {
			vector[i] = Double.valueOf(lineStrs[i]).doubleValue();
          	 //System.out.print(vector[i]);
          	 //System.out.print("  ");
         }
		return vector;
	}
	

    public static Double[][] createMatrix(String lineStr){
    	
    	 int rowNum = Integer.parseInt(lineStr.substring(0,1));
         int columnNum = Integer.parseInt(lineStr.substring(2,3));
         
         String[] lineStrs = lineStr.substring(4).split(" ");
         

                  
         Double[][] matrix = new Double[rowNum][columnNum];        
         int num = 0;
         //System.out.println("print matrix:");
      	 
 	     for(int i=0;i<rowNum;i++) {
      	    for(int j=0;j<columnNum;j++) {
          	 matrix[i][j] = Double.valueOf(lineStrs[num++]).doubleValue();
          	 //System.out.print(matrix[i][j]);
          	 //System.out.print("  ");
            } 
      	   // System.out.println();
         }
 	     return matrix;                
    }
    
}


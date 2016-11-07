


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HMM2 {
	public static void main(String[] args) throws IOException 
	{
//		String fileName = "hmm2_01.in";
//        BufferedReader fromFile = null;
//            FileReader fr = new FileReader(fileName);
//            fromFile = new BufferedReader(fr);

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
            
            Double[][] alpha = new Double[T][N];
            
            //compute vector alpha1,       k1:the observation at t=1
            int k1 = obSequence[0];
            //System.out.println("\nprint the alpha1 vector:");
            for(int i=0;i<N;i++){
            	alpha[0][i] = initial[i]*B[i][k1];
            	//System.out.print(alpha[0][i]+"  ");
            }
            
            //compute vector alphat (alpha at time t)
            
               //compute A' x alpha t-1;   alpha[t]1x4
            for(int t=1;t<T;t++){
            	double sum = 0.0;
            	for(int j=0;j<N;j++){
            		// compute alpha[t][j]
            		double sumTemp = 0.0;
            		for(int l=0;l<N;l++){
            			sumTemp = sumTemp + alpha[t-1][l]*A[l][j];
            		}
            		sum = sumTemp*B[j][obSequence[t]];
            		alpha[t][j] = sum;
            		//System.out.print(alpha[t][j]+"  ");
            	}
            	
            	//System.out.println();
            }
            
            double result = 0.0;
            for(int r=0;r<N;r++){
            	result = result + alpha[T-1][r];
            }
            
            System.out.println(result);
            
            
            


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
        // System.out.println("print matrix:");
      	 
 	     for(int i=0;i<rowNum;i++) {
      	    for(int j=0;j<columnNum;j++) {
          	 matrix[i][j] = Double.valueOf(lineStrs[num++]).doubleValue();
          	 //System.out.print(matrix[i][j]);
          	 //System.out.print("  ");
            } 
      	    //System.out.println();
         }
 	     return matrix;                
    }
    
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HMM4 {
		   public static double[] stringToDoubleMatrix(String str)
		    {
		        String[] strin=str.split(" ");
		        double[] dmout=new double[strin.length];

		        for(int a=0;a<strin.length;a++){
		         dmout[a] = Double.valueOf(strin[a]);
		        }

		        return dmout;
		    }
		   public static void Print(int row,int column,double[][] martix){
			      for(int i=0;i<row;i++){
			    	  for(int j=0; j<column;j++){
			    		  System.out.print(martix[i][j]+" ");
			    	  }
			      }
		   }


	 public static void main(String args[]) throws IOException{
	       BufferedReader br = new BufferedReader (new InputStreamReader(System.in));

	          String s1=br.readLine();
	          double a[]=stringToDoubleMatrix(s1);
	          int rowA = (int)a[0];
	          int columnA = (int)a[1];
	          double A[][] = new double [rowA][columnA];
	          int num = 2;
	          for(int i =0;i<rowA;i++)
	              for(int j =0;j<columnA;j++)
	                  A[i][j] = a[num++];


	          String s2=br.readLine();
	   	      double b[]=stringToDoubleMatrix(s2);
	          int rowB =  (int)b[0];
	          int columnB = (int)b[1];
	          double B[][] = new double [rowB][columnB];
	          num = 2;
	          for(int i =0;i<rowB;i++)
	             for(int j =0;j<columnB;j++)
	                 B[i][j] = b[num++];


	          String s3=br.readLine();
	   	      double pi[]=stringToDoubleMatrix(s3);
	          int rowPi = (int)pi[0];
	          int columnPi = (int)pi[1];
	          double Pi[][] = new double [rowPi][columnPi];
	          num = 2;
	          for(int i =0;i<rowPi;i++)
	              for(int j =0;j<columnPi;j++)
	                  Pi[i][j] = pi[num++];

	          String s4 = br.readLine();
	          double o[] = stringToDoubleMatrix(s4);
	          int rowO = (int)o[0];
	          int O[] = new int [rowO];
	          for(int i = 0;i<rowO;i++)
	                  O[i] = (int)o[i+1];
	          int T=rowO;
	          int N=rowB;
	          int M=columnB;

	        double lptemp = 0.0;
	        double s = 0.0;



	        do{
	      //alpha (scaled)
	          double[][] alpha = new double[T][N];
	          double[] c = new double[T];

		      c[0]=0.0;
	          for(int i=0;i<N;i++){
	           	  alpha[0][i] = Pi[0][i]*B[i][O[0]];
	           	  c[0] +=  alpha[0][i];
	              }
	           	   c[0]=1/c[0];
	          for(int i=0;i<N;i++){
	        	  alpha[0][i]=c[0]*alpha[0][i];
	              }
	          for(int t=1;t<T;t++){
	               	 c[t]=0;
	            	 for(int i=0;i<N;i++){
	           	         double sumTemp = 0.0;
	           		     for(int j=0;j<N;j++){
	           			     sumTemp = sumTemp + alpha[t-1][j]*A[j][i];
	           		         }
	           		     alpha[t][i] = sumTemp*B[i][O[t]];
	           		     c[t]=c[t]+alpha[t][i];
	           		     }
	              //scale alpha
	            	 c[t]=1/c[t];
	            	 for(int i=0;i<N;i++){
	            		 alpha[t][i]=c[t]*alpha[t][i];
	            	 }

	          	}




	     //beta(scaled)
	          double[][] beta = new double[T][N];
	          for ( int i=0 ; i<N;i++ ){
	              beta[T-1][i] = c[T-1];
	          }
	          for(int t=T-2;t>=0;t--){
	        	  for(int i=0;i<N;i++){
	        		  beta[t][i] = 0.0;
	        		  for(int j=0;j<N;j++){
	        			  beta[t][i] += A[i][j]*B[j][O[t+1]]*beta[t+1][j];
	        		      }
	        		  beta[t][i]=c[t]*beta[t][i];
	        	  }

	          }

	     //digamma/gamma
	          double[][][] digamma = new double[T-1][N][N];
	          double[][] gamma = new double[T][N];
	          for(int t=0 ;t<T-1;t++){
	        	  double sumtemp=0.0;
    			  for(int i=0;i<N;i++){
    				  for(int j=0;j<N;j++){
    				  sumtemp += alpha[t][i]*A[i][j]*B[j][O[t+1]]*beta[t+1][j];
    			      }
    			  }
	        	  for(int i=0;i<N;i++){
	        		  gamma[t][i]=0;//???
	        		  for(int j=0;j<N;j++){
	        			  digamma[t][i][j]=alpha[t][i]*A[i][j]*B[j][O[t+1]]*beta[t+1][j]/sumtemp;
	        			  gamma[t][i]+=digamma[t][i][j];
	        		  }
	        	  }
	          }
	       // Special case for gamma[T-1][i]
	         double sumspec=0.0;
	         for(int i=0;i<N;i++){
	        	 sumspec+=alpha[T-1][i];
	         }
	         for(int i=0;i<N;i++){
	        	 gamma[T-1][i]=alpha[T-1][i]/sumspec;
	         }

	        /*  for(int t=0;t<T;t++){
	        	  for(int i=0;i<N;i++){
	        		  double sumtemp=0.0;
		              for(int j=0;j<N;j++){
		            	  sumtemp+=digamma[t][i][j];
		              }
		              gamma[t][i]=sumtemp;
	        	  }

	          }*/

	   //xi
//	          double[][][] xi = new double[T-1][N][N];
//	          double po = 0.0;
//	          double p1 = 0.0;
//	          for(int t=0 ;t<T-1;t++){
//
//                  for(int i=0;i<N;i++){
//	        		  for(int j=0;j<N;j++){
//	        			  p1 += alpha[t][i]*A[i][j]*beta[t+1][j]*B[j][O[t+1]]/(c[t]*c[t]);
//	        			  }
//	        		  }
//                  po=p1;
//	        	  for(int i=0;i<N;i++){
//	        		  for(int j=0;j<N;j++){
//	        			  xi[t][i][j]=alpha[t][i]*A[i][j]*B[i][O[t+1]]*beta[t+1][j]/(p1*c[t]*c[t]);
//	        		     }
//	        	  	  }
//
//	        	  }
	       //log(P(O|Lambda))
	        double logProb = 0;
	    	for(int i=0;i<T;i++){
	    		 logProb +=  Math.log(c[i]);
	    	}
	    	logProb= -logProb;

	       //new A
	          for(int i=0;i<N;i++){
	        	  for(int j=0;j<N;j++){
	        	      double sumtemp1 = 0.0;
	        	      double sumtemp2 = 0.0;
	       	          for(int t=0;t<T-1;t++){
	        		      sumtemp1 += gamma[t][i];
	        		      sumtemp2 += digamma[t][i][j];
	        		      }
	              A [i][j]= sumtemp2/sumtemp1;
	        	  }
	          }

	       //new B
	          for(int i=0;i<N;i++){
	        	   for(int j=0;j<M;j++){
	        		   double sumtemp1 = 0.0;
	        		   double sumtemp2 = 0.0;
	        	       for(int t=0;t<T;t++){
	        	    	   if(O[t] == j){
	        	    		   sumtemp1 += gamma[t][i];
	        	    	     }
	        	    	   sumtemp2 += gamma[t][i];
	        	       }
	        	       B[i][j]=sumtemp1/sumtemp2;
	        	   }
	          }



	        // new Pi
	          for(int i=0;i<N;i++){
	        	  Pi[0][i] = gamma[0][i];
	          }


	          /* double lp = -Math.log(po);*/

	           //System.out.println(lptemp);
	           s = lptemp-logProb;
	           lptemp = logProb;

//
//	           System.out.println( Math.abs(s));
//	           System.out.println(logProb);
//	           System.out.println();
	        }
	        while ( Math.abs(s)>0.01 );


           System.out.print(rowA + " "+ columnA + " ");
           Print(rowA,columnA,A);
           System.out.println();
           System.out.print(rowB + " "+ columnB + " ");
           Print(rowB,columnB,B);

	   }
}

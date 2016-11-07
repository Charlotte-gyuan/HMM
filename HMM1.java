import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HMM1 {

    public static float[][] multiMatrix(float matrix1[][] , float matrix2[][]){
       float result[][] = new float[matrix1.length][matrix2[0].length];
       int x,i,j;
       float tmp=0;
       for(i =0;i<matrix1.length;i++){
           for(j = 0;j<matrix2[0].length;j++){
               for( x=0;x<matrix2.length;x++)
                   tmp += matrix1[i][x] * matrix2[x][j];
                result[i][j] = tmp;
                tmp = 0;
                  }
           }
       return result;


      }

    public static float[] stringTofloatMatrix(String str)
    {
        String[] strin=str.split(" ");
        float[] dmout=new float[strin.length];

        for(int a=0;a<strin.length;a++){
         dmout[a] = Float.valueOf(strin[a]);
        }

        return dmout;
    }

   public static void main(String args[]) throws IOException{
       BufferedReader br = new BufferedReader (new InputStreamReader(System.in));

           String s1=br.readLine();

           float a[]=stringTofloatMatrix(s1);

          int rowA = (int)a[0];
          int columnA = (int)a[1];
          float A[][] = new float [rowA][columnA];
          int num = 2;
          for(int i =0;i<rowA;i++)
              for(int j =0;j<columnA;j++)
                  A[i][j] = a[num++];


          String s2=br.readLine();

   	      float b[]=stringTofloatMatrix(s2);

          int rowB =  (int)b[0];


          int columnB = (int)b[1];
          float B[][] = new float [rowB][columnB];
          num = 2;
          for(int i =0;i<rowB;i++)
             for(int j =0;j<columnB;j++)
                 B[i][j] = b[num++];


          String s3=br.readLine();

   	      float pi[]=stringTofloatMatrix(s3);

          int rowPi = (int)pi[0];
          int columnPi = (int)pi[1];
          float Pi[][] = new float [rowPi][columnPi];
          num = 2;
          for(int i =0;i<rowPi;i++)
              for(int j =0;j<columnPi;j++)
                  Pi[i][j] = pi[num++];

          float temp[][] = multiMatrix(A,B);
          float temp1[][] = multiMatrix(Pi,temp);


          System.out.print("1 "+ columnB+" ");
          for( int i=0;i<temp1.length;i++){
              for(int j=0;j<temp1[i].length;j++){
                  System.out.print(temp1[i][j] + " ");
                  }
              System.out.println();
             }


	   return;
       }
}

package lcs;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;

public class StrMatch {
    public static void main(String[] args) {

        String a="abc";
        String b="ab";

         List<String> aList = new ArrayList<>();
         aList.add("col1");
         aList.add("col2");
         aList.add("col3");

        List<String> bList = new ArrayList<>();
        bList.add("col1");
        bList.add("col");
        bList.add("col3");

        float[][] scores  = calcScores(aList, bList);
        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(scores[i][j]+",");

            }
            System.out.println();
        }
//        List<String> matched  = new ArrayList<>(5);

        String [] matched=new String[3];
        for (int i = 0; i < aList.size(); i++) {
            int row=0,col=0;
            double maxValue=scores[0][0];
            int [][] re=new int [scores.length][scores.length];
            for (int j = 0; j < scores.length; j++) {
                for (int k = 0; k < scores[0].length; k++) {
                    if(scores[j][k]>maxValue){
                        maxValue=scores[j][k];
                        row=j;
                        col=k;
                    }
                }
            }
            scores[row][0]=-1f;
            scores[row][1]=-1f;
            scores[row][2]=-1f;
            scores[0][col]=-1f;
            scores[1][col]=-1f;
            scores[2][col]=-1f;

            System.out.println("============");
            for (int n = 0; n <3; n++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(scores[n][j]+",");

                }
                System.out.println();
            }
            matched[row]=bList.get(col);
        }




        System.out.println(aList);
        System.out.println(bList);
        System.out.println(Arrays.toString(matched));

    }


    public static float [][] calcScores(List<String> a, List<String> b){

        int asize = a.size();

        float [][] scores=new float[3][3];

        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                int lcs=lcs(a.get(i),b.get(j));

                scores[i][j]=lcs+(float)lcs/max(a.get(i).length(),b.get(j).length());

            }
        }

        return scores;

    }


    public static int lcs(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int c[][] = new int[len1+1][len2+1];
        for (int i = 0; i <= len1; i++) {
            for( int j = 0; j <= len2; j++) {
                if(i == 0 || j == 0) {
                    c[i][j] = 0;
                } else if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    c[i][j] = c[i-1][j-1] + 1;
                } else {
                    c[i][j] = max(c[i - 1][j], c[i][j - 1]);
                }
            }
        }
        return c[len1][len2];
    }
}

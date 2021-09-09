package lcs;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class StrMatch {
    public static void main(String[] args) {

         List<String> aList = new ArrayList<>();
         aList.add("col2");
         aList.add("col1");
         aList.add("col3");
         aList.add("col4");

        List<String> bList = new ArrayList<>();
        bList.add("col1");
        bList.add("col3");
        bList.add("col");
        bList.add("col2");

        System.out.println(strMatch(aList, bList));

    }

    public static JSONObject strMatch (List<String> aList, List<String> bList){
        int aSize = aList.size();
        int bSize = bList.size();
        String [] matched=new String[bSize];
        float[][] scores  = calcScores(aList, bList);

        for (int i = 0; i <aSize; i++) {
            for (int j = 0; j < bSize; j++) {
                System.out.print(scores[i][j]+",");
            }
            System.out.println();
        }

        //找到scores最大的位置
        for (int i = 0; i < aList.size(); i++) {
            int row=0,col=0;
            double maxValue=scores[0][0];
            for (int j = 0; j < scores.length; j++) {
                for (int k = 0; k < scores[0].length; k++) {
                    if(scores[j][k]>maxValue){
                        maxValue=scores[j][k];
                        row=j;
                        col=k;
                    }
                }
            }

            //取出匹配分最高的数，存入数组
            matched[row]=bList.get(col);
            //将评分表行列分数置为-1
            for (int j = 0; j < scores.length; j++) {
                scores[row][j]=-1f;
                scores[j][col]=-1f;
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("col1",aList);
        jsonObject.put("col2",matched);

        return jsonObject;
    }

    public static float [][] calcScores(List<String> a, List<String> b){

        int aSize = a.size();
        int bSize = b.size();
        float [][] scores=new float[aSize][bSize];

        for (int i = 0; i <aSize; i++) {
            for (int j = 0; j < bSize; j++) {
                int lcs=lcs(a.get(i),b.get(j));
                scores[i][j]=lcs+(float)lcs/max(a.get(i).length(),b.get(j).length());
            }
        }
        return scores;
    }

    public static int lcs(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int[][] c = new int[len1+1][len2+1];
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

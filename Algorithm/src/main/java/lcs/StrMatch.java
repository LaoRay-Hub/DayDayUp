package lcs;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class StrMatch {
    public static void main(String[] args) {

         List<String> aList = new ArrayList<>();
         aList.add("col1");
         aList.add("col2");
         aList.add("col3");
         aList.add("col4");
         aList.add("col5");
         aList.add("col6");
         aList.add("col6");

        List<String> bList = new ArrayList<>();
        bList.add("col1");
        bList.add("col3");
        bList.add("col");
        bList.add("col4");
        bList.add("col2");
        bList.add("col5");

        System.out.println(strMatch(aList, bList));

    }

    public static JSONObject strMatch (List<String> aList, List<String> bList){
        int aSize = aList.size();
        int bSize = bList.size();
        List<Integer> bListUsedIndex=new ArrayList<>();
        List<String> bListUnused=new ArrayList<>();
        List<String> matched=new ArrayList<>();
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
            bListUsedIndex.add(col);
            //取出匹配分最高的数，存入数组
            matched.add(bList.get(col));
            //将评分表行列分数置为-1
            for (int j = 0; j < scores.length; j++) {
                scores[j][col]=-1f;
                for (int k=0;k<scores[0].length;k++){
                    scores[row][k]=-1f;
                }
            }
        }

        for (int i = 0; i < bList.size(); i++) {
            if(!bListUsedIndex.contains(i)){
                bListUnused.add(bList.get(i));
            }
        }
        matched.addAll(bListUnused);




        JSONObject jsonObject = new JSONObject();
        jsonObject.put("col1",aList);

        if(aSize>bSize){
            List<String>matchedSub= matched.subList(0, bSize);
            jsonObject.put("col2",matchedSub);
        }else {
            jsonObject.put("col2",matched);
        }


        return jsonObject;
    }

    /**
     * 结合最长子序列和字符串长度算出两个list的字符串，相互匹配的分值
     * @param a list1
     * @param b list1
     * @return 字符串相互匹配值二维数组
     */
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

    /**
     * 最长子序算法
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 两个字符串匹配的子序个数
     */
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

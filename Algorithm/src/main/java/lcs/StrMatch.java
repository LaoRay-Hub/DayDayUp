package lcs;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import static java.lang.Math.max;

public class StrMatch {
    public static void main(String[] args) {

        List<String> aList = new ArrayList<>();
        aList.add("catalog");
        aList.add("create_time");
        aList.add("driver_name");
        aList.add("id");
        aList.add("name");
        aList.add("operator_id");
        aList.add("operator_name");
        aList.add("password");
        aList.add("remarks");
        aList.add("schema_name");
        aList.add("type");
        aList.add("update_time");
        aList.add("url");
        aList.add("username");
        aList.add("valid_state");

        List<String> bList = new ArrayList<>();
        bList.add("app_id");
        bList.add("parent_code");
        bList.add("id");
        bList.add("app_id");
        bList.add("auth_name");
        bList.add("app_name");

        System.out.println("a大于b"+strMatch(aList, bList));

        List<String> aList1 = new ArrayList<>();
        aList1.add("app_id");
        aList1.add("parent_code");
        aList1.add("id");
        aList1.add("app_id");
        aList1.add("auth_name");
        aList1.add("app_name");


        List<String> bList1 = new ArrayList<>();
        bList1.add("catalog");
        bList1.add("create_time");
        bList1.add("driver_name");
        bList1.add("id");
        bList1.add("name");
        bList1.add("operator_id");
        bList1.add("operator_name");
        bList1.add("password");
        bList1.add("remarks");
        bList1.add("schema_name");
        bList1.add("type");
        bList1.add("update_time");
        bList1.add("url");
        bList1.add("username");
        bList1.add("valid_state");

        System.out.println("a小于b"+strMatch(aList1, bList1));


        List<String> aList2 = new ArrayList<>();
        aList2.add("catalog");
        aList2.add("create_time");
        aList2.add("driver_name");
        aList2.add("id");
        aList2.add("name");
        aList2.add("operator_id");
        aList2.add("operator_name");
        aList2.add("password");
        aList2.add("remarks");
        aList2.add("schema_name");
        aList2.add("type");
        aList2.add("update_time");
        aList2.add("url");
        aList2.add("username");
        aList2.add("valid_state");

        List<String> bList2 = new ArrayList<>();
        bList2.add("catalog");
        bList2.add("create_time");
        bList2.add("driver_name");
        bList2.add("id");
        bList2.add("name");
        bList2.add("operator_id");
        bList2.add("operator_name");
        bList2.add("password");
        bList2.add("remarks");
        bList2.add("schema_name");
        bList2.add("type");
        bList2.add("update_time");
        bList2.add("url");
        bList2.add("username");
        bList2.add("valid_state");

        System.out.println("a等于b"+strMatch(aList2, bList2));

    }

    public static JSONObject strMatch(List<String> aList, List<String> bList) {
        int aSize = aList.size();
        int bSize = bList.size();
        List<Integer> bListUsedIndex = new ArrayList<>();
        List<String> bListUnused = new ArrayList<>();
        String[] matched = new String[Math.max(aSize, bSize)];
        float[][] scores = calcScores(aList, bList);
        JSONObject strMatched = new JSONObject();

//        for (int i = 0; i < aSize; i++) {
//            for (int j = 0; j < bSize; j++) {
//                System.out.print(scores[i][j] + ",");
//            }
//            System.out.println();
//        }

        //找到scores最大的位置
        for (int i = 0; i < aSize; i++) {
            int row = 0, col = 0;
            double maxValue = scores[0][0];
            for (int j = 0; j < scores.length; j++) {
                for (int k = 0; k < scores[0].length; k++) {
                    if (scores[j][k] > maxValue) {
                        maxValue = scores[j][k];
                        row = j;
                        col = k;
                    }
                }
            }
            bListUsedIndex.add(col);
            //取出匹配分最高的数，存入数组
            matched[row] = bList.get(col);
            //将评分表行列分数置为-1
            for (int j = 0; j < scores.length; j++) {
                scores[j][col] = -1f;
                for (int k = 0; k < scores[0].length; k++) {
                    scores[row][k] = -1f;
                }
            }
        }

        List<String> matchedList = new ArrayList<>(Arrays.asList(matched));
        for (int i = 0; i < bList.size(); i++) {
            if (!bListUsedIndex.contains(i)) {
                bListUnused.add(bList.get(i));
            }
        }
        matchedList.addAll(bListUnused);
//
//        List<String> matchedListFinal = new ArrayList<>();
//        for (String s : matchedList) {
//            if (StringUtils.isNotBlank(s)) {
//                matchedListFinal.add(s);
//            }
//        }
//
//        System.out.println("matchedList="+matchedList);
//        System.out.println("matchedListFinal="+matchedListFinal);
//
//        System.out.println("matchedList2="+matchedList);

        strMatched.put("col1", aList);

        if (aSize < bSize) {
            matchedList.removeAll(Collections.singletonList(null));
            strMatched.put("col2", matchedList);
        } else {
            strMatched.put("col2", matchedList);
        }

        return strMatched;
    }

    /**
     * 结合最长子序列和字符串长度算出两个list的字符串，相互匹配的分值
     *
     * @param a list1
     * @param b list1
     * @return 字符串相互匹配值二维数组
     */
    public static float[][] calcScores(List<String> a, List<String> b) {
        int aSize = a.size();
        int bSize = b.size();
        float[][] scores = new float[aSize][bSize];

        for (int i = 0; i < aSize; i++) {
            for (int j = 0; j < bSize; j++) {
                int lcs = lcs(a.get(i), b.get(j));
                scores[i][j] = lcs + (float) lcs / max(a.get(i).length(), b.get(j).length());
            }
        }
        return scores;
    }

    /**
     * 最长子序算法
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 两个字符串匹配的子序个数
     */
    public static int lcs(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int[][] c = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0 || j == 0) {
                    c[i][j] = 0;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else {
                    c[i][j] = max(c[i - 1][j], c[i][j - 1]);
                }
            }
        }
        return c[len1][len2];
    }
}

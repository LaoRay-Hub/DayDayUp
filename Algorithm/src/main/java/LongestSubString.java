import java.time.temporal.Temporal;
import java.util.*;

/**
 * 最长公共子串
 */
public class LongestSubString {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("abc");
        list1.add("_name");
        list1.add("_nameid");
        list1.add("_name_id");

        List<String> list2 = new ArrayList<>();
        list2.add("abc");
        list2.add("_name");
        list2.add("_id");
        list2.add("_name_id");

        //存储变更后的list2
        List<String> list3 = new ArrayList<>();
        List<Integer>indexUsed = new ArrayList<>();
        String tmp;
        Map<Integer, Integer> integerIntegerHashMap = new HashMap<>();
        int indexTmp = 0;
        for (int i=0;i<list1.size();i++) {
            tmp=list2.get(i);
            int maxSubSizeSamePlace = longestSubstring(list1.get(i), list2.get(i));
            for(int j=0;j<list2.size();j++){
                int maxSubSize = longestSubstring(list1.get(i), list2.get(j));
                if(maxSubSize>maxSubSizeSamePlace){
                    Set<Integer> integers = integerIntegerHashMap.keySet();
                    if(integers.contains(j)){
                        Integer integer = integerIntegerHashMap.get(j);
                        list3.add(j,list3.get(integer));
                        list3.add(integer,list2.get(j));
                        integerIntegerHashMap.put(j,j);
                        integerIntegerHashMap.put(integer,integer);
                    }else {
                        tmp=list2.get(j);
                        indexUsed.add(j);
                        indexTmp=j;
                        integerIntegerHashMap.put(j,i);
                    }

                }
            }

//            if(indexUsed.contains(indexTmp)){
//                list3.add(indexTmp,tmp);
//            }else {
                list3.add(tmp);
//            }

        }

        System.out.println("原数组1："+list1);
        System.out.println("原数组2："+list2);
        System.out.println("变动数组："+list3);
    }


    public static int longestSubstring(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                dp[i][j] = 0;
            }
        }
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        int max = 0;
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (dp[i][j] > max) {
                    max = dp[i][j];
                }
            }
        }
        return max;

    }
}

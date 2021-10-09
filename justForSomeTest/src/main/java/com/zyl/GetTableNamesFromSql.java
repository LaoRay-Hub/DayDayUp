package com.zyl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class GetTableNamesFromSql {
    //去除注释
    public synchronized static String dSql(String sql) {
        Pattern p = Pattern.compile("(?ms)('(?:''|[^'])*')|--.*?$|/\\*.*?\\*/|#.*?$|");
        return p.matcher(sql).replaceAll("$1");

    }


    //解析sql
    public synchronized static void parseSql(String sql, Set<String> tableList) {


        sql = sql.toLowerCase().replaceAll("\t", " ");

        HashMap<Object, Object> map = new HashMap<>();

        int from_ = sql.indexOf(" from ");
        map.put(from_, 6);
        int where_ = sql.indexOf(" where ");
        map.put(where_, 7 + where_);
        int left_outer_join_ = sql.indexOf("left outer join ");
        map.put(left_outer_join_, 16 + left_outer_join_);
        int limit_ = sql.indexOf(" limit ");
        map.put(limit_, 7 + limit_);
        int group_ = sql.indexOf(" group ");
        map.put(group_, 7 + group_);

        int array[] = {where_, left_outer_join_, limit_, group_};
        int min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] == -1) {
                continue;
            } else {
                if (min == -1) {
                    min = array[i];
                }

            }

            if (array[i] < min) {
                min = array[i];
            }
        }

        if (from_ < min) {
            String table = sql.substring(sql.indexOf(" from ") + 6, (int) map.get(min));
            String AfterWhere = sql.substring((int) map.get(min));
            parGet(tableList, table);
            if (AfterWhere.contains(" from ")) {
                parseSql(AfterWhere, tableList);
            }
        } else {


            if (sql.contains(" from ")) {
                String AfterWhere = sql.substring(sql.indexOf(" from ") + 6);
                parGet(tableList, AfterWhere);
            }

        }


    }

    // 获取表名
    private synchronized static void parGet(Set<String> tableList, String table) {


        table.trim().replaceAll(" +", " ");
        if (table.trim().contains(" from ")) {
            String from_ = table.substring(table.indexOf(" from ") + 6);
            if (from_.contains(" where ")) {
                from_ = from_.substring(0, from_.indexOf(" where "));
            }

            if (from_.contains(" from ")) {
                from_ = from_.substring(from_.indexOf(" from ") + 6);
            }

            if (from_.contains(")")) {
                from_ = from_.substring(0, from_.indexOf(")"));
            }

            if (from_.trim().contains(",")) {
                String[] splits = from_.split(",");
                for (String s :
                        splits) {
                    if (s.trim().contains(" ")) {
                        s = s.trim().split(" ")[0];
                    }
                    tableList.add(s.trim());
                }
            } else {
                tableList.add(from_.trim());
            }


        } else if (table.trim().contains(" where ")) {
            table = table.substring(0, table.indexOf(" where "));

            if (table.trim().contains(",")) {
                String[] splits = table.split(",");
                for (String s :
                        splits) {
                    if (s.trim().contains(" ")) {
                        s = s.trim().split(" ")[0];
                    }
                    tableList.add(s.trim());
                }
            } else {
                tableList.add(table.trim());
            }
        } else if (table.trim().contains(",")) {

            String[] splits = table.split(",");
            for (String s :
                    splits) {
                if (s.trim().contains(" ")) {
                    s = s.trim().split(" ")[0];
                }
                tableList.add(s.trim());
            }

        } else if (table.trim().contains(" ")) {

            table = table.trim().split(" ")[0];
            tableList.add(table.trim());

        } else {
            tableList.add(table.trim());
        }


    }

    // 要 校验的 sql
    public static String sql ="SELECT * FROM (SELECT\nds_id\n,name_id\n,education\n,job_skills\n,institution_count\n,rank\n,nonmedical\n,birthday_real\n,if_patent\n,field\n,approximate_age\n,scholarid\n,g_index\n,sex\n,h_index\n,if_qualifications\n,url\n,team_experience\n,country\n,name_en\n,subject\n,project\n,source\n,reference\n,current_residence\n,substr(depart,0,5)||substr(depart,6,2)||substr(depart,9,2) as depart\n,del_flag\n,if_chinese\n,homeplace\n,management_experience\n,name\n,birthday\n,professional_skills\n,substr(birthday_k,0,5)||substr(birthday_k,6,2)||substr(birthday_k,9,2) as birthday_k\n,stroke_count\n,language_skills\n,if_literature\n,isuse\n,introduce\n,local_photo\n,remarks\n,COALESCE(industry_list::TEXT, '[]') as industry_list\n,COALESCE(name_list::TEXT, '[]') as name_list\n,COALESCE(honor_list::TEXT, '[]') as honor_list\n,COALESCE(relation_infos_list::TEXT, '[]') as relation_infos_list\n,COALESCE(talent_list::TEXT, '[]') as talent_list\n,COALESCE(education_list::TEXT, '[]') as education_list\n,COALESCE(institution_list::TEXT, '[]') as institution_list\n,COALESCE(person_label::TEXT, '[]') as person_label\n,sys_update_date\n,sys_dw_update_date\n,id\n,sys_crawl_date\n,sys_insert_date\n,sys_source\n,sys_ods_md5\n,sys_dw_md5\n,is_hash\n,is_exist\n,COALESCE(subdivision_label::TEXT, '[]') as subdivision_label\n,COALESCE(resource_chain_label::TEXT, '[]') as resource_chain_label\n,COALESCE(classification_list::TEXT, '[]') as classification_list\n,COALESCE(zaiti_infos_list::TEXT, '[]') as zaiti_infos_list\n,COALESCE(subdivision_test_label::TEXT, '[]') as subdivision_test_label\n,score\n,COALESCE(cooperative_institution_list::TEXT, '[]') as cooperative_institution_list\n,COALESCE(count_list::TEXT, '[]') as count_list\n,COALESCE(inside_park_list::TEXT, '[]') as inside_park_list\n,COALESCE(label::TEXT, '[]') as label\n,COALESCE(medical_label::TEXT, '[]') as medical_label\n,COALESCE(reference_label::TEXT, '[]') as reference_label\n,COALESCE(relation_list::TEXT, '[]') as relation_list\n,COALESCE(subject_list::TEXT, '[]') as subject_list\n,COALESCE(aminer_list::TEXT, '[]') as aminer_list\nFROM ds_person_information_2108) t WHERE sys_update_date>=${lastTime}";

    public static void main(String[] args) {
        Set<String> tableList = new HashSet<String>();
        parseSql(dSql(sql.toLowerCase().replaceAll("\n", " ")), tableList);
        System.out.println(tableList.toString());
    }


}

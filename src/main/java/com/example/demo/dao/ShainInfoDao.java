package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.ShainInfoDto;
import com.example.demo.form.RegisterForm;
import com.example.demo.form.SearchForm;

@Repository
public class ShainInfoDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShainInfoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 検索
    public ArrayList<ShainInfoDto> searchShainInfo(SearchForm searchForm) {
        String selectSql = "SELECT " +
                "  CIF_OperatorM.Code " +
                " ,CIF_OperatorM.MeisyouKanji " +
                " ,CIF_OperatorM.ShozokuMei " +
                " ,CIF_OperatorM.SeibetsuMei " +
                " ,DATE_FORMAT(CIF_OperatorM.NyushaDate,'%Y/%m/%d') As NyushaDate " +
                " ,CIF_OperatorM.Naisen " +
                "FROM " +
                "  CIF_OperatorM ";

        String whereString = "";
        whereString = getAnd(new String[] { getWhereString("CIF_OperatorM.Code", searchForm.getCode()),
                getWhereStringLike("CIF_OperatorM.MeisyouKanji", searchForm.getMeisyouKanji()),
                getWhereString("CIF_OperatorM.ShozokuMei", searchForm.getShozokuMei()),
                getWhereString("CIF_OperatorM.SeibetsuMei", searchForm.getSeibetsuMei()),
                getWhereString("CIF_OperatorM.Naisen", searchForm.getNaisen()) });
        if (!"".equals(whereString)) {
            selectSql = selectSql + " WHERE " + whereString;
        }
        selectSql = selectSql + " ORDER BY CODE ASC ";

        List<Map<String, Object>> shainList = jdbcTemplate.queryForList(selectSql);
        ArrayList<ShainInfoDto> list = new ArrayList<>();
        for (Map<String, Object> str1 : shainList) {
            ShainInfoDto dto = new ShainInfoDto();
            dto.setCode((str1.get("Code")).toString());
            dto.setMeisyouKanji((String) str1.get("MeisyouKanji"));
            dto.setShozokuMei((String) str1.get("ShozokuMei"));
            dto.setSeibetsuMei((String) str1.get("SeibetsuMei"));
            dto.setNyushaDate((String) str1.get("NyushaDate"));
            dto.setNaisen((String) str1.get("Naisen"));
            list.add(dto);
        }
        return list;
    }

    public ShainInfoDto getShainInfo(String code) {
        String selectSql = "SELECT " +
                "  CIF_OperatorM.Code " +
                " ,CIF_OperatorM.MeisyouKanji " +
                " ,CIF_OperatorM.ShozokuMei " +
                " ,CIF_OperatorM.SeibetsuMei " +
                " ,DATE_FORMAT(CIF_OperatorM.NyushaDate,'%Y/%m/%d') As NyushaDate " +
                " ,CIF_OperatorM.Naisen " +
                "FROM " +
                "  CIF_OperatorM ";

        String whereString = "";
        whereString = getWhereString("CIF_OperatorM.Code", code);
        selectSql = selectSql + " WHERE " + whereString;

        List<Map<String, Object>> shainList = jdbcTemplate.queryForList(selectSql);
        ShainInfoDto dto = new ShainInfoDto();
        Map<String, Object> str1 = shainList.get(0);
        dto.setCode((str1.get("Code")).toString());
        dto.setMeisyouKanji((String) str1.get("MeisyouKanji"));
        dto.setShozokuMei((String) str1.get("ShozokuMei"));
        dto.setSeibetsuMei((String) str1.get("SeibetsuMei"));
        dto.setNyushaDate((String) str1.get("NyushaDate"));
        dto.setNaisen((String) str1.get("Naisen"));
        return dto;
    }

    public void deleteShainInfo(String code) {
        try {
            // データベースへの接続を確立
            String deleteSql = "DELETE " + "FROM CIF_OperatorM WHERE " + getWhereString("CIF_OperatorM.Code", code);
            jdbcTemplate.update(deleteSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerShainInfo(RegisterForm form) {
        String updateSql;
        try {
            String selectSql = "SELECT * " + "FROM CIF_OperatorM WHERE "
                    + getWhereString("CIF_OperatorM.Code", form.getCode());
            List<Map<String, Object>> shainList = jdbcTemplate.queryForList(selectSql);
            if (shainList.size() != 0) {
                updateSql = "UPDATE CIF_OperatorM SET "
                        + "meisyouKanji = '" + form.getMeisyouKanji()
                        + "', "
                        + "shozokuMei = '" + form.getShozokuMei()
                        + "', "
                        + "seibetsuMei = '" + form.getSeibetsuMei()
                        + "', "
                        + "nyushaDate = '" + form.getNyushaDate()
                        + "', "
                        + "naisen = '" + form.getNaisen()
                        + "' WHERE " + getWhereString("Code", form.getCode());
            } else {
                String nyusyaDate = "".equals(form.getNyushaDate()) ? null : "'" + form.getNyushaDate() + "'";
                updateSql = "INSERT INTO CIF_OperatorM (meisyouKanji, shozokuMei, seibetsuMei, nyushaDate, naisen, code) VALUES( '"
                        + form.getMeisyouKanji()
                        + "', '"
                        + form.getShozokuMei()
                        + "', '"
                        + form.getSeibetsuMei()
                        + "', "
                        + nyusyaDate
                        + ", '"
                        + form.getNaisen()
                        + "', '"
                        + form.getCode()
                        + "')";
            }
            jdbcTemplate.update(updateSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getWhereString(String table, String param) {
        if ("".equals(param) || param == null) {
            return "";
        } else {
            return table + "=" + "'" + param + "'";
        }
    }

    public static String getWhereStringLike(String table, String param) {
        if ("".equals(param) || param == null) {
            return "";
        } else {
            return table + " Like " + "'%" + param + "%'";
        }
    }

    public static String getAnd(String[] whereString) {
        if (whereString == null) {
            return "";
        }

        int whereStringSize = whereString.length;
        String result = "";
        for (int i = 0; i < whereStringSize; i++) {
            if (!"".equals(whereString[i])) {
                if ("".equals(result)) {
                    result = whereString[i];
                } else {
                    result = result + " AND " + whereString[i];
                }
            }
        }
        return result;
    }

}
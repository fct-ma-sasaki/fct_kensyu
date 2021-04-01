package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.ShainInfoDto;
import com.example.demo.form.SearchForm;

@Repository
public class ShainInfoDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShainInfoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
        } finally {
        }
    }

    public void insertShainInfo(String code) {
        try {
            // データベースへの接続を確立
            String deleteSql = "DELETE " + "FROM CIF_OperatorM " + getWhereString("CIF_OperatorM.Code", code);
            jdbcTemplate.queryForList(deleteSql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void updateShainInfo(String code) {
        try {
            // データベースへの接続を確立
            String deleteSql = "DELETE " + "FROM CIF_OperatorM " + getWhereString("CIF_OperatorM.Code", code);
            jdbcTemplate.queryForList(deleteSql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    // データベースからの出力結果をArrayListオブジェクトに格納
    public static ArrayList<ShainInfoDto> getShainInfos() {
        ArrayList<ShainInfoDto> objAry = new ArrayList<ShainInfoDto>();
        Connection db = null;
        PreparedStatement objPs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list;
        try {
            // データベースへの接続を確立
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/SQLjdbc");
            db = ds.getConnection();
            objPs = db.prepareStatement("SELECT" + " CIF_OperatorM.Code " + " ,CIF_OperatorM.MeisyouKanji"
                    + " ,CIF_OperatorM.ShozokuMei" + " ,CIF_OperatorM.SeibetsuMei"
                    + " ,DATE_FORMAT(CIF_OperatorM.NyushaDate,'%Y/%m/%d') As NyushaDate" + " ,CIF_OperatorM.Naisen "
                    + "FROM " + "CIF_OperatorM " + "ORDER BY CODE ASC");
            rs = objPs.executeQuery();
            // 取得した結果セットをもとに、ShainInfo配列を生成
            while (rs.next()) {
                ShainInfoDto objBok = new ShainInfoDto();
                objBok.setCode(rs.getString("Code"));
                objBok.setMeisyouKanji(rs.getString("MeisyouKanji"));
                objBok.setShozokuMei(rs.getString("ShozokuMei"));
                if ("男".equals(rs.getString("SeibetsuMei"))) {
                    objBok.setSeibetsuMei("男男男男男男男男");
                } else if ("女".equals(rs.getString("SeibetsuMei"))) {
                    objBok.setSeibetsuMei("女女女女女女女女");
                } else {
                    objBok.setSeibetsuMei(rs.getString("SeibetsuMei"));
                }
                objBok.setNyushaDate(rs.getString("NyushaDate"));
                objBok.setNaisen(rs.getString("Naisen"));
                objAry.add(objBok);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (objPs != null) {
                    objPs.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objAry;
    }

    // 与えられたidに合致するデータをShainInfoオブジェクト
    // として返します。
    //    public static ShainInfoDto getShainInfo(String id) {
    //        Connection db = null;
    //        PreparedStatement objPs = null;
    //        ResultSet rs = null;
    //        ShainInfoDto objBok = new ShainInfoDto();
    //        try {
    //            // データベースへの接続を確立
    //            Context ctx = new InitialContext();
    //            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/SQLjdbc");
    //            db = ds.getConnection();
    //            objPs = db.prepareStatement("SELECT" + " CIF_OperatorM.Code " + " ,CIF_OperatorM.MeisyouKanji"
    //                    + " ,CIF_OperatorM.ShozokuMei" + " ,CIF_OperatorM.SeibetsuMei"
    //                    + " ,DATE_FORMAT(CIF_OperatorM.NyushaDate,'%Y/%m/%d') As NyushaDate" + " ,CIF_OperatorM.Naisen "
    //                    + "FROM " + "CIF_OperatorM " + " WHERE Code=?");
    //            objPs.setString(1, id);
    //            rs = objPs.executeQuery();
    //            // 取得したデータをShainInfoオブジェクトにセット
    //            if (rs.next()) {
    //                objBok.setCode(rs.getString("Code"));
    //                objBok.setMeisyouKanji(rs.getString("MeisyouKanji"));
    //                objBok.setShozokuMei(rs.getString("ShozokuMei"));
    //                objBok.setSeibetsuMei(rs.getString("SeibetsuMei"));
    //                objBok.setNyushaDate(rs.getString("NyushaDate"));
    //                objBok.setNaisen(rs.getString("Naisen"));
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        } finally {
    //            try {
    //                if (rs != null) {
    //                    rs.close();
    //                }
    //                if (objPs != null) {
    //                    objPs.close();
    //                }
    //                if (db != null) {
    //                    db.close();
    //                }
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //        }
    //        return objBok;
    //    }

    //    public static void deleteShainInfo(String id) {
    //        Connection db = null;
    //        PreparedStatement objPs = null;
    //        try {
    //            // データベースへの接続を確立
    //            Context ctx = new InitialContext();
    //            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/SQLjdbc");
    //            db = ds.getConnection();
    //            objPs = db.prepareStatement("DELETE " + "FROM CIF_OperatorM " + "WHERE Code=? ");
    //            objPs.setString(1, id);
    //            objPs.executeUpdate();
    //            // 取得したデータをShainInfoオブジェクトにセット
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        } finally {
    //            try {
    //                if (objPs != null) {
    //                    objPs.close();
    //                }
    //                if (db != null) {
    //                    db.close();
    //                }
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //        }
    //    }

    public String updateShainInfo(ShainInfoDto shainInfoDto) {
        String result = new String();
        Connection db = null;
        PreparedStatement objPs1 = null;
        PreparedStatement objPs2 = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/SQLjdbc");
            db = ds.getConnection();
            objPs1 = db.prepareStatement("SELECT * FROM CIF_OperatorM WHERE code=?");
            objPs1.setString(1, shainInfoDto.getCode());
            ResultSet rs = objPs1.executeQuery();
            if (rs.next()) {
                objPs2 = db.prepareStatement(
                        "UPDATE CIF_OperatorM SET meisyouKanji=?, shozokuMei=?, seibetsuMei=?, nyushaDate=?, naisen=? WHERE code=?");
                result = "2";
            } else {
                objPs2 = db.prepareStatement(
                        "INSERT INTO CIF_OperatorM (meisyouKanji, shozokuMei, seibetsuMei, nyushaDate, naisen, code) VALUES(?,?,?,?,?,?)");
                result = "1";
            }
            objPs2.setString(1, shainInfoDto.getMeisyouKanji());
            objPs2.setString(2, shainInfoDto.getShozokuMei());
            objPs2.setString(3, shainInfoDto.getSeibetsuMei());
            objPs2.setString(4, shainInfoDto.getNyushaDate());
            objPs2.setString(5, shainInfoDto.getNaisen());
            objPs2.setString(6, shainInfoDto.getCode());
            objPs2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objPs1 != null) {
                    objPs1.close();
                }
                if (objPs2 != null) {
                    objPs2.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //    public static ArrayList<ShainInfoDto> searchShainInfo(SearchForm searchForm) {
    //        ArrayList<ShainInfoDto> objAry = new ArrayList<ShainInfoDto>();
    //        Connection db = null;
    //        PreparedStatement objPs = null;
    //        ResultSet rs = null;
    //        try {
    //            // データベースへの接続を確立
    //            Context ctx = new InitialContext();
    //            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/SQLjdbc");
    //            db = ds.getConnection();
    //            String selectSql = "SELECT " +
    //                    "  CIF_OperatorM.Code " +
    //                    " ,CIF_OperatorM.MeisyouKanji " +
    //                    " ,CIF_OperatorM.ShozokuMei " +
    //                    " ,CIF_OperatorM.SeibetsuMei " +
    //                    " ,DATE_FORMAT(CIF_OperatorM.NyushaDate,'%Y/%m/%d') As NyushaDate " +
    //                    " ,CIF_OperatorM.Naisen " +
    //                    "FROM " +
    //                    "  CIF_OperatorM ";
    //
    //            String whereString = "";
    //            whereString = getAnd(new String[] { getWhereString("CIF_OperatorM.Code", searchForm.getCode()),
    //                    getWhereString("CIF_OperatorM.MeisyouKanji", searchForm.getMeisyouKanji()),
    //                    getWhereString("CIF_OperatorM.ShozokuMei", searchForm.getShozokuMei()),
    //                    getWhereString("CIF_OperatorM.SeibetsuMei", searchForm.getSeibetsuMei()),
    //                    getWhereString("CIF_OperatorM.Naisen", searchForm.getNaisen()) });
    //            if (!"".equals(whereString)) {
    //                selectSql = selectSql + " WHERE " + whereString;
    //            }
    //            selectSql = selectSql + "ORDER BY CODE ASC";
    //
    //            objPs = db.prepareStatement(selectSql);
    //            rs = objPs.executeQuery();
    //            // 取得した結果セットをもとに、ShainInfo配列を生成
    //            while (rs.next()) {
    //                ShainInfoDto objBok = new ShainInfoDto();
    //                objBok.setCode(rs.getString("Code"));
    //                objBok.setMeisyouKanji(rs.getString("MeisyouKanji"));
    //                objBok.setShozokuMei(rs.getString("ShozokuMei"));
    //                if ("男".equals(rs.getString("SeibetsuMei"))) {
    //                    objBok.setSeibetsuMei("男性");
    //                } else if ("女".equals(rs.getString("SeibetsuMei"))) {
    //                    objBok.setSeibetsuMei("女性");
    //                } else {
    //                    objBok.setSeibetsuMei(rs.getString("SeibetsuMei"));
    //                }
    //                objBok.setNyushaDate(rs.getString("NyushaDate"));
    //                objBok.setNaisen(rs.getString("Naisen"));
    //                objAry.add(objBok);
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        } finally {
    //            try {
    //                if (rs != null) {
    //                    rs.close();
    //                }
    //                if (objPs != null) {
    //                    objPs.close();
    //                }
    //                if (db != null) {
    //                    db.close();
    //                }
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //        }
    //        return objAry;
    //    }
    //    public static ArrayList<ShainInfoDto> searchShainInfo(SearchForm searchForm) {
    //        ArrayList<ShainInfoDto> objAry = new ArrayList<ShainInfoDto>();
    //        Connection db = null;
    //        PreparedStatement objPs = null;
    //        ResultSet rs = null;
    //        List<Map<String, Object>> list;
    //        try {
    //            // データベースへの接続を確立
    //            String selectSql = "SELECT " +
    //                    "  CIF_OperatorM.Code " +
    //                    " ,CIF_OperatorM.MeisyouKanji " +
    //                    " ,CIF_OperatorM.ShozokuMei " +
    //                    " ,CIF_OperatorM.SeibetsuMei " +
    //                    " ,DATE_FORMAT(CIF_OperatorM.NyushaDate,'%Y/%m/%d') As NyushaDate " +
    //                    " ,CIF_OperatorM.Naisen " +
    //                    "FROM " +
    //                    "  CIF_OperatorM ";
    //
    //            String whereString = "";
    //            whereString = getAnd(new String[] { getWhereString("CIF_OperatorM.Code", searchForm.getCode()),
    //                    getWhereString("CIF_OperatorM.MeisyouKanji", searchForm.getMeisyouKanji()),
    //                    getWhereString("CIF_OperatorM.ShozokuMei", searchForm.getShozokuMei()),
    //                    getWhereString("CIF_OperatorM.SeibetsuMei", searchForm.getSeibetsuMei()),
    //                    getWhereString("CIF_OperatorM.Naisen", searchForm.getNaisen()) });
    //            if (!"".equals(whereString)) {
    //                selectSql = selectSql + " WHERE " + whereString;
    //            }
    //            selectSql = selectSql + "ORDER BY CODE ASC";
    //
    //            jdbcTemplate.queryForList(selectSql);
    //
    //            // 取得した結果セットをもとに、ShainInfo配列を生成
    //            while (rs.next()) {
    //                ShainInfoDto objBok = new ShainInfoDto();
    //                objBok.setCode(rs.getString("Code"));
    //                objBok.setMeisyouKanji(rs.getString("MeisyouKanji"));
    //                objBok.setShozokuMei(rs.getString("ShozokuMei"));
    //                if ("男".equals(rs.getString("SeibetsuMei"))) {
    //                    objBok.setSeibetsuMei("男性");
    //                } else if ("女".equals(rs.getString("SeibetsuMei"))) {
    //                    objBok.setSeibetsuMei("女性");
    //                } else {
    //                    objBok.setSeibetsuMei(rs.getString("SeibetsuMei"));
    //                }
    //                objBok.setNyushaDate(rs.getString("NyushaDate"));
    //                objBok.setNaisen(rs.getString("Naisen"));
    //                objAry.add(objBok);
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        } finally {
    //            try {
    //                if (rs != null) {
    //                    rs.close();
    //                }
    //                if (objPs != null) {
    //                    objPs.close();
    //                }
    //                if (db != null) {
    //                    db.close();
    //                }
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //        }
    //        return objAry;
    //    }
}
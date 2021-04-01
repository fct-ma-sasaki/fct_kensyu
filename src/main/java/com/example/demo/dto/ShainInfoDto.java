package com.example.demo.dto;

import java.io.Serializable;

public final class ShainInfoDto implements Serializable {
    // 各レコードのフィールド値を格納するためのプライベート変数
    private String code;
    private String meisyouKanji;
    private String shozokuMei;
    private String seibetsuMei;
    private String nyushaDate;
    private String naisen;

    // プロパティ参照用のgetterメソッド
    public String getCode() {
        return code;
    }

    public String getMeisyouKanji() {
        return meisyouKanji;
    }

    public String getShozokuMei() {
        return shozokuMei;
    }

    public String getSeibetsuMei() {
        return seibetsuMei;
    }

    public String getNyushaDate() {
        return nyushaDate;
    }

    public String getNaisen() {
        return naisen;
    }

    // プロパティ設定用のsetterメソッド
    public void setCode(String code) {
        this.code = code;
    }

    public void setMeisyouKanji(String meisyouKanji) {
        this.meisyouKanji = meisyouKanji;
    }

    public void setShozokuMei(String shozokuMei) {
        this.shozokuMei = shozokuMei;
    }

    public void setSeibetsuMei(String seibetsuMei) {
        this.seibetsuMei = seibetsuMei;
    }

    public void setNyushaDate(String nyushaDate) {
        this.nyushaDate = nyushaDate;
    }

    public void setNaisen(String naisen) {
        this.naisen = naisen;
    }
}
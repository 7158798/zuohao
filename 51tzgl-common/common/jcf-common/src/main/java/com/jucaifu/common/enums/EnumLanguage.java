package com.jucaifu.common.enums;

/**
 * EnumLanguage
 *
 * @author scofieldcai
 *         <p>
 *         Created by scofieldcai-dev on 15/9/8.
 */
public enum EnumLanguage {

    /**
     * 中国－中文
     */
    CN_ZH("zh", "CN"),

    /**
     * 美国－英文
     */
    US_EN("en", "US"),

    //TODO:

    ;

    /**
     * The Language.
     */
    private String language;

    /**
     * The Country.
     */
    private String country;

    /**
     * Instantiates a new Enum language.
     *
     * @param language the language
     * @param country  the country
     */
    EnumLanguage(String language, String country) {
        this.language = language;
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }
}

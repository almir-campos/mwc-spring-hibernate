/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.complements;

/**
 *
 * @author almir
 */
public enum Language
{
    en_US("en_US", "English"),
    pt_BR("pt_BR", "Português");

    private String code;
    private String description;
    
    Language( String code, String description )
    {
        this.code = code;
        this.description = description;
    }
    public String getCode()
    {
        return this.code;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
    
    
}

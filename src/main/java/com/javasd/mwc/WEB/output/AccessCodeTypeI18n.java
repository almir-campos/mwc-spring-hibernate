/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.output;

/**
 *
 * @author almir
 */
public class AccessCodeTypeI18n
{
    private final String code;
    private final String description;

    public AccessCodeTypeI18n( String code, String description )
    {
        this.code = code;
        this.description = description;
    }

    public String getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }
    
    
}

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
public class MwcUserRoleI18n
{
    private final String code;
    private final String description;
    private final boolean active;

    public MwcUserRoleI18n( String code, String description, boolean active )
    {
        this.code = code;
        this.description = description;
        this.active = active;
    }

    public String getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isActive()
    {
        return active;
    } 
    
}

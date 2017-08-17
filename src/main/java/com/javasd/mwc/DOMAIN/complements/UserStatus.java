/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.complements;

/**
 *
 * @author almir
 */
public enum UserStatus
{
    REGISTERED("REGISTERED", "REGISTERED"),
    LOCKED("LOCKED", "LOCKED"),
    ERROR("ERROR", "ERROR"),
    DEACTIVATED("DEACTIVATED", "DEACTIVATED");

    private String code;
    private String description;
    
    UserStatus( String code, String description )
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

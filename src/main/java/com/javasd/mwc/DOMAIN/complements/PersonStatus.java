/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.complements;

/**
 *
 * @author almir
 */
public enum PersonStatus
{
    ERROR("ERROR", "Error"),
    NOT_INVITED("NOT_INVITED", "Not Invited"),
    INVITED("INVITED", "Invited"),
    WAITING_FOR_REGISTRATION("WAITING_FOR_REGISTRATION", "Waiting for Registration"),
    REGISTERED("REGISTERED", "Registered"),
    DEACTIVATED("DEACTIVATED", "Deactivated");

    private String code;
    private String description;
    
    PersonStatus( String code, String description )
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

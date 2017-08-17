/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.complements;

/**
 *
 * @author almir
 */
public enum MwcUserRoleType
{

    ROLE_ADMIN( "ROLE_ADMIN", "Role Admin" ),
    ROLE_MANAGER( "ROLE_MANAGER", "Role Manager" ),
    ROLE_USER( "ROLE_USER", "Role User" );
    private String code;
    private String description;

    MwcUserRoleType( String code, String description )
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.DOMAIN.complements;

import com.javasd.mwc.util.beans.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author almir
 */
public enum AccessCodeType
{
    ADMIN("ADMIN", "access_code_type_admin"),
    MANAGER("MANAGER", "access_code_type_manager"),
    USER("USER", "access_code_type_user"),
    NOT_USER("NOT_USER", "access_code_type_not_user");

    private final String code;
    private final String description;
    
    AccessCodeType( String code, String description )
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

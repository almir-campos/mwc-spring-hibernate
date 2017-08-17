/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.javasd.mwc.util.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Almir
 */
@Component( "mwcConfig")
public class MwcConfig
{
    // GENERAL USE
    @Value( "${mwc.internet.base.url}")
    private String mwcInternetBaseUrl;
    // DATABASE
    @Value( "${mwc.jdbc.driverClassName}")
    private String mwcJdbcDriverClassName;
    @Value( "${mwc.jdbc.url}")
    private String mwcJdbcUrl;
    @Value( "${mwc.jdbc.username}")
    private String mwcJdbcUsername;
    @Value( "${mwc.jdbc.password}")
    private String mwcJdbcPassword;

    public String getMwcInternetBaseUrl()
    {
        return mwcInternetBaseUrl;
    }

    public String getMwcJdbcDriverClassName()
    {
        return mwcJdbcDriverClassName;
    }

    public String getMwcJdbcUrl()
    {
        return mwcJdbcUrl;
    }

    public String getMwcJdbcUsername()
    {
        return mwcJdbcUsername;
    }

    public String getMwcJdbcPassword()
    {
        return mwcJdbcPassword;
    }
    
}

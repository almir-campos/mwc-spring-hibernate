/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.beans;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 *
 * @author Almir
 */
@Component( "springUser" )
public class SpringUser
{
    public User getUser()
    {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( obj.toString().equals( "anonymousUser" ) )
        {
            return null;
        }
        return (User) obj;
    }

}

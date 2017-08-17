/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.javasd.mwc.util.beans;

import com.javasd.mwc.util.misc.MiscStatic;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Almir
 */
@Component( "mwcPasswordEncoder" )
public class MwcPasswordEncoder implements PasswordEncoder
{

    Logger logger = Logger.getLogger( MwcPasswordEncoder.class );
            
    @Override
    public String encode( CharSequence fromUser )
    {
        CharSequence csEnc = MiscStatic.mixer( ( String ) fromUser);
        //logger.info( "encode/fromUser: " + fromUser );
        return (String) fromUser;
    }

    @Override
    public boolean matches( CharSequence fromUser, String fromDB )
    {
       // logger.info( "matches/fromUser: " + fromUser + ", fromDB: " + fromDB );
//        return true;
        return ( MiscStatic.mixer( (String)fromUser ) ).equals( fromDB );
    }
    
}

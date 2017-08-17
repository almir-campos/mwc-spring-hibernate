/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER.mwcuser;

import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.SERVICE.mwcuser.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Almir Campos
 */
@RequestMapping ( "/admin/jsp" )
@Controller
public class MwcUserControllerJsp
{
    private final Logger logger = Logger.getLogger( MwcUserControllerJsp.class );

    @Autowired
    private UserService userService;

    @RequestMapping ( value = "/listusers", method = RequestMethod.GET )
    public String listusers( Model uiModel )
    {
        uiModel.addAttribute( "users", userService.getAllByLastName());
        return "mwcUser/list";
    }
    
    @RequestMapping( value = "/showUser/{id}", method = RequestMethod.GET )
    public String showUser( @PathVariable( "id") Long id, Model uiModel )
    {
        MwcUser mwcUser = userService.get( id );
        uiModel.addAttribute( " mwcUser ", mwcUser );
        return "mwcUser/show";
        
    }

}

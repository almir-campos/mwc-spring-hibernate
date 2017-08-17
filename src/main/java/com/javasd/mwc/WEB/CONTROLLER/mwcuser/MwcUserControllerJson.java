/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER.mwcuser;

import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.SERVICE.mwcuser.UserService;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Almir Campos
 */
@RequestMapping ( "/admin/json" )
@Controller
public class MwcUserControllerJson
{
    private final Logger logger = Logger.getLogger( MwcUserControllerJson.class );

    @Autowired
    private UserService userService;

    @RequestMapping ( value = "/listusers", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public List<MwcUser> listusersJson()
    {
        return userService.getAll();
    }

    @RequestMapping ( value = "/listuser", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public MwcUser listuserJson( @RequestParam ( value = "id", required = true ) Long id )
    {
        return userService.get( id );
    }

    @RequestMapping ( value = "/listuser/series", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public List<Series> listuserSeriesJson( @RequestParam ( value = "id", required = true ) Long id )
    {
        
        return ( List<Series> ) userService.get( id ).getSeriess();
    }

}

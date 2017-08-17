/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER.mwcuser;

import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.SERVICE.mwcuser.UserService;
import com.javasd.mwc.util.xml.ObjToXML;
import com.javasd.mwc.util.xml.XMLBuilder;
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
@RequestMapping ( "/admin/xml" )
@Controller
public class MwcUserControllerXml
{
    private final Logger logger = Logger.getLogger( MwcUserControllerXml.class );

    @Autowired
    private UserService userService;

    @RequestMapping ( value = "/listusers", method = RequestMethod.GET, produces = "application/xml" )
    @ResponseBody
    public String listusersXML()
    {
        List<MwcUser> users = userService.getAll();
        ObjToXML otx;

        StringBuilder sb = new StringBuilder();
        sb.append( XMLBuilder.HEADER );
        sb.append( XMLBuilder.getTag( "users", true ) );
        for ( MwcUser u : users )
        {
//           otx = new ObjToXML(u, new String[]{ "getFirstName", "lastName", "lclass1", "arr", "birthDateString" } );
            otx = new ObjToXML( u, null );
            sb.append( otx.getFromFieldsXML( "mwcUser", false ) );
        }
        sb.append( XMLBuilder.getTag( "users", false ) );
        return sb.toString();
    }

    @RequestMapping ( value = "/listuser", method = RequestMethod.GET, produces = "application/xml" )
    @ResponseBody
    public String listuserXML( @RequestParam ( value = "id", required = true ) Long id )
    {
        MwcUser mwcUser = userService.get( id );
        ObjToXML otx = new ObjToXML( mwcUser, new String[]
        {
            "id", "getFirstName", "lastName", "lclass1", "arr", "birthDateString", "series"
        } );

        String xml = otx.getFromFieldsXML( "mwcUser", false );

        return xml;
    }

    @RequestMapping ( value = "/listuser/series", method = RequestMethod.GET, produces = "application/xml" )
    @ResponseBody
    public String listuserSeriesXML( @RequestParam ( value = "id", required = true ) Long id )
    {
        MwcUser mwcUser = userService.get( id );
        List<Series> series = ( List<Series> ) mwcUser.getSeriess();
        
        ObjToXML otx;

        StringBuilder sb = new StringBuilder();
        sb.append( XMLBuilder.HEADER );
        sb.append( XMLBuilder.getTag( "series", true ) );
        for ( Series s : series )
        {
            otx = new ObjToXML( s, null );
            sb.append( otx.getFromFieldsXML( "series", false ) );
        }
        sb.append( XMLBuilder.getTag( "series", false ) );
        return sb.toString();
    }

// IT'S TIME TO USE GENERICS TO DO THE CRUD OF THE REPOSITORIES
//
//    @RequestMapping ( value = "/listuser/series/seriesDetails", method = RequestMethod.GET, produces = "application/xml" )
//    @ResponseBody
//    public String listuserSerieDetailsXML( 
//            @RequestParam ( value = "idUser", required = true ) Long idUser, 
//            @RequestParam ( value = "iSerie", required = true ) Long idSerie )
//    {
//        MwcUser mwcUser = userService.findById( idUser );
//        List<Serie> series = mwcUser.getSeries();
//        
//        ObjToXML otx;
//
//        StringBuilder sb = new StringBuilder();
//        sb.append( XMLBuilder.HEADER );
//        sb.append( XMLBuilder.getTag( "series", true ) );
//        for ( Series s : series )
//        {
//            otx = new ObjToXML( s, null );
//            sb.append( otx.getFromFieldsXML( "series", false ) );
//        }
//        sb.append( XMLBuilder.getTag( "series", false ) );
//        return sb.toString();
//    }

}

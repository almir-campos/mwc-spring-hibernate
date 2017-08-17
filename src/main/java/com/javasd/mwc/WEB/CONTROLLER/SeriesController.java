/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER;

import com.javasd.mwc.annotations.MWC_SameUser;
import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.SeriesDetail;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.SERVICE.seriesdetail.SeriesDetailService;
import com.javasd.mwc.SERVICE.series.SeriesService;
import com.javasd.mwc.SERVICE.mwcuser.UserService;
import com.javasd.mwc.WEB.output.PersonBasicData;
import com.javasd.mwc.annotations.MWC_LastScreen;
import com.javasd.mwc.annotations.MWC_Realm;
import com.javasd.mwc.util.beans.MiscDynamic;
import com.javasd.mwc.util.beans.ObjectFiller;
import com.javasd.mwc.util.beans.ObjectMerger;
import com.javasd.mwc.util.misc.MiscStatic;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author almir
 */
@RequestMapping( value = "/series" )
@Controller
public class SeriesController
{

    private final Logger logger = Logger.getLogger( SeriesController.class );
    @Autowired
    UserService userService;
    @Autowired
    SeriesService seriesService;
    @Autowired
    SeriesDetailService seriesDetailService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ObjectMerger objectMerger;
    @Autowired
    private ObjectFiller objectFiller;
    @Autowired
    private MiscDynamic miscDynamic;

    ////////////////////////////////////////////////////////////////////////////
    //
    // ADD
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_LastScreen
    @MWC_Realm( value = "user" )
    @RequestMapping( value = "/add/{userId}", method = RequestMethod.GET )
    public String add(
            @PathVariable( "userId" ) Long userId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        MwcUser loadedUser = userService.get( userId );
        uiModel.addAttribute( "series", new Series() );
        uiModel.addAttribute( "mwcUser", miscDynamic.getMwcUser01( loadedUser ) );
        uiModel.addAttribute( "userId", userId );
        return "series/add";
    }

    //@MWC_SameUser
    @RequestMapping( value = "/add/{userId}", method = RequestMethod.POST )
    public String add(
            @PathVariable( "userId" ) Long userId,
            @RequestParam( value = "file", required = false ) Part file,
            @Valid Series series,
            BindingResult result,
            Model uiModel )
    {

        if ( result.hasErrors() )
        {
            uiModel.addAttribute( "errors", result.getAllErrors() );
            uiModel.addAttribute( "userId", userId );
            return "series/add";
        }

        MwcUser mwcUser = userService.get( userId );
        series.setMwcUser( mwcUser );

        if ( file != null )
        {
            try
            {
                byte[] fileContent = null;
                InputStream inputStream = file.getInputStream();
                if ( inputStream == null )
                {
                    logger.info( "File inputstream is null" );
                }
                else
                {
                    fileContent = IOUtils.toByteArray( inputStream );
                    series.setInitialPhoto( fileContent );
//                    savedUser = userService.save(mwcUser);
                    //ObjToXML otx = new ObjToXML( savedUser, null );
                    //return otx.getFromFieldsXML( "savedUser", true );
                }
            }
            catch ( IOException e )
            {
                logger.error( "*************** Error saving uploaded file" );
                e.printStackTrace();
            }
        }
        else
        {
            logger.info( "***************** avatarImg ??" );
        }

        mwcUser.getSeriess().add( series );
        userService.update( mwcUser );
        return "redirect:/mwcUser/listseries/" + userId;
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // SHOW
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_LastScreen
    @MWC_Realm( value = "series" )
    @RequestMapping( value = "/show/{seriesId}" )
    public String show( @PathVariable( "seriesId" ) Long seriesId, Model uiModel )
    {
        Series series = seriesService.get( seriesId );
        Person person = series.getMwcUser().getPerson();
        uiModel.addAttribute( "person", objectFiller.fillFields( person, PersonBasicData.class ) );
        uiModel.addAttribute( "series", series );
        return "series/show";

    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // EDIT
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Realm( value = "series" )
    @MWC_LastScreen
    @RequestMapping( value = "/edit/{seriesId}", method = RequestMethod.GET )
    public String edit(
            @PathVariable( "seriesId" ) Long seriesId,
            Locale locale,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        Series series = seriesService.getWithSeriesDetails( seriesId );
//        Set<SeriesDetail> seriesDetails = series.getSeriesDetails();
//        uiModel.addAttribute( "seriesMwcUser", series.getMwcUser() );
//        uiModel.addAttribute( "seriesDetails", seriesDetails );
        Person person = series.getMwcUser().getPerson();
        uiModel.addAttribute( "person", objectFiller.fillFields( person, PersonBasicData.class ) );
        uiModel.addAttribute( "series", series );
        return "series/edit";

    }

    @MWC_SameUser
    @RequestMapping( value = "/edit", method = RequestMethod.POST )
    public String edit(
            @Valid Series series,
            BindingResult result,
            Model uiModel )
    {
        List<ObjectError> errors = result.getAllErrors();
        if ( !errors.isEmpty() )
        {
            uiModel.addAttribute( "currentId", series.getId() );
            uiModel.addAttribute( "series", series );
            uiModel.addAttribute( "errors", errors );
            return "series/edit";

        }
        Series loadedSeries = seriesService.getWithSeriesDetails( series.getId() );
        objectMerger.mergeFields( series, loadedSeries );
        Series updatedSeries = seriesService.update( loadedSeries );
        uiModel.addAttribute( "series", updatedSeries );
        return "redirect:/series/show/" + series.getId();

    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // DELETE
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_LastScreen
    @MWC_Realm( value = "series" )
    @RequestMapping( value = "/delete/{seriesId}" )
    public String delete( @PathVariable( "seriesId" ) Long seriesId, Model uiModel )
    {
        Series series = seriesService.get( seriesId );
        MwcUser mwcUser = series.getMwcUser();
        mwcUser.getSeriess().remove( series );
        userService.update( mwcUser );
        return "redirect:/mwcUser/listseries/" + mwcUser.getId();

    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // SHOW SERIES DETAILS
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_LastScreen
    @MWC_Realm( value = "series" )
    @RequestMapping( value = "/details/{seriesId}", method = RequestMethod.GET )
    public String seriesDetails(
            @PathVariable( "seriesId" ) Long seriesId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        Series series = seriesService.get( seriesId );
        List<SeriesDetail> seriesDetails = seriesDetailService.getAllByWeightDate( series );
        uiModel.addAttribute( "series", series );
        uiModel.addAttribute( "seriesDetails", seriesDetails );
        return "seriesDetail/list";

    }

    @MWC_SameUser
    @RequestMapping( value = "/details/table/{seriesId}", method = RequestMethod.GET )
    public String seriesDetailsTable( @PathVariable( "seriesId" ) Long seriesId, Model uiModel )
    {
        Series series = seriesService.get( seriesId );
        List<SeriesDetail> seriesDetails = seriesDetailService.getAllByWeightDate( series );
        uiModel.addAttribute( "series", series );
        uiModel.addAttribute( "seriesDetails", seriesDetails );
        return "complements/ajax/seriesdetailstableload";

    }

    @MWC_SameUser
    @RequestMapping( value = "/seriesInitialPhoto/{seriesId}", method = RequestMethod.GET )
    @ResponseBody
    public byte[] seriesInitialPhoto( @PathVariable( "seriesId" ) Long seriesId )
    {
        Series series = seriesService.get( seriesId );
        return series.getInitialPhoto();
    }

    @MWC_SameUser
    @RequestMapping( value = "/updateSeriesData", method = RequestMethod.POST )
    @ResponseBody
    public String updateSeriesData(
            @RequestParam( "seriesId" ) Long seriesId,
//            @RequestParam( "initialWeight" ) Double initialWeight,
            @RequestParam( "goalWeight" ) Double goalWeight,
            @RequestParam( "startDateString" ) String startDateString,
            @RequestParam( "estimatedEndDateString" ) String estimatedEndDateString,
            Locale locale,
            Model uiModel )
    {
        Series series = seriesService.get( seriesId );
//        series.setInitialWeight( initialWeight );
        series.setGoalWeight( goalWeight );
        series.setStartDate( MiscStatic.toDateTimeFromStringJodaTime( startDateString ) );
        series.setEstimatedEndDate( MiscStatic.toDateTimeFromStringJodaTime( estimatedEndDateString ) );
        Series updatedSeries = seriesService.update( series );
        return messageSource.getMessage( "message_edit_update_series_data",
                                         new Object[]
        {
                                         },
                                         locale );
    }

    @MWC_SameUser
    @RequestMapping( value = "/updatePhoto", method = RequestMethod.POST )
    @ResponseBody
    public String updatePhoto(
            @RequestParam( "seriesId" ) Long seriesId,
            @RequestParam( value = "file" ) Part file )
    {
        byte[] photo = null;
        if ( file != null )
        {
            try
            {
                InputStream inputStream = file.getInputStream();
                if ( inputStream == null )
                {
                    logger.info( "File inputstream is null" );
                }
                else
                {
                    Series series = seriesService.get( seriesId );
                    photo = IOUtils.toByteArray( inputStream );
                    series.setInitialPhoto( photo );
                    seriesService.update( series );
                }
            }
            catch ( IOException e )
            {
                logger.error( "*************** Error saving uploaded file" );
                e.printStackTrace();
            }
        }
        else
        {
            logger.info( "***************** avatarImg ??" );
        }
        return null;
    }

    @MWC_SameUser
    @RequestMapping( value = "/seriesphotoload", method = RequestMethod.GET )
    public String seriesPhotoLoad( @RequestParam( "seriesId" ) Long seriesId, Model uiModel )
    {
        uiModel.addAttribute( "id", seriesId );
        return "complements/ajax/seriesphotoload";
    }
}

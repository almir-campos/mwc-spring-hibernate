/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER;

import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.SeriesDetail;
import com.javasd.mwc.SERVICE.series.SeriesService;
import com.javasd.mwc.SERVICE.seriesdetail.SeriesDetailService;
import com.javasd.mwc.WEB.output.Measurement;
import com.javasd.mwc.WEB.output.SeriesDetailBasicData;
import com.javasd.mwc.annotations.MWC_SameUser;
import com.javasd.mwc.util.misc.MiscStatic;
import com.javasd.utils.mathutils.MathUtils;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author almir
 */
@Controller
@RequestMapping( "/seriesDetail" )
public class SeriesDetailController
{

    private final Logger logger = Logger.getLogger( SeriesController.class );
    @Autowired
    private SeriesDetailService seriesDetailService;

    @Autowired
    private SeriesService seriesService;

    ////////////////////////////////////////////////////////////////////////////
    //
    // ADD
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_SameUser
    @RequestMapping( value = "/add/{seriesId}", method = RequestMethod.POST )
    @ResponseBody
    public Series add(
            SeriesDetail seriesDetail,
            @PathVariable( "seriesId" ) Long seriesId )
    {
        Series loadedSeries = seriesService.getWithSeriesDetails( seriesId );
        seriesDetail.setSeries( loadedSeries );
        loadedSeries.getSeriesDetails().add( seriesDetail );
        return seriesService.update( loadedSeries );
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // DELETE
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_SameUser
    @RequestMapping( value = "/delete/{seriesDetailId}", method = RequestMethod.POST )
    @ResponseBody
    public void delete( @PathVariable( "seriesDetailId" ) Long seriesDetailId )
    {
//        SeriesDetail seriesDetail = seriesDetailService.get( seriesDetailId );
//        if ( false )
//        {
//            Series series = seriesDetail.getSeries();
//            series.getSeriesDetails().remove( seriesDetail );
//            seriesService.update( series );
//        }
        seriesDetailService.delete( seriesDetailId );
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // EDIT
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_SameUser
    @RequestMapping( value = "/update", method = RequestMethod.POST )
    @ResponseBody
    public String update(
            @RequestParam( "seriesDetailId" ) Long seriesDetailId,
            @RequestParam( "weightDate" ) String weightDate,
            @RequestParam( "weight" ) Double weight,
            @RequestParam( "comment" ) String comment )

    {
        SeriesDetail seriesDetail = seriesDetailService.get( seriesDetailId );
        DateTimeFormatter formatter = DateTimeFormat.forPattern( "yyyy-MM-dd" );
        DateTime dt = formatter.parseDateTime( weightDate );
        seriesDetail.setWeightDate( dt );
        seriesDetail.setWeight( weight );
        seriesDetail.setComment( comment );
        seriesDetailService.update( seriesDetail );
        return "Ok";
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // OTHER
    //
    ////////////////////////////////////////////////////////////////////////////
    @RequestMapping( value = "/simpleRegressionData/{seriesId}", method = RequestMethod.POST )
    @ResponseBody
    public SeriesDetailBasicData simpleRegressionData(
            @PathVariable( "seriesId" ) Long seriesId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        Series series = seriesService.get( seriesId );
        List<SeriesDetail> seriesDetails = seriesDetailService.getAllByWeightDateAsc( series );
        
        double[][] series0 = new double[ seriesDetails.size() ][ 2 ];
//        List<SeriesDetailBasicData> seriesDetailsBasicData = new ArrayList<SeriesDetailBasicData>();
        SeriesDetailBasicData seriesDetailBasicData = new SeriesDetailBasicData();
        for ( int i = 0; i < series0.length; i++ )
        {
            series0[ i ][ 0] = 1.0 * Days.
                    daysBetween( seriesDetails.get( 0 ).getWeightDate(), seriesDetails.get( i ).getWeightDate() ).
                    getDays();
            series0[ i ][ 1 ] = seriesDetails.get( i ).getWeight();
            
        }

        SimpleRegression simpleRegression = new SimpleRegression();
        simpleRegression.addData( series0 );
        Measurement measurement;
        for ( int i = 0; i < series0.length; i++ )
        {
           measurement = new Measurement( seriesDetails.get( i ).getWeightDateString(), i, series0[ i ][ 0 ], series0[ i ] [ 1 ], simpleRegression.predict( series0[ i ][ 0 ] ) );
           seriesDetailBasicData.getMeasurements().add( measurement );
        }
        
        seriesDetailBasicData.setSlope( simpleRegression.getSlope() );
        seriesDetailBasicData.setIntercept( simpleRegression.getIntercept() );
        
        int totalDays = (int) MathUtils.round ( ( series.getGoalWeight() - simpleRegression.getIntercept() ) / simpleRegression.getSlope(), 0);
        DateTime currentEstimatedGoalDate = seriesDetails.get( 0 ).getWeightDate().plusDays( totalDays );
        int todayDaysToGoal = Days.daysBetween( new DateTime(), currentEstimatedGoalDate ).getDays();
        seriesDetailBasicData.setTotalDaysToGoal( totalDays );
        seriesDetailBasicData.setTodayDaysToGoal( todayDaysToGoal );
        seriesDetailBasicData.setCurrentEstimatedGoalDate( MiscStatic.stringJodaDateTime( currentEstimatedGoalDate ));
//        XMLBuilder xmlb = new XMLBuilder( null, false );
//        return xmlb.getXMLBlockFromDoubleArray( series, "data", "item" );
        
        return seriesDetailBasicData;
    }
}

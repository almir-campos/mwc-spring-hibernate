/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.javasd.mwc.WEB.output;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Almir
 */
public class SeriesDetailBasicData
{
    private List<Measurement> measurements;
    private double slope;
    private double intercept;
    private int totalDaysToGoal;
    private int todayDaysToGoal;
    private String currentEstimatedGoalDate;

    public SeriesDetailBasicData()
    {
        this.measurements = new ArrayList<Measurement>();
    }

    
    public List<Measurement> getMeasurements()
    {
        return measurements;
    }

    public void setMeasurements( List<Measurement> measurements )
    {
        this.measurements = measurements;
    }

    public double getSlope()
    {
        return slope;
    }

    public void setSlope( double slope )
    {
        this.slope = slope;
    }

    public double getIntercept()
    {
        return intercept;
    }

    public void setIntercept( double intercept )
    {
        this.intercept = intercept;
    }

    public int getTodayDaysToGoal()
    {
        return todayDaysToGoal;
    }

    public void setTodayDaysToGoal( int todayDaysToGoal )
    {
        this.todayDaysToGoal = todayDaysToGoal;
    }

    public int getTotalDaysToGoal()
    {
        return totalDaysToGoal;
    }

    public void setTotalDaysToGoal( int totalDaysToGoal )
    {
        this.totalDaysToGoal = totalDaysToGoal;
    }

    public String getCurrentEstimatedGoalDate()
    {
        return currentEstimatedGoalDate;
    }

    public void setCurrentEstimatedGoalDate( String currentEstimatedGoalDate )
    {
        this.currentEstimatedGoalDate = currentEstimatedGoalDate;
    }

}

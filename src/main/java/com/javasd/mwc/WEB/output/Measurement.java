/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.javasd.mwc.WEB.output;

/**
 *
 * @author Almir
 */
public class Measurement
{
    private final String date;
    private final int sequential;
    private final double dayNumber;
    private final double weight;
    private final double weightTrendLine;

    public Measurement( String date, int sequential, double dayNumber, double weight, double weightTrendLine )
    {
        this.date = date;
        this.sequential = sequential;
        this.dayNumber = dayNumber;
        this.weight = weight;
        this.weightTrendLine = weightTrendLine;
    }

    public String getDate()
    {
        return date;
    }

    public int getSequential()
    {
        return sequential;
    }

    public double getDayNumber()
    {
        return dayNumber;
    }

    public double getWeight()
    {
        return weight;
    }

    public double getWeightTrendLine()
    {
        return weightTrendLine;
    }
}

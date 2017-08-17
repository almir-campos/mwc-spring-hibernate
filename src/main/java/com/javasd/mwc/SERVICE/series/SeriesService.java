/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.series;

import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import java.util.List;

/**
 *
 * @author almir
 */
public interface SeriesService
{
	public Series get( Long id );
        public Series getWithSeriesDetails( Long id );
        public List<Series> getAllByStartDateDesc( MwcUser mwcUser );
        public Series update( Series series );
}

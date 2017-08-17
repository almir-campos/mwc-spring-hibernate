/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.seriesdetail;

import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.SeriesDetail;
import java.util.List;

/**
 *
 * @author almir
 */
public interface SeriesDetailService
{
	public SeriesDetail get( Long id );
	public List<SeriesDetail> getAllByWeightDate( Series series );
	public List<SeriesDetail> getAllByWeightDateAsc( Series series );
        public SeriesDetail update( SeriesDetail seriesDetail );
        public void delete( Long id );
}

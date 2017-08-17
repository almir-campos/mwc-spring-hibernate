/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.seriesdetail;

import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.SeriesDetail;
import com.javasd.mwc.REPOSITORY.generic.GenericRepository;
import java.util.List;

/**
 *
 * @author Almir Campos
 */
public interface SeriesDetailRepository
	extends GenericRepository<SeriesDetail>
{
    public List<SeriesDetail> getAllByWeightDate(Series series );
    public List<SeriesDetail> getAllByWeightDateAsc(Series series );
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.series;

import com.javasd.mwc.REPOSITORY.generic.GenericRepository;
import com.javasd.mwc.DOMAIN.entity.Series;
import com.javasd.mwc.DOMAIN.entity.MwcUser;
import java.util.List;

/**
 *
 * @author Almir Campos
 */
public interface SeriesRepository
	extends GenericRepository<Series>
{
    @Override
    public Series get( Long id );
    public Series getWithSeriesDetails( Long id );
    public List<Series> getAllByStartDateDesc(MwcUser mwcUser );
}

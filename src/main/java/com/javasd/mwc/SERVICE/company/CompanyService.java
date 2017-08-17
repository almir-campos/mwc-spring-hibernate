/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.company;

import com.javasd.mwc.DOMAIN.entity.Company;
import java.util.List;

/**
 *
 * @author almir
 */
public interface CompanyService
{
    public List<Company> getAllByName();
    public Company get( Long id );
    public Object[] getBasicData( Long id );
    public Company save ( Company company );
    public Company update ( Company company );
    public void delete( Long id );
}

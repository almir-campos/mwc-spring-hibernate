/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.company;

import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.REPOSITORY.generic.GenericRepository;
import java.util.List;

/**
 *
 * @author almir
 */
public interface CompanyRepository extends GenericRepository<Company>
{
    public List<Company> getAllByName();
    public Object[] getBasicData( Long id );
}

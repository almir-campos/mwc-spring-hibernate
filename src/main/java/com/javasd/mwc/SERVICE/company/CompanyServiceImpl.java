/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.company;

import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.REPOSITORY.company.CompanyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author almir
 */
@Service ( "companyService" )
@Repository
@Transactional
public class CompanyServiceImpl
    implements CompanyService
{

    @Autowired
    private CompanyRepository companyRepository;
    
    @Override
    @Transactional( readOnly = true )
    public List<Company> getAllByName()
    {
        return companyRepository.getAllByName();
    }

    @Override
    @Transactional( readOnly = true )
    public Company get( Long id )
    {
        return companyRepository.get( id );
    }

    @Override
    public Company save( Company company )
    {
        return companyRepository.save( company );
    }

    @Override
    public Company update( Company company )
    {
        return companyRepository.update( company );
    }

    @Override
    public void delete( Long id )
    {
        companyRepository.delete( id );
    }

    @Override
    public Object[] getBasicData( Long id )
    {
        return companyRepository.getBasicData( id );
    }
    
}

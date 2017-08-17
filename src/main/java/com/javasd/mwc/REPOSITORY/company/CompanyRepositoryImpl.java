/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.company;

import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.REPOSITORY.generic.GenericRepositoryImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author almir
 */
@Repository( "companyRepository")
public class CompanyRepositoryImpl
        extends GenericRepositoryImpl<Company>
        implements CompanyRepository
{

    public CompanyRepositoryImpl()
    {
        super( Company.class );
    }

    @Override
    public List<Company> getAllByName()
    {
        return ( List<Company> ) entityManager.createNamedQuery( "Company.getAllByName" ).getResultList();
    }

    @Override
    public Object[] getBasicData( Long id )
    {
//        List<Company> companies = ( List<Company> ) entityManager.
//                createNamedQuery( "Company.getBasicData" ).
//                setParameter( "id", id ).
//                getResultList();
//        if ( companies.isEmpty() )
//        {
//            return null;
//        }
//        return (Company) companies.get( 0 );
        Object[] company = ( Object[] ) entityManager.
                createNamedQuery( "Company.getBasicData" ).
                setParameter( "id", id ).
                getSingleResult();
        return company;
    }
}

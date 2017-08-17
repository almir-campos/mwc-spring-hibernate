/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.branch;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.REPOSITORY.generic.GenericRepositoryImpl;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author almir
 */
@Repository( "branchRepository" )
public class BranchRepositoryImpl
    extends GenericRepositoryImpl<Branch>
    implements BranchRepository
{

//    @Autowired
//    private PersonInBranchRepository personInBranchRepository;
    
    private final Logger logger = Logger.getLogger( this.getClass() );
    
    public BranchRepositoryImpl( )
    {
        super( Branch.class );
    }

    @Override
    public List<Branch> getAllByAcronymForCompany( Company company )
    {
        return ( List<Branch> ) entityManager.createNamedQuery( "Branch.getAllByAcronymForCompany").setParameter( "company", company ).getResultList();
    }

    @Override
    public List<Branch> getAllByCompanyAndAcronym()
    {
        return ( List<Branch> ) entityManager.createNamedQuery( "Branch.getAllByCompanyAndAcronym").getResultList();
    }
    
    @Override
    public Branch getWithPeople( Long id )
    {
        Branch branch = get( id );
        // THE NEXT LINE IS A TRICK TO FORCE LOADING THE LAZY FIELD PERSONINBRANCH.
        // SEE A MORE COMPLETE SOLUTION AT SeriesRepositoryImpl / getWithSeriesDetails
        branch.getPersonInBranch().size();
        return branch;
    } 
    
}

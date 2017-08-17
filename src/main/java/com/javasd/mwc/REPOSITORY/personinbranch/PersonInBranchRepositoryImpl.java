/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.personinbranch;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.DOMAIN.entity.PersonInBranch;
import com.javasd.mwc.REPOSITORY.generic.GenericRepositoryImpl;
import com.javasd.mwc.SERVICE.branch.BranchService;
import com.javasd.mwc.SERVICE.person.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author almir
 */
@Repository( "personInBranchRepository" )
public class PersonInBranchRepositoryImpl
        extends GenericRepositoryImpl<PersonInBranch>
        implements PersonInBranchRepository
{

    @Autowired
    private BranchService branchService;
    @Autowired
    private PersonService personService;

    public PersonInBranchRepositoryImpl()
    {
        super( PersonInBranch.class );
    }

    @Override
    public PersonInBranch getByPerson( Person person )
    {
        List<PersonInBranch> peopleInBranch = null;

        peopleInBranch = ( List<PersonInBranch> ) entityManager.
                createNamedQuery( "PersonInBranch.getByPerson" ).
                setParameter( "person", person ).
                getResultList();
        if ( peopleInBranch.size() == 0 )
        {
            return null;
        }
        return peopleInBranch.get( 0 );
    }

//    @Override
//    public PersonInBranch get( Person person, Branch branch )
//    {
//        List<PersonInBranch> peopleInBranch = null;
//        
//        peopleInBranch = (List<PersonInBranch>) entityManager.
//                createNamedQuery("PersonInBranch.getByPersonAndBranch" ).
//                setParameter( "person", person).
//                setParameter( "branch", branch).
//                getResultList();
//        if ( peopleInBranch.size() == 0 )
//        {
//            return null;
//        }
//        return peopleInBranch.get( 0 );
//    }
    @Override
    public List<PersonInBranch> getPeopleInBranch( Branch branch )
    {
        List<PersonInBranch> peopleInBranch = null;

        peopleInBranch = ( List<PersonInBranch> ) entityManager.
                createNamedQuery( "PersonInBranch.getByBranch" ).
                setParameter( "branch", branch ).
                getResultList();
        if ( peopleInBranch.size() == 0 )
        {
            return null;
        }
        return peopleInBranch;
    }

    @Override
    public PersonInBranch getByBranchPerson( Branch branch, Person person )
    {
        return ( PersonInBranch ) entityManager.
                createNamedQuery( "PersonInBranch.getByBranchPerson" ).
                setParameter( "branch", branch ).
                setParameter( "person", person ).
                getSingleResult();
    }

    @Override
    public Long deleteByBranchPerson( Long branchId, Long personId )
    {
        Branch branch = branchService.get( branchId );
        Person person = personService.get( personId );
        delete( getByBranchPerson( branch, person ).getId() );
        return branch.getCompany().getId();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.personinbranch;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.DOMAIN.entity.PersonInBranch;
import com.javasd.mwc.REPOSITORY.personinbranch.PersonInBranchRepository;
import com.javasd.mwc.SERVICE.branch.BranchService;
import com.javasd.mwc.SERVICE.person.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author almir
 */
@Service( "personInBranchService")
@Repository
@Transactional
public class PersonInBranchServiceImpl implements PersonInBranchService
{

    @Autowired
    private PersonService personService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private PersonInBranchRepository personInBranchRepository;
    
    @Override
    public PersonInBranch getByPerson( Person person )
    {
        return personInBranchRepository.getByPerson( person );
    }

    @Override
    public PersonInBranch update( Long personId, Long branchId, String position )
    {
        Person loadedPerson = personService.get( personId );
        Branch loadedBranch = branchService.get( branchId );
        PersonInBranch personInBranch = loadedPerson.getPersonInBranch();
        if ( personInBranch == null )
        {
            personInBranch = new PersonInBranch();
        }
        personInBranch.setPerson( loadedPerson );
        personInBranch.setBranch( loadedBranch );
        personInBranch.setPosition( position );
        return personInBranchRepository.update( personInBranch );
    }

    @Override
    public List<PersonInBranch> getPeopleInBranch( Branch branch )
    {
        return personInBranchRepository.getPeopleInBranch( branch );
    }

    @Override
    public PersonInBranch getByBranchPerson( Branch branch, Person person )
    {
        return personInBranchRepository.getByBranchPerson( branch, person );
    }

    @Override
    public Long deleteByBranchPerson( Long branchId, Long personId )
    {
        return personInBranchRepository.deleteByBranchPerson( branchId, personId );
    }

    
    
}

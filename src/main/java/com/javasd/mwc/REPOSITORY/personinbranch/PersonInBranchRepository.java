/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.personinbranch;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.DOMAIN.entity.PersonInBranch;
import com.javasd.mwc.REPOSITORY.generic.GenericRepository;
import java.util.List;

/**
 *
 * @author almir
 */
public interface PersonInBranchRepository extends GenericRepository<PersonInBranch>
{
    public PersonInBranch getByPerson( Person person );
    public List<PersonInBranch> getPeopleInBranch( Branch branch );
    public PersonInBranch getByBranchPerson( Branch branch, Person person );
    public Long deleteByBranchPerson( Long branchId, Long personId );
}

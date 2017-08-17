/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.personinbranch;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Person;
import com.javasd.mwc.DOMAIN.entity.PersonInBranch;
import java.util.List;

/**
 *
 * @author almir
 */
public interface PersonInBranchService
{

    public PersonInBranch getByPerson( Person person );

    public PersonInBranch update( Long personId, Long branchId, String position );

    public List<PersonInBranch> getPeopleInBranch( Branch branch );

    public PersonInBranch getByBranchPerson( Branch branch, Person person );

    public Long deleteByBranchPerson( Long branchId, Long personId );
}

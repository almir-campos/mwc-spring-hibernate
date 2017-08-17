/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.REPOSITORY.branch;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.REPOSITORY.generic.GenericRepository;
import java.util.List;

/**
 *
 * @author almir
 */
public interface BranchRepository extends GenericRepository<Branch>
{
    public List<Branch> getAllByAcronymForCompany( Company company );
    public List<Branch> getAllByCompanyAndAcronym();
    public Branch getWithPeople( Long id );
}

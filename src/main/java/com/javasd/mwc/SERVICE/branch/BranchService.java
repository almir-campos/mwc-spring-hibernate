/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.branch;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Company;
import java.util.List;

/**
 *
 * @author almir
 */
public interface BranchService
{
    public Branch get( Long id );
    public Branch getWithPeople( Long id );
    public List<Branch> getAllByAcronym( Company company );
    public List<Branch> getAllByCompanyAndAcronym();
    public Branch save( Branch branch );
    public Branch update( Branch branch );
    public void delete( Long id );
}

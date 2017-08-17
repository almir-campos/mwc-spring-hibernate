/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.SERVICE.branch;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.REPOSITORY.branch.BranchRepository;
import com.javasd.mwc.util.beans.ObjectMerger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author almir
 */
@Service ("branchService")
@Transactional
@Repository
public class BranchServiceImpl implements BranchService
{

    @Autowired
    private BranchRepository branchRepository;
    
    @Autowired
    private ObjectMerger objectMerger;
    
    @Override
    public Branch get( Long id )
    {
        return branchRepository.get( id );
    }

    @Override
    public Branch getWithPeople( Long id )
    {
        return branchRepository.getWithPeople( id );
    }
    
    @Override
    public List<Branch> getAllByAcronym( Company company )
    {
        return branchRepository.getAllByAcronymForCompany( company );
    }

    @Override
    public Branch save( Branch branch )
    {
        return branchRepository.save( branch );
    }

    @Override
    public Branch update( Branch branch )
    {
        Branch loadedBranch = get( branch.getId() );
        Branch mergedBranch = ( Branch ) objectMerger.mergeFields( branch, loadedBranch );
        return branchRepository.update( mergedBranch );
    }

    @Override
    public void delete( Long id )
    {
        branchRepository.delete( id );
    }

    @Override
    public List<Branch> getAllByCompanyAndAcronym()
    {
        return branchRepository.getAllByCompanyAndAcronym();
    }
} 

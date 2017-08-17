/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.WEB.CONTROLLER;

import com.javasd.mwc.DOMAIN.entity.Branch;
import com.javasd.mwc.DOMAIN.entity.Company;
import com.javasd.mwc.SERVICE.branch.BranchService;
import com.javasd.mwc.SERVICE.company.CompanyService;
import com.javasd.mwc.util.beans.ObjectFiller;
import com.javasd.mwc.util.beans.ObjectMerger;
import com.javasd.mwc.WEB.output.Branch01;
import com.javasd.mwc.WEB.output.Company01;
import com.javasd.mwc.annotations.MWC_Admin;
import com.javasd.mwc.annotations.MWC_LastScreen;
import com.javasd.mwc.annotations.MWC_Realm;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author almir
 */
@RequestMapping( value = "/company" )
@Controller
public class CompanyController
{

    private final Logger logger = Logger.getLogger( CompanyController.class );
    @Autowired
    private CompanyService companyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private ObjectFiller objectFiller;
    @Autowired
    private ObjectMerger objectMerger;
    //
    
    @MWC_Admin
    @MWC_LastScreen
    @RequestMapping( value = "/listcompanies", method = RequestMethod.GET )
    public String listCompanies(
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        uiModel.addAttribute( "companies", companyService.getAllByName() );
        return "company/list";
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // ADD
    //
    ////////////////////////////////////////////////////////////////////////////
    
//    /**
//     * It is better to not user this method.
//     * 
//     * This method puts an object in the Model to be used when adding a Company
//     * but it will also puts an empty Company object which is available to all
//     * views under /company. Then is preferable to put an empty Company object
//     * only when it is necessary.
//     * 
//     * @return 
//     */
//    @ModelAttribute( "company" )
//    public Company getCompanyObject()
//    {
//        return new Company();
//    }

    @MWC_Admin
    @MWC_LastScreen
    @RequestMapping( value = "/add", method = RequestMethod.GET )
    public String add(
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        // IF YOU ADD THIS OBJECT YOU DON'T NEED TO USE THE PREVIOUS METHOD
        uiModel.addAttribute( "company", new Company() );
        return "company/add";
    }

    @RequestMapping( value = "/add", method = RequestMethod.POST )
    public String add(
            @Valid @ModelAttribute( "company" ) Company company,
            BindingResult result,
            Model uiModel )
    {
        if ( result.hasErrors() )
        {
            uiModel.addAttribute( "errors", result );
            return "company/add";
        }
        companyService.save( company );
        return "redirect:/company/listcompanies";
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // SHOW
    //
    ////////////////////////////////////////////////////////////////////////////
    @MWC_Realm( value="company" )
    @MWC_LastScreen
    @RequestMapping( value = "/show/{companyId}", method = RequestMethod.GET )
    public String show(
            @PathVariable( "companyId" ) Long companyId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        Company company = companyService.get( companyId );
        uiModel.addAttribute( "company", ( Company01 ) objectFiller.fillFields( company, Company01.class ) );
        return "company/show";
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // EDIT
    //
    ////////////////////////////////////////////////////////////////////////////
    
    @MWC_Admin
    @MWC_LastScreen
    @RequestMapping( value = "/edit/{companyId}", method = RequestMethod.GET )
    public String edit(
            @PathVariable( "companyId" ) Long companyId,
            Model uiModel,
            HttpServletResponse httpServletResponse )
    {
        Company company = companyService.get( companyId );
        uiModel.addAttribute( "company", ( Company01 ) objectFiller.fillFields( company, Company01.class ) );
        return "company/edit";
    }

    @RequestMapping( value = "/edit", method = RequestMethod.POST )
    public String edit(
            @Valid Company company,
            BindingResult result,
            Model uiModel )
    {
        if ( result.hasErrors() )
        {
//            uiModel.addAttribute( "company", company );
            uiModel.addAttribute("currentId", company.getId() );
            uiModel.addAttribute( "errors", result.getAllErrors() );
            return "company/edit";
        }
        Company loadedCompany = companyService.get( company.getId() );
        Company mergedCompany = ( Company ) objectMerger.mergeFields( company, loadedCompany );
        Company updatedCompany = companyService.update( mergedCompany );
        uiModel.addAttribute( "company", ( Company01 ) objectFiller.fillFields( updatedCompany, Company01.class ) );
        return "redirect:/company/show/" + company.getId();
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // DELETE
    //
    ////////////////////////////////////////////////////////////////////////////
    
    @MWC_Admin
    @RequestMapping( value = "/delete/{companyId}", method = RequestMethod.GET )
    public String delete(
            @PathVariable( "companyId" ) Long companyId,
            HttpServletResponse httpServletResponse )
    {
        companyService.delete( companyId );
        return "redirect:/company/listcompanies";
    }

    @RequestMapping( value = "/listbranches/{companyId}", method = RequestMethod.GET )
    public String listBranches( @PathVariable( "companyId" ) Long companyId, Model uiModel )
    {
        Company loadedCompany = companyService.get( companyId );
        List<Branch> branches = branchService.getAllByAcronym( loadedCompany );
        List<Branch01> branches01 = new ArrayList<Branch01>();
        for ( Branch b : branches )
        {
            branches01.add( ( Branch01 ) objectFiller.fillFields( b, Branch01.class ) );
        }
//        DON'T USE THE TWO BELOW LINES - PREFER objectFiller INSTEAD
//        Object[] companyBasicData = companyService.getBasicData( companyId );
//        uiModel.addAttribute( "company", companyBasicData );
        uiModel.addAttribute( "company", objectFiller.fillFields( loadedCompany, Company01.class ) );
        uiModel.addAttribute( "branches", branches01 );
        return "branch/list";
    }
}

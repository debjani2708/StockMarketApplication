package com.iiht.StockMarket.services;

import java.util.List;

import com.iiht.StockMarket.dto.CompanyDetailsDTO;
import com.iiht.StockMarket.exception.InvalidCompanyException;

public interface CompanyInfoService {

	public CompanyDetailsDTO saveCompanyDetails(CompanyDetailsDTO companyDetailsDTO) throws InvalidCompanyException;
	public CompanyDetailsDTO deleteCompany(Long companyCode) throws InvalidCompanyException;
	//----------------------------------------------------------------------------
	public CompanyDetailsDTO getCompanyInfoById(Long companyCode);
	public List<CompanyDetailsDTO> getAllCompanies();
}
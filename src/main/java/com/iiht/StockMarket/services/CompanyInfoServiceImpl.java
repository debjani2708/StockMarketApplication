package com.iiht.StockMarket.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.StockMarket.dto.CompanyDetailsDTO;
import com.iiht.StockMarket.exception.InvalidCompanyException;
import com.iiht.StockMarket.model.CompanyDetails;
import com.iiht.StockMarket.repository.CompanyInfoRepository;
import com.iiht.StockMarket.utils.StockMarketUtility;

@Service
@Transactional
public class CompanyInfoServiceImpl implements CompanyInfoService {


   
    @Autowired
    private CompanyInfoRepository repository;

	public CompanyDetailsDTO saveCompanyDetails(CompanyDetailsDTO companyDetailsDTO) throws InvalidCompanyException {

        if (companyDetailsDTO!=null)
        {
            if (repository.existsById(companyDetailsDTO.getCompanyCode()))
            {
              throw new InvalidCompanyException("Company code already exists");
            }          
            
           companyDetailsDTO= StockMarketUtility.convertToCompanyDetailsDTO(repository.save(StockMarketUtility.convertToCompanyDetails(companyDetailsDTO)));
        }
		return companyDetailsDTO;
	};
	//----------------------------------------------------------------------------
	public CompanyDetailsDTO deleteCompany(Long companyCode) throws InvalidCompanyException{
		if (!repository.existsById(companyCode))
        {
          throw new InvalidCompanyException("Company code doesn't exists");
        }   
		CompanyDetailsDTO c=StockMarketUtility.convertToCompanyDetailsDTO(repository.findById(companyCode).orElse(null));
		repository.deleteByCompanyCode(companyCode);
		return c;
	};
	//----------------------------------------------------------------------------
	public CompanyDetailsDTO getCompanyInfoById(Long companyCode) {
       CompanyDetails c= repository.findById(companyCode).orElse(null);        
        return c!=null?StockMarketUtility.convertToCompanyDetailsDTO(c):null;
	};
	
	//----------------------------------------------------------------------------
	public List<CompanyDetailsDTO> getAllCompanies() {
		List<CompanyDetails> companydetails= repository.findAll();
		List<CompanyDetailsDTO> companydtodetails=companydetails.stream().map(c->StockMarketUtility.convertToCompanyDetailsDTO(c)).collect(Collectors.toList());
		return companydtodetails;
	}
}
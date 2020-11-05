package com.iiht.StockMarket.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.StockMarket.dto.StockPriceDetailsDTO;
import com.iiht.StockMarket.dto.StockPriceIndexDTO;
import com.iiht.StockMarket.exception.InvalidCompanyException;
import com.iiht.StockMarket.model.StockPriceDetails;
import com.iiht.StockMarket.repository.CompanyInfoRepository;
import com.iiht.StockMarket.repository.StockPriceRepository;
import com.iiht.StockMarket.utils.StockMarketUtility;

@Service
@Transactional
public class StockMarketServiceImpl implements StockMarketService {

	@Autowired
    private StockPriceRepository stockRepository;

	@Autowired
    private CompanyInfoRepository companyRepository;
	
	//----------------------------------------------------------------------------
	public StockPriceDetailsDTO saveStockPriceDetails(StockPriceDetailsDTO stockPriceDetailsDTO) {
		
		if (stockPriceDetailsDTO!=null) {
			 if (!companyRepository.existsById(stockPriceDetailsDTO.getCompanyCode()))
	            {
	              throw new InvalidCompanyException("Company code doesn't exists");
	            } 
			 stockPriceDetailsDTO= StockMarketUtility.convertToStockPriceDetailsDTO(stockRepository.save(StockMarketUtility.convertToStockPriceDetails(stockPriceDetailsDTO)));
		}
		return stockPriceDetailsDTO;
	}
	//----------------------------------------------------------------------------
	public List<StockPriceDetailsDTO> deleteStock(Long companyCode) {
		List<StockPriceDetails> stockPriceDetailsList= stockRepository.findStockByCompanyCode(companyCode);
		stockRepository.deleteStockByCompanyCode(companyCode);
		return StockMarketUtility.convertToStockPriceDetailsDtoList(stockPriceDetailsList);
		
	}
	//----------------------------------------------------------------------------
	public List<StockPriceDetailsDTO> getStockByCode(Long companyCode){
		List<StockPriceDetails> stockPriceDetailsList= stockRepository.findStockByCompanyCode(companyCode);
		return StockMarketUtility.convertToStockPriceDetailsDtoList(stockPriceDetailsList);
	};
	//----------------------------------------------------------------------------
	public StockPriceDetailsDTO getStockPriceDetailsDTO(StockPriceDetails stockDetails)	{
		return null;
	}	
	//----------------------------------------------------------------------------
	public Double getMaxStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		
		return stockRepository.findMaxStockPrice(companyCode, startDate, endDate);
	}
	public Double getAvgStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockRepository.findAvgStockPrice(companyCode, startDate, endDate);
	}
	public Double getMinStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockRepository.findMinStockPrice(companyCode, startDate, endDate);
	}
	
	public StockPriceIndexDTO getStockPriceIndex(Long companyCode, LocalDate startDate, LocalDate endDate) {
		StockPriceIndexDTO s=new StockPriceIndexDTO(); 
		s.setCompanyDto(StockMarketUtility.convertToCompanyDetailsDTO(companyRepository.findById(companyCode).orElse(null)));
		s.setStockPriceList(StockMarketUtility.convertToStockPriceDetailsDtoList(stockRepository.findStockByCompanyCodeInDateRange(companyCode, startDate, endDate)));
		s.setAvgStockPrice(this.getAvgStockPrice(companyCode, startDate, endDate));
		s.setMaxStockPrice(this.getMaxStockPrice(companyCode, startDate, endDate));
		s.setMinStockPrice(this.getMinStockPrice(companyCode, startDate, endDate));
		return s;
	}
}
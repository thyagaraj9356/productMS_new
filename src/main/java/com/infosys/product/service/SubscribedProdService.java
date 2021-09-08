package com.infosys.product.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.product.dto.SubscribedProductDTO;
import com.infosys.product.entity.SubscribedProd;
import com.infosys.product.exception.InfyException;
import com.infosys.product.repository.SubscribedProdRepository;

@Service
public class SubscribedProdService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SubscribedProdRepository subscribedprodrepo;
	
	public List<SubscribedProductDTO> getByBuyerid(@PathVariable String buyerid) throws InfyException{

		logger.info("Products for the buyer with id {}", buyerid);

		Iterable<SubscribedProd> sub = subscribedprodrepo.findByBuyerid(buyerid);
		List<SubscribedProductDTO> subscribedprodDTO = new ArrayList<SubscribedProductDTO>();

		sub.forEach(subs -> {
			subscribedprodDTO.add(SubscribedProductDTO.valueOf(subs));
		});
		if(subscribedprodDTO.isEmpty())
			throw new InfyException("EMPTY_BUYER_PRODUCTS");
		return subscribedprodDTO;
	}
	
	public List<SubscribedProductDTO> getAllSubProduct() throws InfyException{

		Iterable<SubscribedProd> subs = subscribedprodrepo.findAll();
		List<SubscribedProductDTO> subscribedprodDTOs = new ArrayList<>();

		subs.forEach(subpro -> {
			SubscribedProductDTO subscribedprodDTO = SubscribedProductDTO.valueOf(subpro);
			subscribedprodDTOs.add(subscribedprodDTO);
		});
		if(subscribedprodDTOs.isEmpty())
			throw new InfyException("NO_SUBSCRIBED_PRODUCTS");
		logger.info("Subscribed Product Details : {}", subscribedprodDTOs);
		return subscribedprodDTOs;
	}
	
}

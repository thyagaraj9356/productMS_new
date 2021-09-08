package com.infosys.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.product.entity.SubscribedProd;

	public interface SubscribedProdRepository extends JpaRepository<SubscribedProd, String>{
		List<SubscribedProd> findByBuyerid(String buyerid);
	}


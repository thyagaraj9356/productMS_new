package com.infosys.product.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.product.dto.ProductDTO;
import com.infosys.product.entity.Product;
import com.infosys.product.exception.InfyException;
import com.infosys.product.repository.ProductRepository;
import com.infosys.product.repository.SubscribedProdRepository;

@Service
public class ProductService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductRepository productrepo;

	@Autowired
	SubscribedProdRepository subscribedprodrepo;

	public List<ProductDTO> getProductByName(@PathVariable String productname) throws InfyException {

		logger.info("Product request with name {}", productname);

		Iterable<Product> product = productrepo.findByproductname(productname);
		List<ProductDTO> productDTO = new ArrayList<ProductDTO>();

		product.forEach(Pro -> {
			productDTO.add(ProductDTO.valueOf(Pro));
		});
		if (productDTO.isEmpty())
			throw new InfyException("PRODUCTS_NOT_FOUND");
		return productDTO;
	}
	public List<ProductDTO> getProductBycategory(@PathVariable String category) throws InfyException {
		logger.info("Product request of category {}", category);
		Iterable<Product> product = productrepo.findBycategory(category);
		List<ProductDTO> productDTO = new ArrayList<ProductDTO>();

		product.forEach(Pro -> {
			productDTO.add(ProductDTO.valueOf(Pro));
		});
		if (productDTO.isEmpty())
			throw new InfyException("PRODUCTS_NOT_FOUND");
		return productDTO;
	}

	public List<ProductDTO> getProductBySellerId(@PathVariable String sellerid) throws InfyException {
		logger.info("Product request of seller {}", sellerid);
		Iterable<Product> product = productrepo.findBysellerid(sellerid);
		List<ProductDTO> productDTO = new ArrayList<ProductDTO>();

		product.forEach(Pro -> {
			productDTO.add(ProductDTO.valueOf(Pro));
		});
		if (productDTO.isEmpty())
			throw new InfyException("PRODUCTS_NOT_FOUND");
		return productDTO;
	}

	public List<ProductDTO> getAllProduct() throws InfyException {

		Iterable<Product> products = productrepo.findAll();
		List<ProductDTO> productDTOs = new ArrayList<>();

		products.forEach(product -> {
			ProductDTO productDTO = ProductDTO.valueOf(product);
			productDTOs.add(productDTO);
		});
		if (productDTOs.isEmpty())
			throw new InfyException("PRODUCTS_NOT_FOUND");
		logger.info("Product Details : {}", productDTOs);
		return productDTOs;
	}

	public Product updateProductStock(Product product, String prodid) throws InfyException{
        Product existingProduct = productrepo.findById(prodid).orElse(null);
        if(existingProduct != null) {
            existingProduct.setStock(product.getStock());
            return productrepo.save(existingProduct); 
        }else {
        	throw new InfyException("PRODUCTS_NOT_FOUND");
        }
    
    }
	public ProductDTO getByProdid(String prodid) throws InfyException{
		logger.info("Profile request for customer {}", prodid);
		Product pro = productrepo.findByProdid(prodid);
		if(pro != null) {
			ProductDTO proDTO = ProductDTO.valueOf(pro);
			logger.info("Profile for customer : {}", proDTO);
			return proDTO;
		} else{
			throw new InfyException("PRODUCTS_NOT_FOUND");
		}
	}
	public void saveProduct(ProductDTO productDTO) throws InfyException {
		logger.info("Adding product with data {}", productDTO);
		Product product = productDTO.createProduct();
		productrepo.save(product);
	}


}

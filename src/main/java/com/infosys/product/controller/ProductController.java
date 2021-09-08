package com.infosys.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infosys.product.dto.ProductDTO;
import com.infosys.product.dto.SubscribedProductDTO;
import com.infosys.product.entity.Product;
import com.infosys.product.exception.InfyException;
import com.infosys.product.service.ProductService;
import com.infosys.product.service.SubscribedProdService;

@RestController
@CrossOrigin
@RequestMapping
public class ProductController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductService productservice;
	@Autowired
	SubscribedProdService subscribedProduct;
	@Autowired
	Environment environment;

	@RequestMapping(value = "/api/product/{productname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> getProductByName(@PathVariable String productname)
			throws InfyException {
		try {
			logger.info("product request with name {}", productname);
			List<ProductDTO> productDTO = productservice.getProductByName(productname);
			return new ResponseEntity<>(productDTO, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}

	}

	@RequestMapping(value = "/api/productcategory/{category}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> getProductBycategory(@PathVariable String category)
			throws InfyException {
		try {
			logger.info("product request with category {}", category);
			List<ProductDTO> productDTO = productservice.getProductBycategory(category);
			return new ResponseEntity<>(productDTO, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}
	
	@RequestMapping(value = "/api/products/{sellerid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> getProductBysellerid(@PathVariable String sellerid)
			throws InfyException {
		try {
			logger.info("product request with sellerid {}", sellerid);
			List<ProductDTO> productDTO = productservice.getProductBySellerId(sellerid);
			return new ResponseEntity<>(productDTO, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@GetMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> getAllProduct() throws InfyException {
		try {
			logger.info("Fetching all products");
			List<ProductDTO> buyerDTOs = productservice.getAllProduct();
			return new ResponseEntity<>(buyerDTOs, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@RequestMapping(value = "/api/product/{prodid}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProductStock(@RequestBody Product product, @PathVariable String prodid) throws InfyException{
        try {
        	Product products = productservice.updateProductStock(product, prodid);
			return new ResponseEntity<>(products, HttpStatus.OK);
        }catch(Exception exception) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
        }
    }
	
	@RequestMapping(value = "/api/subscribedProducts/{buyerid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SubscribedProductDTO>> getByBuyerid(@PathVariable String buyerid)
			throws InfyException {
		try {
			logger.info("Subscribed products for buyer {}", buyerid);
			List<SubscribedProductDTO> subscribed = subscribedProduct.getByBuyerid(buyerid);
			return new ResponseEntity<>(subscribed, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@GetMapping(value = "/api/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SubscribedProductDTO>> getAllSubProduct() throws InfyException {
		try {
			logger.info("Fetching all subscribed products");
			List<SubscribedProductDTO> subscribedDto = subscribedProduct.getAllSubProduct();
			return new ResponseEntity<>(subscribedDto, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@RequestMapping(value = "/api/verifyprod/{prodid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> getByProdid(@PathVariable String prodid) throws InfyException{
		try {
			logger.info("productname request for product {}", prodid);
			ProductDTO product = productservice.getByProdid(prodid);
			return new ResponseEntity<>(product,HttpStatus.OK);
		} catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
		
	}
	@PostMapping(value = "/api/product/addproduct", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createProduct(@Valid @RequestBody ProductDTO productDTO) throws InfyException {
		try {
			String successMessage = environment.getProperty("INSERT_SUCCESS");
			logger.info("Product to be added {}", productDTO);
			productservice.saveProduct(productDTO);
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

}

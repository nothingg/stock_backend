package com.ghb.stock_backend.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ghb.stock_backend.controller.request.ProductRequest;
import com.ghb.stock_backend.exception.ProductNotFoundException;
import com.ghb.stock_backend.exception.ValidationException;
import com.ghb.stock_backend.model.Product;
import com.ghb.stock_backend.service.StorageService;

@RestController
@RequestMapping("/product")
public class productController {

	private final AtomicLong counter = new AtomicLong();
	private List<Product> products = new ArrayList<>();
	private StorageService storageService;
	
	public productController(StorageService storageService) {
		this.storageService = storageService;
		
	}

	//
	@GetMapping()
	public List<Product> getProducts() {
		return products;
	}

	@GetMapping("/{id}")
	public Product getProduct(@PathVariable long id) {
		return products.stream()
				.filter(result -> result.getId() == id)
				.findFirst()
				.orElseThrow(() -> new ProductNotFoundException(id));
				
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping()
	public Product addProduct(@Valid ProductRequest productRequest, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ValidationException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
			});
		}
		String fileName = storageService.store(productRequest.getImage());
		Product data = new Product(counter.incrementAndGet(), 
				productRequest.getName(), 
				fileName, 
				productRequest.getPrice(),
				productRequest.getStock());
		products.add(data);

		return data;
	}
	
	@PutMapping("/{id}")
	public void editdProduct(@RequestBody Product product, @PathVariable long id) {
		
		 products.stream()
			.filter(result -> result.getId() == id)
			.findFirst()
			.ifPresentOrElse(result -> {
				result.setName(product.getName());
				result.setImage(product.getImage());
				result.setPrice(product.getPrice());
				result.setStock(product.getStock());
			}, () -> {
				//todo
				throw new ProductNotFoundException(id);
			});
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteProduct(@RequestBody Product product, @PathVariable long id) {

		products.stream()
			.filter(result -> result.getId() == id)
			.findFirst()
			.ifPresentOrElse(result -> products.remove(result), () -> {
					//todo
				throw new ProductNotFoundException(id);
				});
	}
	
	

//	@GetMapping({ "/say/{id}/name/{name}", "/sayname/{id}" })
//	public String getProductByName(@PathVariable(name = "id") long id, @PathVariable(required = false) String name) {
//		return name + " product id: " + id;
//	}
//
//	@GetMapping("/say/print")
//	public String getProductByNameQuery(
//			@RequestParam(name = "name", required = false, defaultValue = "cat") String name) {
//		return name;
//	}
}

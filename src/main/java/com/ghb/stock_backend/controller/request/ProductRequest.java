package com.ghb.stock_backend.controller.request;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

		@NotEmpty
		private String name;
		private MultipartFile image;
		private int price;
		private int stock;


}

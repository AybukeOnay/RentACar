package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.ColorService;
import com.kodlamaio.rentACar.business.requests.colors.CreateColorRequest;
import com.kodlamaio.rentACar.business.requests.colors.UpdateColorRequest;
import com.kodlamaio.rentACar.business.responses.colors.ColorResponse;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	private ColorService colorService;

	
	public ColorsController(ColorService colorService) {
		super();
		this.colorService = colorService;
	}
	
	@GetMapping("/getall")
	public List<ColorResponse> getAll()
	{
		return colorService.getAll();
	}
	
	
	@PostMapping("/add")
	public void add(@RequestBody CreateColorRequest createColorRequest)
	{
		colorService.add(createColorRequest);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id)
	{
		colorService.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable int id, @RequestBody UpdateColorRequest updateColorRequest)
	{
		colorService.update(updateColorRequest, id);
	}
	
}

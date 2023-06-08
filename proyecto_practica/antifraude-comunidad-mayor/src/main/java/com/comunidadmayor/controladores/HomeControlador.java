package com.comunidadmayor.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.comunidadmayor.modelo.Libro;
import com.comunidadmayor.repositorios.LibroRepositorio;

@Controller
@RequestMapping("")
public class HomeControlador {

	@Autowired
	private LibroRepositorio libroRepositorio;

	@GetMapping("/master")
	public ModelAndView verPaginaDeInicio() {
		return new ModelAndView("master");
	}
	
	@GetMapping("/libros")
	public ModelAndView libros() {
		List<Libro> ultimasLibros = libroRepositorio.findAll(PageRequest.of(0, 4, Sort.by("fechaEstreno").descending()))
				.toList();
		return new ModelAndView("index").addObject("ultimasLibros", ultimasLibros);
	}

	@GetMapping("")
	public ModelAndView listarLibros(
			@PageableDefault(sort = "fechaEstreno", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Libro> libros = libroRepositorio.findAll(pageable);
		return new ModelAndView("libros").addObject("libros", libros);
	}

	@GetMapping("/libros/{id}")
	public ModelAndView mostrarDetallesDeLibro(@PathVariable Integer id) {
		Libro libro = libroRepositorio.getOne(id);
		return new ModelAndView("libro").addObject("libro", libro);
	}
}

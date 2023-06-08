package com.comunidadmayor.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.comunidadmayor.modelo.Genero;
import com.comunidadmayor.modelo.Libro;
import com.comunidadmayor.repositorios.GeneroRepositorio;
import com.comunidadmayor.repositorios.LibroRepositorio;
import com.comunidadmayor.servicio.AlmacenServicioImpl;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

	@Autowired
	private LibroRepositorio libroRepositorio;

	@Autowired
	private GeneroRepositorio generoRepositorio;

	@Autowired
	private AlmacenServicioImpl servicio;

	@GetMapping("")
	public ModelAndView verPaginaDeInicio(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
		Page<Libro> libros = libroRepositorio.findAll(pageable);
		return new ModelAndView("admin/index").addObject("libros", libros);
	}

	@GetMapping("/libros/nuevo")
	public ModelAndView mostrarFormularioDeNuevaLibro() {
		List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
		return new ModelAndView("admin/nueva-libro").addObject("libro", new Libro()).addObject("generos", generos);
	}

	@PostMapping("/libros/nuevo")
	public ModelAndView registrarLibro(@Validated Libro libro, BindingResult bindingResult) {
		if (bindingResult != null && bindingResult.hasErrors() || libro.getPortada().isEmpty()) {
			if (libro.getPortada().isEmpty()) {
				bindingResult.rejectValue("portada", "MultipartNotEmpty");
			}

			List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
			return new ModelAndView("admin/nueva-libro").addObject("libro", libro).addObject("generos", generos);
		}

		String rutaPortada = servicio.almacenarArchivo(libro.getPortada());
		libro.setRutaPortada(rutaPortada);

		libroRepositorio.save(libro);
		return new ModelAndView("redirect:/admin");
	}

	@GetMapping("/libros/{id}/editar")
	public ModelAndView mostrarFormularioDeEditarLibro(@PathVariable Integer id) {
		Libro libro = libroRepositorio.getOne(id);
		List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));

		return new ModelAndView("admin/editar-libro").addObject("libro", libro).addObject("generos", generos);
	}

	@PostMapping("/libros/{id}/editar")
	public ModelAndView actualizarLibro(@PathVariable Integer id, @Validated Libro libro, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
			return new ModelAndView("admin/editar-libro").addObject("libro", libro).addObject("generos", generos);
		}

		Libro libroDB = libroRepositorio.getOne(id);
		libroDB.setTitulo(libro.getTitulo());
		libroDB.setSinopsis(libro.getSinopsis());
		libroDB.setFechaEstreno(libro.getFechaEstreno());
		libroDB.setYoutubeAudioId(libro.getYoutubeAudioId());
		libroDB.setGeneros(libro.getGeneros());

		if (!libro.getPortada().isEmpty()) {
			servicio.eliminarArchivo(libro.getPortada().getOriginalFilename());
			String rutaPortada = servicio.almacenarArchivo(libro.getPortada());
			libroDB.setRutaPortada(rutaPortada);
		}

		libroRepositorio.save(libroDB);
		return new ModelAndView("redirect:/admin");
	}

	@PostMapping("/libros/{id}/eliminar")
	public String eliminarLibro(@PathVariable Integer id) {
		Libro libro = libroRepositorio.getOne(id);
		libroRepositorio.delete(libro);
		servicio.eliminarArchivo(libro.getRutaPortada());

		return "redirect:/admin";
	}
}

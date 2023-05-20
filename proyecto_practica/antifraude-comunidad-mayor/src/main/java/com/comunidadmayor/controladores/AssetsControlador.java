package com.comunidadmayor.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comunidadmayor.servicio.AlmacenServicioImpl;

/**
 * @author <a href="mailto:usuario@mail.com">Maria Fernanda Rivas</a>
 * @project antifraude-comunidad-mayor
 * @class AssetsControlador
 * @description
 * @HU_CU_REQ
 * @date 18/05/2023
 */

@RestController
@RequestMapping("/assets")
public class AssetsControlador {

	@Autowired
	private AlmacenServicioImpl servicio;

	@GetMapping("/{filename:.+}")
	public Resource obtenerRecurso(@PathVariable("filename") String filename) {
		return servicio.cargarComoRecurso(filename);

	}

}

package co.edu.usco.springBoot_securityMemory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String home() {
		return "En esta pagina pueden entrar USER, CREATOR, EDITOR y ADMIN.";
	}

	@GetMapping("/new")
	public String nuevo() {
		return "Solo tiene permiso ADMIN y CREATOR.";
	}

	@GetMapping("/edit/{id}")
	public String editar(@org.springframework.web.bind.annotation.PathVariable int id) {
		return "Página para editar el registro " + id + ": solo ADMIN y EDITOR.";
	}

	@GetMapping("/delete{id}")
	public String eliminar(@org.springframework.web.bind.annotation.PathVariable int id) {
		return "Página para eliminar el registro " + id + ": solo ADMIN.";
	}

	@GetMapping("/403")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String accesoDenegado() {
		return "Error 403: no tienes permiso para entrar a esta página.";
	}
}
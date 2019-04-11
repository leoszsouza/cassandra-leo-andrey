package com.example.demo.controllers;

import com.example.demo.dto.NotaMySQLDTO;
import com.example.demo.services.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;



@Controller
public class NotaFiscalUiController {

	@Autowired
	NotaFiscalService notaFiscalService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getPost(Model model) {
		try {
			List<NotaMySQLDTO> notas = notaFiscalService.getNotasFiscaisMysql();

			model.addAttribute("listNotaFiscal", notas);

			return "/notafiscal/list";
		} catch (Exception e) {
			throw new RuntimeException("Falha ao consultar uma nota");
		}
	}	
	
}

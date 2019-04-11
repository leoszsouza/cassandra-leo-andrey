package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NotaDTO;
import com.example.demo.model.NotaFiscal;
import com.example.demo.repositories.NotaFiscalRepository;
import com.example.demo.services.NotaFiscalService;


@Controller
public class NotaFiscalMigracaoController {

	@Autowired
	NotaFiscalService notaFiscalService;
	
	@Autowired
	NotaFiscalRepository notaFiscalRepository;

//	@Autowired
//	PdfGenerator pdfGenerator;

	@RequestMapping(value = "/notafiscal/listcassandra", method = RequestMethod.GET)	
	public String popularDadosCassandra(Model model) {
		List<NotaFiscal> notas = notaFiscalService.migrateDataToCassandra();				
		
		model.addAttribute("listFromCassandra", notas);

		return "notafiscal/listcassandra";
	}
	
	

	@RequestMapping(value = "/notafiscal/cassandra/exportPDF/{number}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> exportNotafiscal(@PathVariable("number") Integer number) throws Exception {
		return null;
//		NotaFiscalDTO notaFiscalDTO = notaFiscalService.getByNumber(number);
//		
//		MediaType mediaType = MediaType.parseMediaType("application/pdf");
//		File file = pdfGenerator.createPdf("notafiscal/export", notaFiscalDTO, number.toString());
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
// 
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
//                .contentType(mediaType)
//                .contentLength(file.length())
//                .body(resource);
	}
	
	
}

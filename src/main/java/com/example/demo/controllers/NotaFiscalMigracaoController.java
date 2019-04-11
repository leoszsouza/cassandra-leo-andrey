package com.example.demo.controllers;

import com.example.demo.dto.NotaDTO;
import com.example.demo.dto.NotaItemDTO;
import com.example.demo.model.NotaFiscal;
import com.example.demo.pdf.PDFUtils;
import com.example.demo.repositories.NotaFiscalRepository;
import com.example.demo.services.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


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

		List<NotaFiscal> data = notaFiscalRepository.getByNumber(number);
		PDFUtils pdf = new PDFUtils();

		NotaDTO dto = new NotaDTO();
		List<NotaItemDTO> itens = new ArrayList<NotaItemDTO>();

		for(NotaFiscal nota : data){
			NotaItemDTO item = new NotaItemDTO();
			item.setDescricaoServico(nota.getService_description());
			item.setQuantidade(String.valueOf(nota.getQuantity()));
			item.setValorUnitario(nota.getUnit_value());
			item.setPorcentoTaxa(nota.getTax_percent());
			item.setPorcentoDesconto(nota.getDiscount_percent());
			item.setSubtotal(nota.getSubtotal());
			item.setRecurso(nota.getRecurso());
			item.setFuncao(nota.getFuncao());
			itens.add(item);
		}

		dto.setItens(itens);

		pdf.generate(dto);

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

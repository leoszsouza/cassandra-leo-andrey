package com.example.demo.pdf;

import com.example.demo.dto.NotaDTO;
import com.example.demo.dto.NotaItemDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PDFUtils {

    public static String DIRETORIO = "C:/temp/nota.pdf";
    private static Font FONTE = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Font SUB_FONTE = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    private static Font FONTE_COLUNA = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.LIGHT_GRAY);
    private static Font FONTE_CELULA = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL, BaseColor.GRAY);

    public void generate(NotaDTO nota){

        try{
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, new FileOutputStream(DIRETORIO));
            pdf.open();

            Paragraph topo = new Paragraph();
            topo.add(new Paragraph("NOTA FISCAL", FONTE));
            topo.add(new Paragraph("ITENS", SUB_FONTE));
            topo.add(new Paragraph(" "));
            pdf.add(topo);

            PdfPTable tabela = new PdfPTable(8);
            tabela.setWidthPercentage(100);
            tabela.setWidths(new int[]{20, 7, 7, 7, 7, 10, 25, 17});

            PdfPCell coluna = new PdfPCell(new Phrase("Descrição", FONTE_COLUNA));
            tabela.addCell(coluna).setBackgroundColor(BaseColor.DARK_GRAY);

            coluna = new PdfPCell(new Phrase("Qtd.", FONTE_COLUNA));
            tabela.addCell(coluna).setBackgroundColor(BaseColor.DARK_GRAY);

            coluna = new PdfPCell(new Phrase("R$ Un.", FONTE_COLUNA));
            tabela.addCell(coluna).setBackgroundColor(BaseColor.DARK_GRAY);

            coluna = new PdfPCell(new Phrase("Taxa", FONTE_COLUNA));
            tabela.addCell(coluna).setBackgroundColor(BaseColor.DARK_GRAY);

            coluna = new PdfPCell(new Phrase("Desc.", FONTE_COLUNA));
            tabela.addCell(coluna).setBackgroundColor(BaseColor.DARK_GRAY);

            coluna = new PdfPCell(new Phrase("R$ Subtot.", FONTE_COLUNA));
            tabela.addCell(coluna).setBackgroundColor(BaseColor.DARK_GRAY);

            coluna = new PdfPCell(new Phrase("Recurso", FONTE_COLUNA));
            tabela.addCell(coluna).setBackgroundColor(BaseColor.DARK_GRAY);

            coluna = new PdfPCell(new Phrase("Função", FONTE_COLUNA));
            tabela.addCell(coluna).setBackgroundColor(BaseColor.DARK_GRAY);

            tabela.setHeaderRows(1);

            List<NotaItemDTO> itens = nota.getItens();

            if(itens != null){
                for(NotaItemDTO item : itens) {
                    PdfPCell linha = new PdfPCell(new Phrase(item.getDescricaoServico(), FONTE_CELULA));
                    tabela.addCell(linha).setBackgroundColor(BaseColor.LIGHT_GRAY);

                    linha = new PdfPCell(new Phrase(item.getQuantidade(), FONTE_CELULA));
                    tabela.addCell(linha);

                    linha = new PdfPCell(new Phrase(String.valueOf(item.getValorUnitario()), FONTE_CELULA));
                    tabela.addCell(linha);

                    linha = new PdfPCell(new Phrase(item.getPorcentoTaxa()+"%", FONTE_CELULA));
                    tabela.addCell(linha);

                    linha = new PdfPCell(new Phrase(item.getPorcentoDesconto()+"%", FONTE_CELULA));
                    tabela.addCell(linha);

                    linha = new PdfPCell(new Phrase("R$"+item.getSubtotal(), FONTE_CELULA));
                    tabela.addCell(linha);

                    linha = new PdfPCell(new Phrase(item.getRecurso(), FONTE_CELULA));
                    tabela.addCell(linha).setBackgroundColor(BaseColor.LIGHT_GRAY);

                    linha = new PdfPCell(new Phrase(item.getFuncao(), FONTE_CELULA));
                    tabela.addCell(linha);
                }

            }

            Paragraph rodape = new Paragraph();
            rodape.add(new Paragraph(" "));
            rodape.add(new Paragraph("Total: R$"+nota.getTotal(), SUB_FONTE));

            pdf.add(tabela);
            pdf.add(rodape);

            pdf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException o) {
            o.printStackTrace();
        }

    }

}

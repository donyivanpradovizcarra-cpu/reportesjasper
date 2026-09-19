package com.ejemplo.reportes.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.reportes.model.Producto;
import com.ejemplo.reportes.service.ProductoService;
import com.ejemplo.reportes.service.ReporteService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ProductoService productoService;
    private final ReporteService reporteService;

    public ReporteController(
            ProductoService productoService,
            ReporteService reporteService) {

        this.productoService = productoService;
        this.reporteService = reporteService;
    }

    // ==============================
    // REPORTE PDF
    // ==============================

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> descargarPdf() throws Exception {

        List<Producto> productos =
                productoService.listarProductos();

        JasperPrint jasperPrint =
                reporteService.generarReporte(productos);

        byte[] archivo =
                JasperExportManager.exportReportToPdf(jasperPrint);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Reporte_Productos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(archivo);
    }

    // ==============================
    // REPORTE WORD
    // ==============================

    @GetMapping("/word")
    public ResponseEntity<byte[]> descargarWord() throws Exception {

        List<Producto> productos =
                productoService.listarProductos();

        JasperPrint jasperPrint =
                reporteService.generarReporte(productos);

        ByteArrayOutputStream salida =
                new ByteArrayOutputStream();

        JRDocxExporter exporter =
                new JRDocxExporter();

        exporter.setExporterInput(
                new SimpleExporterInput(jasperPrint));

        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(salida));

        exporter.exportReport();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Reporte_Productos.docx")
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                .body(salida.toByteArray());
    }

    // ==============================
    // REPORTE EXCEL
    // ==============================

    @GetMapping("/excel")
    public ResponseEntity<byte[]> descargarExcel() throws Exception {

        List<Producto> productos =
                productoService.listarProductos();

        JasperPrint jasperPrint =
                reporteService.generarReporte(productos);

        ByteArrayOutputStream salida =
                new ByteArrayOutputStream();

        JRXlsxExporter exporter =
                new JRXlsxExporter();

        exporter.setExporterInput(
                new SimpleExporterInput(jasperPrint));

        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(salida));

        SimpleXlsxReportConfiguration configuracion =
                new SimpleXlsxReportConfiguration();

        configuracion.setDetectCellType(true);
        configuracion.setCollapseRowSpan(false);

        exporter.setConfiguration(configuracion);

        exporter.exportReport();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Reporte_Productos.xlsx")
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(salida.toByteArray());
    }
}
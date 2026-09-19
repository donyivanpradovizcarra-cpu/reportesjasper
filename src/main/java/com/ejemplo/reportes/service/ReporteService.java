package com.ejemplo.reportes.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ejemplo.reportes.model.Producto;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReporteService {

    public JasperPrint generarReporte(List<Producto> productos) throws Exception {

        InputStream archivo = getClass()
                .getResourceAsStream("/reportes/productos.jrxml");

        if (archivo == null) {
            throw new RuntimeException(
                    "No se encontro el archivo productos.jrxml");
        }

        JasperReport reporte =
                JasperCompileManager.compileReport(archivo);

        JRBeanCollectionDataSource datos =
                new JRBeanCollectionDataSource(productos);

        Map<String, Object> parametros = new HashMap<>();

        parametros.put(
                "TITULO",
                "REPORTE GENERAL DE PRODUCTOS"
        );

        return JasperFillManager.fillReport(
                reporte,
                parametros,
                datos
        );
    }
}
package com.ejemplo.reportes.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ejemplo.reportes.model.Producto;

@Service
public class ProductoService {

    public List<Producto> listarProductos() {

        return Arrays.asList(
            new Producto(1, "Laptop Lenovo", "Tecnologia", 2500.00, 10),
            new Producto(2, "Mouse Logitech", "Accesorios", 80.00, 25),
            new Producto(3, "Teclado Redragon", "Accesorios", 150.00, 15),
            new Producto(4, "Monitor Samsung", "Tecnologia", 850.00, 8),
            new Producto(5, "Impresora Epson", "Impresion", 750.00, 6),
            new Producto(6, "Memoria USB 64GB", "Almacenamiento", 45.00, 30)
        );
    }
}
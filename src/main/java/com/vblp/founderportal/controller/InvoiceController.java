package com.vblp.founderportal.controller;

import com.vblp.founderportal.DTO.InvoiceDTO;
import com.vblp.founderportal.service.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin("http://localhost:8080")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // ================= 1)CREATE =================
    @PostMapping
    public String createinvoice(@RequestBody InvoiceDTO dto) {
        return invoiceService.createInvoice(dto);
    }

    // ================= 2)GET ALL =================
    @GetMapping
    public List<InvoiceDTO> getAllinvoices() {
        return invoiceService.getAllInvoices();
    }

    // ================= 3)GET BY ID =================
    @GetMapping("/{id}")
    public InvoiceDTO getinvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    // =================4) UPDATE =================
    @PutMapping("/{id}")
    public String updateInvoice(@PathVariable Long id,
                                @RequestBody InvoiceDTO dto) {
        return invoiceService.updateInvoice(id, dto);
    }

    // =================5) DELETE =================
    @DeleteMapping("/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        return invoiceService.deleteInvoice(id);
    }

    // =================6) FILTER BY STATUS =================
    @GetMapping("/status/{status}")
    public List<InvoiceDTO> getInvoicesByStatus(@PathVariable String status) {
        return invoiceService.getInvoicesByStatus(status);
    }

    // =================7) UPDATE STATUS ONLY =================
    @PutMapping("/{id}/status/{status}")
    public String updateInvoiceStatus(@PathVariable Long id,
                                      @PathVariable String status) {
        return invoiceService.updateInvoiceStatus(id, status);
    }
}
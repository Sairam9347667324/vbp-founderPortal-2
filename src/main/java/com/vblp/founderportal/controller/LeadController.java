package com.vblp.founderportal.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vblp.founderportal.DTO.LeadAuditDTO;
import com.vblp.founderportal.DTO.PendingLeadDTO;
import com.vblp.founderportal.Repository.LeadRepository;
import com.vblp.founderportal.entity.Lead;
import com.vblp.founderportal.entity.Lead.EnquirySource;
import com.vblp.founderportal.entity.Lead.LeadStatus;
import com.vblp.founderportal.entity.Lead.ServiceType;
import com.vblp.founderportal.service.LeadService;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin("http://localhost:8080")
public class LeadController {

    @Autowired
    private LeadRepository leadRepository;
    
    private final LeadService service;

    public LeadController(LeadService service) {
        this.service = service;
    }

    // 1️⃣ Create Lead
    @PostMapping("/save")
    public ResponseEntity<Lead> createLead(@RequestBody Lead lead) {
    	Lead saved =  leadRepository.save(lead);
        
        return ResponseEntity.ok(saved);
    }

    // 2️⃣ Get All Leads
    @GetMapping("/getAll")
    public ResponseEntity<List<Lead>> getAllLeads() {
        return ResponseEntity.ok(leadRepository.findAll());
    }

    // 3️⃣ Get Lead By ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLeadById(@PathVariable Long id) {

        Optional<Lead> optionalLead = leadRepository.findById(id);

        if (optionalLead.isPresent()) {
            return ResponseEntity.ok(optionalLead.get());
        } else {
            return ResponseEntity.status(404)
                    .body("Lead not found with id: " + id);
        }
    }

    // 4️⃣ Update Lead
    @PutMapping("/{id}")
    public ResponseEntity<String> updateLead(@PathVariable Long id,
            @RequestBody Lead updatedLead) {

        Optional<Lead> optionalLead = leadRepository.findById(id);

        if (optionalLead.isPresent()) {

            Lead existingLead = optionalLead.get();

            existingLead.setFullName(updatedLead.getFullName());
            existingLead.setBusinessName(updatedLead.getBusinessName());
            existingLead.setLocation(updatedLead.getLocation());
            existingLead.setPhoneNumber(updatedLead.getPhoneNumber());
            existingLead.setEnquirySource(updatedLead.getEnquirySource());
            existingLead.setServiceType(updatedLead.getServiceType());
            existingLead.setStatus(updatedLead.getStatus());
            existingLead.setProjectDate(updatedLead.getProjectDate());

            leadRepository.save(existingLead);

            return ResponseEntity.ok("All details updated successfully");

        } else {
            return ResponseEntity.badRequest()
                    .body("Lead not found with id: " + id);
        }
    }

    // 5️⃣ Delete Lead
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLead(@PathVariable Long id) {

        if (leadRepository.existsById(id)) {
            leadRepository.deleteById(id);
            return ResponseEntity.ok("Lead deleted successfully");
        } else {
            return ResponseEntity.badRequest()
                    .body("Lead not found with id: " + id);
        }
    }

    // 6️⃣ Filter by Project Date
    @GetMapping("/search/date")
    public ResponseEntity<?> getByProjectDate(@RequestParam LocalDate projectDate) {
        List<Lead> leads = leadRepository.findByProjectDate(projectDate);

        if (leads.isEmpty()) {
            // Return a friendly message when no data is found
            Map<String, String> response = new HashMap<>();
            response.put("message", "No leads and projects on this date");
            return ResponseEntity.status(404).body(response);
        }

        // Return the list of leads if found
        return ResponseEntity.ok(leads);
    }

    // 7️⃣ Search by Name
    @GetMapping("/search/name")
    public ResponseEntity<?> getByFullName(@RequestParam String name) {
        List<Lead> leads = leadRepository.findByFullNameContainingIgnoreCase(name);

        if (leads.isEmpty()) {
            // Return a friendly message when no data is found
            Map<String, String> response = new HashMap<>();
            response.put("message", "No leads on this name");
            return ResponseEntity.status(404).body(response);
        }

        // Return the list of leads if found
        return ResponseEntity.ok(leads);
    }

    // 8️⃣ Filter by Status
    @GetMapping("/search/status")
    public ResponseEntity<?> getByStatus(@RequestParam LeadStatus status) {
        List<Lead> leads = leadRepository.findByStatus(status);

        if (leads.isEmpty()) {
            // Return a friendly message when no data is found
            Map<String, String> response = new HashMap<>();
            response.put("message", "No status matched for any lead");
            return ResponseEntity.status(404).body(response);
        }

        // Return the list of leads if found
        return ResponseEntity.ok(leads);
    }

    // 9️⃣ Filter by Service Type
    @GetMapping("/search/service")
    public ResponseEntity<?> getByServiceType(@RequestParam ServiceType serviceType) {
        List<Lead> leads = leadRepository.findByServiceType(serviceType);

        if (leads.isEmpty()) {
            // Return a friendly message when no data is found
            Map<String, String> response = new HashMap<>();
            response.put("message", "No Service Type matched for any lead");
            return ResponseEntity.status(404).body(response);
        }

        // Return the list of leads if found
        return ResponseEntity.ok(leads);
    }

    // 🔟 Filter by Enquiry Source
    @GetMapping("/search/source")
    public ResponseEntity<?> getByEnquirySource(@RequestParam EnquirySource enquirySource) {
        List<Lead> leads = leadRepository.findByEnquirySource(enquirySource);

        if (leads.isEmpty()) {
            // Return a friendly message when no data is found
            Map<String, String> response = new HashMap<>();
            response.put("message", "No EnquirySource matched for any lead");
            return ResponseEntity.status(404).body(response);
        }

        // Return the list of leads if found
        return ResponseEntity.ok(leads);
    }

    // 1️⃣1️⃣ Popup for New Leads
    @GetMapping("/newlead")
    public ResponseEntity<?> getPendingLeads() {
        List<PendingLeadDTO> pendingLeads = leadRepository.findNewLeads(LeadStatus.Newlead);

        if (pendingLeads.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No pending leads found");
            return ResponseEntity.status(404).body(response);
        }

        return ResponseEntity.ok(pendingLeads);
    }

    // 1️⃣2️⃣ Audit by Date
    @GetMapping("/audit/date")
    public ResponseEntity<LeadAuditDTO> getDailyAuditByDate(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Optional<LeadAuditDTO> optionalAudit =
                leadRepository.findLeadAuditByDate(
                        date,
                        Lead.LeadStatus.Newlead,
                        Lead.LeadStatus.Contacted,
                        Lead.LeadStatus.Accepted,
                        Lead.LeadStatus.ProposalSent,
                        Lead.LeadStatus.ClosedWon,
                        Lead.LeadStatus.ClosedLost
                );

        LeadAuditDTO audit = optionalAudit.orElse(
                new LeadAuditDTO(
                        date,
                        0L,
                        0L,
                        0L,
                        0L,
                        0L,
                        0L,
                        0L
                )
        );

        return ResponseEntity.ok(audit);
    }
    
    // Excel Upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(service.uploadExcel(file));
    }
    
    
}
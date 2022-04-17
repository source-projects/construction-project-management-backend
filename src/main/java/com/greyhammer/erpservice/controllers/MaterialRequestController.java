package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.commands.CreateMaterialRequestCommand;
import com.greyhammer.erpservice.exceptions.MaterialRequestNotFoundException;
import com.greyhammer.erpservice.exceptions.NoPermissionException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.MaterialRequest;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.responses.PageResponse;
import com.greyhammer.erpservice.services.MaterialRequestService;
import com.greyhammer.erpservice.views.MaterialRequestView;
import com.greyhammer.erpservice.views.ProjectView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
public class MaterialRequestController {
    private final MaterialRequestService materialRequestService;
    public MaterialRequestController(MaterialRequestService materialRequestService) {
        this.materialRequestService = materialRequestService;
    }

    @JsonView(MaterialRequestView.ListView.class)
    @RequestMapping("/api/material-requests")
    public ResponseEntity<PageResponse<MaterialRequest>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String desc
    ) {
        Sort.Direction dir = desc != null && desc.equals("true") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = sortBy == null
                ? PageRequest.of(page, size)
                : PageRequest.of(page, size, Sort.by(dir, sortBy));

        Set<MaterialRequest> requests = materialRequestService.getAll(pageable);
        PageResponse<MaterialRequest> response = new PageResponse<>();
        response.setResults(requests);
        response.setCount(requests.size());
        response.setPage(page);
        response.setTotal(materialRequestService.getTotalMaterialRequestCount());

        return ResponseEntity.ok(response);
    }


    @RequestMapping("/api/material-requests/{id}")
    @JsonView(MaterialRequestView.FullView.class)
    public ResponseEntity<Object> get(@PathVariable Long id) {
        try {
            MaterialRequest request = materialRequestService.get(id);
            return ResponseEntity.ok(request);
        } catch (MaterialRequestNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @RequestMapping(value = "/api/material-requests/{id}/approve", method = RequestMethod.PUT)
    @JsonView(MaterialRequestView.FullView.class)
    public ResponseEntity<Object> approve(@PathVariable Long id) {
        try {
            MaterialRequest request = materialRequestService.approve(id);
            return ResponseEntity.ok(request);
        } catch (NoPermissionException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "No permission to approve this request.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (MaterialRequestNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @RequestMapping(value = "/api/material-requests/{id}/reject", method = RequestMethod.PUT)
    @JsonView(MaterialRequestView.FullView.class)
    public ResponseEntity<Object> reject(@PathVariable Long id) {
        try {
            MaterialRequest request = materialRequestService.reject(id);
            return ResponseEntity.ok(request);
        } catch (NoPermissionException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "No permission to reject this request.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (MaterialRequestNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @RequestMapping(value = "/api/material-requests/{id}/final-approve", method = RequestMethod.PUT)
    @JsonView(MaterialRequestView.FullView.class)
    public ResponseEntity<Object> finalApprove(@PathVariable Long id) {
        try {
            MaterialRequest request = materialRequestService.finalApprove(id);
            return ResponseEntity.ok(request);
        } catch (NoPermissionException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "No permission to approve this request.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (MaterialRequestNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @RequestMapping(value = "/api/material-requests/{id}/final-reject", method = RequestMethod.PUT)
    @JsonView(MaterialRequestView.FullView.class)
    public ResponseEntity<Object> finalReject(@PathVariable Long id) {
        try {
            MaterialRequest request = materialRequestService.finalReject(id);
            return ResponseEntity.ok(request);
        } catch (NoPermissionException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "No permission to reject this request.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (MaterialRequestNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @JsonView(MaterialRequestView.FullView.class)
    @RequestMapping(value = "/api/material-requests", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody CreateMaterialRequestCommand command) {
        try {
            MaterialRequest request = materialRequestService.handleCreateMaterialRequestCommand(command);
            return ResponseEntity.ok(request);
        } catch (ProjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}

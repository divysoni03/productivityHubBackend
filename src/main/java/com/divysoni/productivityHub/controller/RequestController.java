package com.divysoni.productivityHub.controller;

import com.divysoni.productivityHub.dto.misc.CustomResponse;
import com.divysoni.productivityHub.dto.misc.RequestDto;
import com.divysoni.productivityHub.dto.misc.ResponseBuilder;
import com.divysoni.productivityHub.mappers.impl.RequestMapperImpl;
import com.divysoni.productivityHub.services.impl.RequestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestServiceImpl requestService;
    private final RequestMapperImpl requestMapper;

    public RequestController(RequestServiceImpl requestService, RequestMapperImpl requestMapper) {
        this.requestService = requestService;
        this.requestMapper = requestMapper;
    }

    @PostMapping("/{task-list-id}/search")
    public ResponseEntity<CustomResponse<RequestDto>> createRequest(@PathVariable("task-list-id") ObjectId taskListId, @RequestParam String userName) {
        try {
            return ResponseBuilder.created("Request sent successfully!", requestMapper.toDto(requestService.makeNewRequest(taskListId, userName)));
        } catch(Exception e) {
            log.error("Failed to send Request!", e);
            return ResponseBuilder.failure(HttpStatus.NOT_FOUND,"Failed to send Request!");
        }
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<RequestDto>>> getAllRequests() {
        try {
            return ResponseBuilder.success("Request Fetched Successfully!", requestService.getAllRequest().stream().map(requestMapper::toDto).toList());
        } catch (Exception e) {
            log.error("failed to fetch Requests!", e);
            return ResponseBuilder.failure(HttpStatus.NO_CONTENT,"Failed to fetch Requests!");
        }
    }

    @DeleteMapping("/{request-id}")
    public ResponseEntity<CustomResponse<Object>> deleteRequest(@PathVariable("request-id") ObjectId requestId) {
        try {
            requestService.deleteById(requestId);
            return ResponseBuilder.success("Request removed successfully!", null);
        } catch (Exception e) {
            log.error("failed to remove the request!");
            return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, "failed to remove the request!");
        }
    }

    @GetMapping("/{request-id}")
    public ResponseEntity<CustomResponse<Object>> acceptRequest(@PathVariable("request-id") ObjectId requestId) {
        try {
            requestService.acceptRequest(requestId);
            return ResponseBuilder.success("Request accepted successfully!", null);
        } catch (Exception e) {
            log.error("Failed to accept the Request!");
            return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, "Failed to accept the Request!");
        }
    }
}

package com.nextstack.airetail.audit.service.impl;

import com.nextstack.airetail.audit.dto.request.AuditLogPageRequest;
import com.nextstack.airetail.audit.dto.response.AuditLogResponse;
import com.nextstack.airetail.audit.entity.AuditLog;
import com.nextstack.airetail.audit.mapper.AuditLogMapper;
import com.nextstack.airetail.audit.repository.AuditLogRepository;
import com.nextstack.airetail.audit.service.AuditService;
import com.nextstack.airetail.audit.specification.AuditLogSpecifications;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link AuditService}.
 */
@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    /**
     * Creates the service with required dependencies.
     */
    public AuditServiceImpl(AuditLogRepository auditLogRepository, AuditLogMapper auditLogMapper) {
        this.auditLogRepository = auditLogRepository;
        this.auditLogMapper = auditLogMapper;
    }

    @Override
    public void logAction(String action, String entity, Long entityId, String details, String ipAddress) {
        Long userId = SecurityUtils.getCurrentUser() != null ? SecurityUtils.getCurrentUser().getId() : null;
        AuditLog auditLog = AuditLog.builder()
                .action(action)
                .entity(entity)
                .entityId(entityId)
                .userId(userId)
                .details(details)
                .ipAddress(ipAddress)
                .build();
        auditLog.setActive(true);
        auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AuditLogResponse> getAll(AuditLogPageRequest pageRequest) {
        Page<AuditLog> page = auditLogRepository.findAll(
                AuditLogSpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, auditLogMapper::toResponse);
    }
}

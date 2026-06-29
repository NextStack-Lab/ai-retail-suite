package com.nextstack.airetail.payment.service.impl;

import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.payment.dto.request.PaymentPageRequest;
import com.nextstack.airetail.payment.dto.request.PaymentRequest;
import com.nextstack.airetail.payment.dto.response.PaymentResponse;
import com.nextstack.airetail.payment.entity.Payment;
import com.nextstack.airetail.payment.entity.PaymentStatus;
import com.nextstack.airetail.payment.mapper.PaymentMapper;
import com.nextstack.airetail.payment.repository.PaymentRepository;
import com.nextstack.airetail.payment.service.PaymentService;
import com.nextstack.airetail.payment.specification.PaymentSpecifications;
import com.nextstack.airetail.payment.validator.PaymentValidator;
import com.nextstack.airetail.sales.entity.Sale;
import com.nextstack.airetail.sales.repository.SaleRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link PaymentService}.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final SaleRepository saleRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentValidator paymentValidator;

    /**
     * Creates the service with required dependencies.
     */
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              SaleRepository saleRepository,
                              PaymentMapper paymentMapper,
                              PaymentValidator paymentValidator) {
        this.paymentRepository = paymentRepository;
        this.saleRepository = saleRepository;
        this.paymentMapper = paymentMapper;
        this.paymentValidator = paymentValidator;
    }

    @Override
    public PaymentResponse create(PaymentRequest request) {
        paymentValidator.validateCreate(request);
        Payment payment = paymentMapper.toEntity(request);
        payment.setSale(findSale(request.getSaleId()));
        if (payment.getStatus() == null) {
            payment.setStatus(PaymentStatus.COMPLETED);
        }
        payment.setActive(true);
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getById(Long id) {
        return paymentMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PaymentResponse> getAll(PageRequestDto pageRequest) {
        PaymentPageRequest request = new PaymentPageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PaymentResponse> getAll(PaymentPageRequest pageRequest) {
        Page<Payment> page = paymentRepository.findAll(
                PaymentSpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, paymentMapper::toResponse);
    }

    @Override
    public PaymentResponse update(Long id, PaymentRequest request) {
        Payment payment = findActiveById(id);
        paymentValidator.validateUpdate(id, request);
        paymentMapper.updateEntity(request, payment);
        payment.setSale(findSale(request.getSaleId()));
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    public void delete(Long id) {
        Payment payment = findActiveById(id);
        payment.setActive(false);
        paymentRepository.save(payment);
    }

    private Payment findActiveById(Long id) {
        return paymentRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
    }

    private Sale findSale(Long saleId) {
        return saleRepository.findByIdAndActiveTrue(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", saleId));
    }
}

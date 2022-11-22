package com.em.apiproductschallenge.client;

import com.em.apiproductschallenge.exceptions.RestException;
import com.em.apiproductschallenge.model.ProductDetail;
import com.em.apiproductschallenge.service.MockClientService;
import com.em.apiproductschallenge.utils.Messages;
import com.em.apiproductschallenge.utils.errors.ErrorDecoder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for mock client
 */
@Service
@Slf4j
public class MockClientImpl implements MockClientService {
    private static final String API_MOCK_CLIENT = "API MOCK CLIENT";
    @Autowired
    private MockRestClient mockRestClient;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Get product by id
     *
     * @param productId
     * @return Product detail
     */
    @Override
    public ProductDetail getProductById(String productId) {
        log.debug("[getProductById] --- The search of the product with id " + productId + " start ");
        ProductDetail productDetail;
        try {
            Response response = mockRestClient.getProductById2(productId);
            if (response.status() > 299) {
                ErrorDecoder.decode(response, API_MOCK_CLIENT);
            }
            productDetail = objectMapper.readValue(response.body().asInputStream(), ProductDetail.class);
        } catch (IOException e) {
            log.error(Messages.ERROR_CONVERSION, e.getMessage());
            throw new RestException(String.format(Messages.ERROR_COMUNICATION, API_MOCK_CLIENT));
        } catch (FeignException fe) {
            log.error(fe.getMessage());
            throw new RestException(String.format(Messages.ERROR_COMUNICATION, API_MOCK_CLIENT));
        }

        return productDetail;
    }

    /**
     * Get similar products id
     *
     * @param productId
     * @return List of product ids
     */
    @Override
    public List<ProductDetail> getSimilarProducts(String productId) {
        log.debug("[getSimilarProducts] --- The search of similar products for " + productId + " start ");
        List<String> similarIds;

        try {
            Response response = mockRestClient.getSimilarProductIds(productId);
            if (response.status() > 299) {
                ErrorDecoder.decode(response, API_MOCK_CLIENT);
            }
            similarIds = objectMapper.readValue(response.body().asInputStream(), new TypeReference<List<String>>() {
            });
        } catch (IOException e) {
            log.error(Messages.ERROR_CONVERSION, e.getMessage());
            throw new RestException(String.format(Messages.ERROR_COMUNICATION, API_MOCK_CLIENT));
        } catch (FeignException fe) {
            log.error(fe.getMessage());
            throw new RestException(String.format(Messages.ERROR_COMUNICATION, API_MOCK_CLIENT));
        }
        log.info(String.valueOf(similarIds));

        return similarIds.stream().map(this::getProductById).collect(Collectors.toList());
    }
}

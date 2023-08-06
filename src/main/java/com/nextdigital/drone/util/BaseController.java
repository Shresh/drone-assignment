package com.nextdigital.drone.util;

import com.nextdigital.drone.config.Messages;
import com.nextdigital.drone.exception.InvalidRequestBodyException;
import com.nextdigital.drone.pojo.GlobalApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class BaseController {

    /**
     * Global API Response Instance
     */
    @Autowired
    protected GlobalApiResponse globalApiResponse;

    /**
     * API Success Status
     */
    protected final boolean API_SUCCESS_STATUS = true;

    /**
     * API Error Status
     */
    protected final boolean API_ERROR_STATUS = false;

    /**
     * Message Source Instance
     */
    @Autowired
    protected Messages customMessageSource;

    /**
     * Module Name
     */
    protected String permissionName;

    /**
     * Function that sends successful API Response
     *
     * @param message
     * @param data
     * @return
     */
    protected GlobalApiResponse successResponse(String message, Object data) {
        globalApiResponse.setStatus(API_SUCCESS_STATUS);
        globalApiResponse.setMessage(message);
        globalApiResponse.setData(data);
        return globalApiResponse;
    }

    protected GlobalApiResponse successResponse(String message) {
        globalApiResponse.setStatus(API_SUCCESS_STATUS);
        globalApiResponse.setMessage(message);
        globalApiResponse.setData(null);
        return globalApiResponse;
    }

    /**
     * Function that sends error API Response
     *
     * @param message
     * @param errors
     * @return
     */
    protected GlobalApiResponse errorResponse(String message, Object errors) {
        globalApiResponse.setStatus(API_ERROR_STATUS);
        globalApiResponse.setMessage(message);
        globalApiResponse.setData(errors);
        return globalApiResponse;
    }

    public String getPermissionName() {
        return permissionName;
    }

    protected void validateRequestBody(BindingResult bindingResults) throws InvalidRequestBodyException {
        if (bindingResults.hasErrors()) {
            List<FieldError> errors = bindingResults.getFieldErrors();
            StringBuilder error_definition = new StringBuilder();
            for (FieldError error : errors) {
                error_definition.append(customMessageSource.get(error.getDefaultMessage(), error.getField())).append(",");
            }
            throw new InvalidRequestBodyException(error_definition.toString());
        }
    }
}

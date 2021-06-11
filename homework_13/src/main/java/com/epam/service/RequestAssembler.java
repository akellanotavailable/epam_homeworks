package com.epam.service;

import com.epam.dto.RequestDto;
import com.epam.entity.Request;

public interface RequestAssembler {
    RequestDto assemble(Request request);

    Request assemble(RequestDto requestDto);
}

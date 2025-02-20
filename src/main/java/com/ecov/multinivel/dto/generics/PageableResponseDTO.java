package com.ecov.multinivel.dto.generics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponseDTO {
    public Object result;
    public Boolean isLastPage;
}

package com.deenn.mkoboassessment.pojo.responses;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaginatedResponse {

    private Collection<?> data;

    private int size;

    private int page;

    private int pages;

    private boolean isFirst;

    private boolean isLast;

    private boolean hasNext;

    private boolean hasPrevious;

}

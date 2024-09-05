package com.example.clothdonationsystem.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 *
 * @author bhavesh
 */
public class PaginationUtil {

    private static final String DEFAULT_SORT_BY = "id";
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;
    public static final Integer DEFAULT_PAGE_NUMBER = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 10;


    public static Pageable getPageable(PaginationRequestModel paginationRequest) {
        return getPageable(paginationRequest.getPageNumber(), paginationRequest.getPageSize(),
                paginationRequest.getSortBy(), paginationRequest.getSortDirection());
    }

    public static Pageable getPageable(Integer pageNumber, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        Sort sort = getSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(validatePageNumber(pageNumber), validatePageSize(pageSize), sort);
        return pageable;
    }

    /**
     * This method returns Pageable based on parameters, if any parameters is
     * null then sets the default values.
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static Pageable getPageable(Integer pageNumber, Integer pageSize) {
        return getPageable(pageNumber, pageSize, null, null);
    }

    /**
     * This method returns Sort based on parameters, if any parameters is null
     * then sets the default values.
     *
     * @param sortBy
     * @param sortDirection
     * @return Sort
     */
    public static Sort getSort(String sortBy, Sort.Direction sortDirection) {
        if (sortBy == null) {
            sortBy = DEFAULT_SORT_BY;
        }
        if (sortDirection == null) {
            sortDirection = DEFAULT_SORT_DIRECTION;
        }
        return Sort.by(sortDirection, sortBy);
    }

    /**
     * Validates page number if it's null then sets the default values.
     *
     * @param pageNumber
     * @return
     */
    public static Integer validatePageNumber(Integer pageNumber) {
        return pageNumber != null ? pageNumber : DEFAULT_PAGE_NUMBER;
    }

    /**
     * Validates page size if it's null then sets the default values.
     *
     * @param pageSize
     * @return
     */
    public static Integer validatePageSize(Integer pageSize) {
        return pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
    }


    /**
     * Converts and returns Page into PageModel.
     *
     * @param page
     * @return
     */
    public static PageModel getPageModel(Page page) {
        return getPageModel(page, page.getContent());
    }

    /**
     * Converts and returns Page & ListResponse into PageModel.
     *
     * @param page
     * @param listResponse
     * @return
     */
    public static PageModel getPageModel(Page page, List listResponse) {
        return PageModel.builder()
                .data(listResponse != null ? listResponse : new ArrayList<>())
                .pageCount(Long.parseLong(page.getTotalPages() + ""))
                .totalCount(page.getTotalElements())
                .build();
    }


    /**
     * Converts list Of data into pagination.
     * @param data
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static <T> List<T> getPage(List<T> data, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        if (pageNumber == null){
            pageNumber = 0;
        }
        int fromIndex = (pageNumber) * pageSize;
        if(data == null || data.size() <= fromIndex){
            return Collections.emptyList();
        }
        return data.subList(fromIndex, Math.min(fromIndex + pageSize, data.size()));
    }


}
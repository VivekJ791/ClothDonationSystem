package com.example.clothdonationsystem.utils;

import java.io.Serializable;

import org.springframework.data.domain.Sort;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginationRequestModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Integer pageNumber;

    Integer pageSize;

    String sortBy;

    Sort.Direction sortDirection;

}

package com.example.clothdonationsystem.utils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object data;

    private Long totalCount;

    private Long pageCount;

}

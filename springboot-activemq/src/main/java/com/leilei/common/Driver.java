package com.leilei.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Penglei
 * @date 2020/7/17 0017.
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver implements Serializable {
    private Long id;
    private String name;
}

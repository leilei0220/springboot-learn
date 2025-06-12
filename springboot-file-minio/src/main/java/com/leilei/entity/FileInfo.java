package com.leilei.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * @author: lei
 * @date: 2025-06-12 10:40
 * @desc:
 */
@Data
@Table("file_info")
public class FileInfo {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String fileMd5;

    private String fileName;

    private Long fileSize;

    private String filePath;

    private Boolean uploadComplete;

    private String createTime;
}

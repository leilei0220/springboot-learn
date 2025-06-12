package com.leilei.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * @author: lei
 * @date: 2025-06-12 10:37
 * @desc:
 */
@Data
@Table("file_chunk")
public class FileChunk {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String fileMd5;

    private Integer chunkIndex;

    private Long chunkSize;

    private Boolean uploaded;
}

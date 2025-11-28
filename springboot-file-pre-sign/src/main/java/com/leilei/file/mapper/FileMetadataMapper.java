package com.leilei.file.mapper;

import com.leilei.file.entity.FileMetadata;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件元数据Mapper
 */
@Mapper
public interface FileMetadataMapper extends BaseMapper<FileMetadata> {
}


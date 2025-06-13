package com.leilei.mapper;

import com.leilei.entity.FileInfo;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {
    @Select("SELECT * FROM file_info WHERE file_md5 = #{fileMd5}")
    FileInfo selectByMd5(String fileMd5);

}
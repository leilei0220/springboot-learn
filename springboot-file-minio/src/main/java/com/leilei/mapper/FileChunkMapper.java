package com.leilei.mapper;

import com.leilei.entity.FileChunk;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileChunkMapper extends BaseMapper<FileChunk> {
    @Insert("INSERT IGNORE INTO file_chunk (file_md5, chunk_index, chunk_size, uploaded) VALUES (#{fileMd5}, #{chunkIndex}, #{chunkSize}, true)")
    void insertIgnore(FileChunk chunk);

    @Select("SELECT * FROM file_chunk WHERE file_md5 = #{fileMd5} AND chunk_index = #{chunkIndex}")
    FileChunk selectByMd5AndIndex(@Param("fileMd5") String fileMd5, @Param("chunkIndex") int chunkIndex);

    @Select("SELECT COUNT(*) FROM file_chunk WHERE file_md5 = #{fileMd5} AND uploaded = true")
    int countUploadedChunks(@Param("fileMd5") String fileMd5);
}
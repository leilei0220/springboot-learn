package com.leilei.service;

import com.leilei.entity.FileChunk;
import com.leilei.entity.FileInfo;
import com.leilei.mapper.FileChunkMapper;
import com.leilei.mapper.FileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired private FileChunkMapper fileChunkMapper;

    public boolean isFileExists(String fileMd5) {
        return fileInfoMapper.selectByMd5(fileMd5) != null;
    }

    public void saveChunk(String fileMd5, int chunkIndex, long chunkSize) {
        FileChunk chunk = new FileChunk();
        chunk.setFileMd5(fileMd5);
        chunk.setChunkIndex(chunkIndex);
        chunk.setChunkSize(chunkSize);
        chunk.setUploaded(true);
        fileChunkMapper.insertIgnore(chunk);
    }

    public boolean isChunkExists(String fileMd5, int chunkIndex) {
        return fileChunkMapper.selectByMd5AndIndex(fileMd5, chunkIndex) != null;
    }

    public boolean isUploadComplete(String fileMd5, int totalChunks) {
        int count = fileChunkMapper.countUploadedChunks(fileMd5);
        return count == totalChunks;
    }

    public List<String> getChunkPaths(String fileMd5, int totalChunks) {
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < totalChunks; i++) {
            paths.add("chunks/" + fileMd5 + "/" + i);
        }
        return paths;
    }

    public void markUploadComplete(String fileMd5, String finalObjectName, String fileName, long fileSize) {
        FileInfo info = new FileInfo();
        info.setFileMd5(fileMd5);
        info.setFileName(fileName);
        info.setFileSize(fileSize);
        info.setFilePath(finalObjectName);
        info.setUploadComplete(true);
        fileInfoMapper.insertData(info);
    }
}

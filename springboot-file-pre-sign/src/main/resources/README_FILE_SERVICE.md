# 文件预签名上传服务使用说明

## 功能概述

本服务实现了基于预签名URL的文件上传功能，支持MinIO和阿里云OSS两种存储后端。文件上传流程如下：

1. 前端发送文件名、MD5、文件大小等信息到后端
2. 后端生成预签名上传URL和文件唯一Key
3. 前端使用预签名URL直接上传文件到存储服务
4. 前端通知后端文件上传完成
5. 前端提交业务表单时，使用文件Key
6. 后端业务服务调用文件服务，将文件从临时目录移动到业务目录

## 数据库表

执行 `src/main/resources/db/file_metadata.sql` 创建文件元数据表。

## 配置说明

### MinIO配置（默认）

```yaml
file:
  storage:
    type: minio  # 使用MinIO存储

minio:
  endpoint: http://10.50.125.197:9000
  access-key: admin
  secret-key: 123456789
  bucket: eamp-dev
```

### 阿里云OSS配置

```yaml
file:
  storage:
    type: oss  # 使用阿里云OSS存储

oss:
  endpoint: oss-cn-hangzhou.aliyuncs.com
  access-key-id: your-access-key-id
  access-key-secret: your-access-key-secret
  bucket: your-bucket-name
```

### 文件服务配置

```yaml
file:
  # 存储类型：minio 或 oss（默认minio）
  storage:
    type: minio
  # 临时文件路径前缀
  temp-path: temp
  # 预签名URL有效期（秒，默认3600秒=1小时）
  presigned-url-expiry: 3600
```

## API接口

### 1. 获取预签名上传URL

**接口地址：** `POST /api/file/presign`

**请求参数：**
```json
{
  "fileName": "example.pdf",
  "md5": "d41d8cd98f00b204e9800998ecf8427e",
  "fileSize": 1024
}
```

**响应：**
```json
{
  "uploadUrl": "http://minio-server:9000/bucket/temp/xxx/example.pdf?X-Amz-Algorithm=...",
  "fileKey": "abc123def456..."
}
```

### 2. 通知文件上传完成

**接口地址：** `POST /api/file/upload-complete`

**请求参数：**
```json
{
  "fileKey": "abc123def456..."
}
```

**响应：**
```json
{
  "success": true,
  "message": "文件上传完成"
}
```

### 3. 移动文件到业务目录

**接口地址：** `POST /api/file/move`

**请求参数：**
```json
{
  "fileKey": "abc123def456...",
  "businessPath": "business/order/2024/01"
}
```

**响应：**
```json
{
  "success": true,
  "message": "文件移动成功"
}
```

### 4. 获取文件信息

**接口地址：** `GET /api/file/info/{fileKey}`

**响应：**
```json
{
  "fileKey": "abc123def456...",
  "fileName": "example.pdf",
  "md5": "d41d8cd98f00b204e9800998ecf8427e",
  "fileSize": 1024,
  "tempPath": "temp/abc123def456.../example.pdf",
  "businessPath": "business/order/2024/01/abc123def456.../example.pdf",
  "storageType": "minio",
  "status": "MOVED",
  "createTime": "2024-01-01T10:00:00",
  "updateTime": "2024-01-01T10:05:00"
}
```

## 前端使用示例

### JavaScript/TypeScript示例

```javascript
// 1. 获取预签名URL
async function uploadFile(file) {
  // 计算MD5（需要使用MD5库，如crypto-js）
  const md5 = await calculateMD5(file);
  
  // 请求预签名URL
  const response = await fetch('/api/file/presign', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      fileName: file.name,
      md5: md5,
      fileSize: file.size
    })
  });
  
  const { uploadUrl, fileKey } = await response.json();
  
  // 2. 使用预签名URL上传文件
  const uploadResponse = await fetch(uploadUrl, {
    method: 'PUT',
    body: file,
    headers: {
      'Content-Type': file.type,
      'Content-MD5': md5
    }
  });
  
  if (uploadResponse.ok) {
    // 3. 通知后端上传完成
    await fetch('/api/file/upload-complete', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ fileKey })
    });
    
    return fileKey;
  } else {
    throw new Error('文件上传失败');
  }
}

// 4. 提交业务表单时，使用文件Key
async function submitBusinessForm(formData) {
  const response = await fetch('/api/business/submit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      ...formData,
      fileKey: formData.fileKey  // 使用文件Key
    })
  });
  
  return response.json();
}

// 5. 业务服务中移动文件
async function moveFileToBusiness(fileKey, businessPath) {
  const response = await fetch('/api/file/move', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      fileKey: fileKey,
      businessPath: businessPath
    })
  });
  
  return response.json();
}
```

## 文件状态说明

- **UPLOADING**: 文件正在上传中（已生成预签名URL，但未完成上传）
- **UPLOADED**: 文件已上传完成（已上传到临时目录）
- **MOVED**: 文件已移动到业务目录

## 注意事项

1. 预签名URL有时效性，默认1小时，可在配置文件中调整
2. 预签名URL包含MD5和文件大小验证，确保上传的文件与请求一致
3. 文件先上传到临时目录（temp），业务提交后再移动到业务目录
4. 支持单文件上传，多文件需要多次请求
5. 文件路径格式：`{temp-path}/{fileKey}/{fileName}` 或 `{businessPath}/{fileKey}/{fileName}`


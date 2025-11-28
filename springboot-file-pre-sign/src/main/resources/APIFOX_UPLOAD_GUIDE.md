# Apifox 使用预签名URL上传文件到 MinIO 指南

## 前置准备

### 1. 获取预签名URL

首先调用 `/api/file/presign` 接口获取预签名URL：

**接口信息：**
- **方法**: POST
- **URL**: `http://localhost:8080/api/file/presign`
- **Content-Type**: `application/json`

**请求体：**
```json
{
  "fileName": "test.pdf",
  "md5": "d41d8cd98f00b204e9800998ecf8427e",
  "fileSize": 1024
}
```

**响应示例：**
```json
{
  "uploadUrl": "http://10.50.125.197:9000/eamp-dev/temp/abc123def456/test.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20240101%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240101T120000Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=xxx",
  "fileKey": "abc123def456..."
}
```

**复制 `uploadUrl` 的值，用于下一步上传。**

---

## 在 Apifox 中上传文件

### 方法一：使用 PUT 方法（推荐）

#### 步骤 1：创建新请求
1. 在 Apifox 中点击 **新建请求**
2. 选择 **PUT** 方法
3. 在 URL 输入框中粘贴预签名URL（从上面的响应中获取的 `uploadUrl`）

#### 步骤 2：设置请求头
在 **Headers** 标签页中添加以下请求头：

| Key | Value | 说明 |
|-----|-------|------|
| `Content-Type` | `application/pdf` | 根据文件类型设置（如：`image/jpeg`, `application/pdf`, `application/octet-stream`） |
| `Content-MD5` | `1B2M2Y8AsgTpgAmY7PhCfg==` | **Base64编码的MD5值**（注意：不是十六进制字符串！） |
| `Content-Length` | `1024` | 文件大小（字节），必须与获取预签名URL时提供的文件大小一致 |

**⚠️ 重要提示：**
- `Content-MD5` 必须是 **Base64 编码**的 MD5 值，不是十六进制字符串
- 十六进制 MD5（如 `d41d8cd98f00b204e9800998ecf8427e`）需要转换为 Base64（如 `1B2M2Y8AsgTpgAmY7PhCfg==`）
- `Content-Length` 必须与获取预签名URL时提供的文件大小完全一致
- 如果MD5格式错误或文件大小不匹配，MinIO会返回 "The Content-Md5 you specified is not valid" 错误

**MD5 格式转换说明：**
- 十六进制 MD5（32位）：`d41d8cd98f00b204e9800998ecf8427e`
- Base64 MD5（24位，通常以 `=` 或 `==` 结尾）：`1B2M2Y8AsgTpgAmY7PhCfg==`
- 可以使用在线工具转换，或使用后端提供的转换接口

#### 步骤 3：设置请求体
1. 切换到 **Body** 标签页
2. 选择 **binary** 或 **file** 类型
3. 点击 **选择文件**，选择要上传的文件

**或者：**
1. 选择 **raw** 类型
2. 在右侧下拉菜单中选择 **binary**
3. 点击 **选择文件**，选择要上传的文件

#### 步骤 4：发送请求
点击 **发送** 按钮

**成功响应：**
- **状态码**: `200 OK`
- **响应体**: 通常为空或包含一些XML格式的响应

**失败响应：**
- **状态码**: `403 Forbidden` - MD5或文件大小不匹配
- **状态码**: `400 Bad Request` - 请求格式错误
- **状态码**: `404 Not Found` - 预签名URL已过期或无效

---

### 方法二：使用 POST 方法（部分存储服务支持）

如果 MinIO 配置支持 POST 上传，也可以使用 POST 方法，步骤类似，但需要：
1. 方法改为 **POST**
2. 可能需要使用 `multipart/form-data` 格式
3. 其他请求头设置相同

---

## 完整示例流程

### 示例 1：上传 PDF 文件

**步骤 1 - 获取预签名URL：**
```
POST http://localhost:8080/api/file/presign
Content-Type: application/json

{
  "fileName": "document.pdf",
  "md5": "5d41402abc4b2a76b9719d911017c592",
  "fileSize": 2048
}
```

**响应：**
```json
{
  "uploadUrl": "http://10.50.125.197:9000/eamp-dev/temp/xxx/document.pdf?...",
  "fileKey": "abc123..."
}
```

**步骤 2 - 上传文件：**
```
PUT http://10.50.125.197:9000/eamp-dev/temp/xxx/document.pdf?X-Amz-Algorithm=...
Content-Type: application/pdf
Content-MD5: 5d41402abc4b2a76b9719d911017c592
Content-Length: 2048

[选择文件: document.pdf]
```

---

### 示例 2：上传图片文件

**步骤 1 - 获取预签名URL：**
```json
{
  "fileName": "image.jpg",
  "md5": "098f6bcd4621d373cade4e832627b4f6",
  "fileSize": 15360
}
```

**步骤 2 - 转换MD5格式：**
```
GET http://localhost:8080/api/file/test/md5-to-base64?hexMd5=098f6bcd4621d373cade4e832627b4f6
```

**响应：**
```json
{
  "hexMd5": "098f6bcd4621d373cade4e832627b4f6",
  "base64Md5": "CY9rzUYh03PK3k6DJie09g=="
}
```

**步骤 3 - 上传文件：**
```
PUT [预签名URL]
Content-Type: image/jpeg
Content-MD5: CY9rzUYh03PK3k6DJie09g==
Content-Length: 15360

[选择文件: image.jpg]
```

---

## 常见问题排查

### 1. 403 Forbidden 错误

**可能原因：**
- MD5值不匹配
- 文件大小不匹配
- 预签名URL已过期

**解决方法：**
- 确保 `Content-MD5` 与获取预签名URL时提供的MD5完全一致
- 确保 `Content-Length` 与获取预签名URL时提供的文件大小完全一致
- 检查预签名URL是否在有效期内（默认1小时）

### 2. 400 Bad Request 错误

**可能原因：**
- 请求头格式错误
- 文件内容损坏

**解决方法：**
- 检查请求头是否正确设置
- 重新选择文件

### 3. 404 Not Found 错误

**可能原因：**
- 预签名URL已过期
- URL格式错误

**解决方法：**
- 重新获取预签名URL
- 检查URL是否完整复制

---

## Apifox 配置截图说明

### 1. 请求方法设置
```
[PUT] [URL输入框: 粘贴预签名URL]
```

### 2. Headers 标签页
```
Headers:
  Content-Type: application/pdf
  Content-MD5: d41d8cd98f00b204e9800998ecf8427e
  Content-Length: 1024
```

### 3. Body 标签页
```
Body:
  [binary] [选择文件按钮]
  已选择: test.pdf (1024 bytes)
```

---

## 快速测试脚本

你也可以在 Apifox 中使用以下脚本来自动化流程：

### 前置脚本（Pre-request Script）
```javascript
// 如果需要先获取预签名URL，可以在这里调用
// 注意：Apifox 的前置脚本主要用于设置变量，不适合复杂的API调用
```

### 后置脚本（Post-response Script）
```javascript
// 上传成功后，可以调用通知接口
if (pm.response.code === 200) {
    console.log("文件上传成功！");
    // 可以在这里自动调用 /api/file/upload-complete 接口
}
```

---

## 注意事项

1. **MD5 和文件大小必须匹配**：预签名URL生成时已经包含了MD5和文件大小的验证，上传时必须完全一致

2. **预签名URL有效期**：默认1小时，过期后需要重新获取

3. **Content-Type**：根据实际文件类型设置，如果不确定可以使用 `application/octet-stream`

4. **文件大小限制**：确保文件大小不超过 MinIO 和 Spring Boot 的配置限制

5. **网络连接**：确保 Apifox 能够访问 MinIO 服务器地址

---

## 验证上传结果

上传成功后，可以调用以下接口验证：

```
GET http://localhost:8080/api/file/info/{fileKey}
```

或者在 MinIO 控制台中查看文件是否已上传到临时目录。


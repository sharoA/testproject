# 后端技术栈文档

## 技术栈概览
- **框架**: Spring Boot 2.7.x
- **Java版本**: Java 8
- **构建工具**: Maven 3.6+
- **数据库**: MySQL/Oracle (根据项目需求)
- **ORM框架**: MyBatis
- **API文档**: Swagger/OpenAPI 3.0
- **测试框架**: JUnit5 + Mockito + Spring Boot Test

## 项目结构
```
src/
├── main/
│   ├── java/
│   │   └── com/yourcompany/yourmicroservice/
│   │       ├── application/       # 应用层
│   │       ├── domain/            # 领域层
│   │       │   ├── model/         # 实体和值对象
│   │       │   ├── repository/    # 仓库接口
│   │       │   └── service/       # 领域服务
│   │       ├── infrastructure/    # 基础设施层
│   │       │   ├── config/        # 配置文件
│   │       │   ├── security/      # 安全配置
│   │       │   └── persistence/   # 数据持久化
│   │       └── interfaces/        # 接口层（REST Controllers）
│   └── resources/
│       ├── application.properties
│       └── bootstrap.properties
└── test/
    └── java/
```

## 开发规范
1. 遵循DDD（领域驱动设计）架构
2. 使用RESTful API设计原则
3. 实现统一的异常处理
4. 添加详细的日志记录
5. 编写完整的单元测试

## 配置说明
- 数据库连接配置
- 日志配置
- 安全配置
- API文档配置

生成时间: 2025-06-30 20:46:26

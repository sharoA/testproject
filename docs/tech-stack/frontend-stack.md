# 前端技术栈文档

## 技术栈概览
- **框架**: Vue.js 2.x
- **构建工具**: Webpack + npm
- **路由**: Vue Router
- **状态管理**: Vuex
- **HTTP客户端**: Axios
- **UI框架**: Element UI / Ant Design Vue (根据项目选择)
- **测试框架**: Jest + Vue Test Utils

## 项目结构
```
frontend/
├── build/                    # 项目构建相关代码
├── config/                   # 项目开发环境配置
├── src/
│   ├── components/           # Vue公共组件
│   ├── views/               # 页面组件
│   ├── router/              # 路由管理
│   ├── store/               # Vuex状态管理
│   ├── utils/               # 工具函数
│   ├── api/                 # API接口
│   ├── assets/              # 静态资源
│   ├── App.vue              # 根组件
│   └── main.js              # 入口文件
├── static/                   # 静态文件
├── package.json
└── index.html
```

## 开发规范
1. 组件命名使用PascalCase
2. 文件命名使用kebab-case
3. 统一的代码格式化(ESLint + Prettier)
4. 组件单一职责原则
5. 合理的组件拆分和复用

## 构建配置
- 开发环境配置
- 生产环境配置
- 代理配置
- 打包优化

生成时间: 2025-07-01 09:28:20

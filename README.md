# Gobang2022 - Gomoku / Renju (Swing Desktop)

本项目是由 **朱天宇**、**寿豪泽**、**关亮亮** 三人共同开发的人机对弈五子棋程序，包含完整的游戏界面设计、AI决策逻辑、测试验证。现已开源，欢迎学习与交流！

## 项目成员分工

- **朱天宇**：棋盘UI设计、项目架构设计
- **寿豪泽**：五子棋AI棋法设计
- **关亮亮**：项目整体测试与验证

## 环境要求

- Java 8 及以上版本
- 推荐使用 IntelliJ IDEA 导入项目（项目根目录的 `Gobang2022-2.iml` 可直接导入）

## 编译与运行

### 命令行编译

```powershell
New-Item -ItemType Directory -Force -Path .tmp\classes | Out-Null
Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding ASCII .tmp\sources.txt
javac -encoding UTF-8 -d .tmp\classes "@.tmp\sources.txt"
Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\image .tmp\classes\main\java\com\ztydwz\gobang2022\
Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\config .tmp\classes\main\java\com\ztydwz\gobang2022\
```

### 命令行运行

```powershell
java -cp .tmp\classes main.java.com.ztydwz.gobang2022.Controller.Start
```

### IntelliJ IDEA

1. 用 IntelliJ IDEA 打开项目根目录
2. 确保 `src` 被标记为 Sources Root（`.iml` 已配置）
3. 运行 `src/main/java/com/ztydwz/gobang2022/Controller/Start.java` 中的 `main` 方法

## 游戏玩法介绍

1. 启动程序后，首先点击 **`游戏设置`**。
2. 在设置界面中，选择玩家的先后手（点击 **`先手`** 或 **`后手`**），其他选项保持默认（可用于测试）。
3. 点击 **`确认`** 完成设置。
4. 点击 **`开始游戏`**，开始对弈。

### 规则说明

- 先手方（黑棋）依次下：一个黑子、一个白子、一个黑子，三子初步布局（必须符合26种默认开局之一）。
- 后手方（白棋）选择是否进行交换，若交换则黑白互换。
- 交换阶段结束后，白方下第4颗子（第2颗白子）。
- 黑方输入打点数（2~5），并在棋盘中心3×3区域中放置对应数量的黑子。
- 白方从黑方的打点中选择一颗保留，其余丢弃。
- 随后轮流落子，直到满足以下任一条件，游戏结束：
  - 黑方出现禁手（可设置是否检测禁手）
  - 任一方五子连珠
  - 棋盘落满

### 其他功能

- **导出棋谱**：游戏结束后可点击左上角 **`导出棋谱`** 保存对局记录。
- **悔棋功能**：支持悔棋操作，方便复盘或修正失误。

## 项目结构

```
src/
└── main/java/com/ztydwz/gobang2022/
    ├── Controller/   # 鼠标/游戏流程控制器
    ├── Model/        # Swing模型、棋盘全局状态、棋子/按钮/窗口对象
    ├── Service/      # AI、规则判断、绘制、棋谱导出
    ├── View/         # 顶层Swing框架和对话框辅助
    ├── image/        # 黑白棋子及指针图像资源
    └── config/       # 版本属性文件
```

- **项目入口**：`main.java.com.ztydwz.gobang2022.Controller.Start`
- **源码根目录**：`src`
- **编译输出**：使用项目根目录下的 `.tmp\classes`（已在 `.gitignore` 中忽略）

## 已知局限

- AI 棋力尚弱，搜索深度和评估质量有待提升
- 禁手检测逻辑不完整，部分 Renju 规则未正确实现
- 双人模式及自动对战模式未经充分测试
- 全局状态管理（`Static.java`）耦合度高，不利于单元测试
- 棋谱导出路径硬编码为 `C:\棋谱\`，仅 Windows 下可用
- 无自动化测试覆盖
- 无 Maven / Gradle 构建系统，需手动编译

## 开发工作流

本项目使用 Trellis 进行任务管理。开发时请遵循以下流程：

1. 基于 `main` 分支创建功能分支
2. 参考 `.trellis/spec/` 中的编码规范
3. 修改源码后，使用上述编译命令验证
4. 手动启动 Swing UI 进行冒烟测试（至少验证所修改的模式可用）

## 联系方式

如果有任何问题或建议，欢迎在 Issues 中反馈，或者直接联系开发成员。

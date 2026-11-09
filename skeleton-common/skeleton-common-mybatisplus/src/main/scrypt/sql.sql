DROP TABLE IF EXISTS sys_role_permission;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_permission;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_dept;
DROP TABLE IF EXISTS sys_config;
DROP TABLE IF EXISTS sys_dict_data;
DROP TABLE IF EXISTS sys_dict_type;
DROP TABLE IF EXISTS sys_user_post;
DROP TABLE IF EXISTS sys_post;

-- 1. 用户表 sys_user
CREATE TABLE sys_user (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
                          username VARCHAR(50) NOT NULL COMMENT '登录账号',
                          password VARCHAR(100) NOT NULL COMMENT '密码（BCrypt哈希）',
                          nickname VARCHAR(50) NOT NULL COMMENT '用户昵称',
                          email VARCHAR(100) DEFAULT '' COMMENT '邮箱',
                          phone VARCHAR(20) DEFAULT '' COMMENT '手机号',
                          avatar VARCHAR(255) DEFAULT '' COMMENT '头像地址',
                          status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1正常',
                          dept_id BIGINT DEFAULT NULL COMMENT '所属部门ID',
                          create_by BIGINT DEFAULT NULL COMMENT '创建人ID',
                          create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          update_by BIGINT DEFAULT NULL COMMENT '更新人ID',
                          update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                          UNIQUE KEY uk_username (username),
                          KEY idx_dept_id (dept_id),
                          KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 2. 角色表 sys_role
CREATE TABLE sys_role (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
                          role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
                          role_key VARCHAR(50) NOT NULL COMMENT '角色标识，如 admin、common',
                          sort INT NOT NULL DEFAULT 0 COMMENT '排序号',
                          status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1正常',
                          data_scope TINYINT DEFAULT 1 COMMENT '数据权限范围 1全部 2本部门 3本人',
                          create_by BIGINT DEFAULT NULL COMMENT '创建人ID',
                          create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          update_by BIGINT DEFAULT NULL COMMENT '更新人ID',
                          update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                          UNIQUE KEY uk_role_key (role_key),
                          KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 3. 用户角色关联表 sys_user_role
CREATE TABLE sys_user_role (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                               user_id BIGINT NOT NULL COMMENT '用户ID',
                               role_id BIGINT NOT NULL COMMENT '角色ID',
                               create_by BIGINT DEFAULT NULL COMMENT '创建人ID',
                               create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               update_by BIGINT DEFAULT NULL COMMENT '更新人ID',
                               update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                               UNIQUE KEY uk_user_role (user_id, role_id),
                               KEY idx_user_id (user_id),
                               KEY idx_role_id (role_id),
                               KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 4. 权限菜单表 sys_permission
CREATE TABLE sys_permission (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '权限ID',
                                parent_id BIGINT DEFAULT 0 COMMENT '父权限ID，0为顶级',
                                permission_name VARCHAR(50) NOT NULL COMMENT '权限名称',
                                permission_key VARCHAR(100) DEFAULT '' COMMENT '权限标识：user:list、user:add',
                                permission_type CHAR(1) NOT NULL COMMENT '类型 C目录 M菜单 B按钮',
                                path VARCHAR(255) DEFAULT '' COMMENT '前端路由地址',
                                component VARCHAR(255) DEFAULT '' COMMENT '前端组件路径',
                                icon VARCHAR(100) DEFAULT '' COMMENT '菜单图标',
                                sort INT NOT NULL DEFAULT 0 COMMENT '排序',
                                visible TINYINT NOT NULL DEFAULT 1 COMMENT '是否显示 0隐藏 1显示',
                                status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1正常',
                                create_by BIGINT DEFAULT NULL COMMENT '创建人ID',
                                create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                update_by BIGINT DEFAULT NULL COMMENT '更新人ID',
                                update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                                KEY idx_parent_id (parent_id),
                                KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限菜单表';

-- 5. 角色权限关联表 sys_role_permission
CREATE TABLE sys_role_permission (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                                     role_id BIGINT NOT NULL COMMENT '角色ID',
                                     permission_id BIGINT NOT NULL COMMENT '权限ID',
                                     create_by BIGINT DEFAULT NULL COMMENT '创建人ID',
                                     create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     update_by BIGINT DEFAULT NULL COMMENT '更新人ID',
                                     update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                                     UNIQUE KEY uk_role_permission (role_id, permission_id),
                                     KEY idx_role_id (role_id),
                                     KEY idx_permission_id (permission_id),
                                     KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 6. 部门表 sys_dept（数据权限配套）
CREATE TABLE `sys_dept` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门ID',
                            `parent_id` bigint DEFAULT 0 COMMENT '父部门ID，0=根节点',
                            `dept_name` varchar(100) NOT NULL COMMENT '部门名称',
                            `sort` int DEFAULT 0 COMMENT '排序号',
                            `leader` varchar(50) DEFAULT NULL COMMENT '负责人姓名',
                            `leader_user_id` bigint DEFAULT NULL COMMENT '负责人用户ID',
                            `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
                            `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1启用',
                            `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                            PRIMARY KEY (`id`),
                            KEY `idx_parent_id` (`parent_id`),
                            KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 7. 系统配置表 sys_config
CREATE TABLE sys_config (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '配置ID',
                            config_name VARCHAR(100) NOT NULL COMMENT '参数名称',
                            config_key VARCHAR(100) NOT NULL COMMENT '参数键名',
                            config_value VARCHAR(500) NOT NULL DEFAULT '' COMMENT '参数值',
                            config_type TINYINT NOT NULL DEFAULT 1 COMMENT '类型 0系统内置 1自定义',
                            remark VARCHAR(500) DEFAULT '' COMMENT '备注',
                            create_by BIGINT DEFAULT NULL COMMENT '创建人ID',
                            create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            update_by BIGINT DEFAULT NULL COMMENT '更新人ID',
                            update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                            UNIQUE KEY uk_config_key (config_key),
                            KEY idx_config_type (config_type),
                            KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 8. 字典类型表 sys_dict_type
CREATE TABLE sys_dict_type (
                               id BIGINT AUTO_INCREMENT COMMENT '主键ID',
                               dict_code VARCHAR(100) NOT NULL COMMENT '字典编码（唯一key，如sys_user_status）',
                               dict_name VARCHAR(100) NOT NULL COMMENT '字典名称，如：用户状态',
                               status TINYINT DEFAULT 1 COMMENT '状态：0禁用 1正常',
                               remark VARCHAR(500) DEFAULT '' COMMENT '备注',
                               sort INT DEFAULT 0 COMMENT '排序号',
                               create_by BIGINT DEFAULT NULL COMMENT '创建人ID',
                               create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               update_by BIGINT DEFAULT NULL COMMENT '更新人ID',
                               update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                               PRIMARY KEY (id),
                               UNIQUE KEY uk_dict_code (dict_code, is_deleted),
                               KEY idx_status (status),
                               KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- 9. 字典数据表 sys_dict_data
CREATE TABLE sys_dict_data (
                               id BIGINT AUTO_INCREMENT COMMENT '主键ID',
                               dict_type_id BIGINT NOT NULL COMMENT '字典类型ID，关联sys_dict_type.id',
                               dict_code VARCHAR(100) NOT NULL COMMENT '冗余字典编码，减少联表查询',
                               dict_label VARCHAR(100) NOT NULL COMMENT '字典展示标签：正常、锁定',
                               dict_value VARCHAR(100) NOT NULL COMMENT '字典存储值：1、0',
                               css_class VARCHAR(100) DEFAULT '' COMMENT '自定义css样式',
                               list_class VARCHAR(100) DEFAULT '' COMMENT '前端标签类型 success/danger/warning',
                               sort INT DEFAULT 0 COMMENT '排序号',
                               status TINYINT DEFAULT 1 COMMENT '状态：0禁用 1正常',
                               remark VARCHAR(500) DEFAULT '' COMMENT '备注',
                               create_by BIGINT DEFAULT NULL COMMENT '创建人ID',
                               create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               update_by BIGINT DEFAULT NULL COMMENT '更新人ID',
                               update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                               PRIMARY KEY (id),
                               KEY idx_dict_type_id (dict_type_id),
                               KEY idx_dict_code (dict_code),
                               KEY idx_status (status),
                               KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- 10. 岗位表 sys_post
CREATE TABLE `sys_post` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
                            `post_code` varchar(100) NOT NULL COMMENT '岗位编码，唯一（如：manager、staff）',
                            `post_name` varchar(100) NOT NULL COMMENT '岗位名称',
                            `sort` int DEFAULT 0 COMMENT '排序号',
                            `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1启用',
                            `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                            `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_post_code` (`post_code`,`is_deleted`),
                            KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位表';

-- 11. 用户岗位关联表 sys_user_post
CREATE TABLE `sys_user_post` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                 `post_id` bigint NOT NULL COMMENT '岗位ID',
                                 `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `uk_user_post` (`user_id`,`post_id`,`is_deleted`),
                                 KEY `idx_user_id` (`user_id`),
                                 KEY `idx_post_id` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户岗位关联表';

-- 插入部门
INSERT INTO sys_dept (id, parent_id, dept_name, sort, status, create_by, create_time, update_by, update_time, is_deleted) VALUES
                                                                                                                                   (1, 0, '总公司', 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                   (2, 1, '研发部', 2, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                   (3, 1, '财务部', 3, 1, NULL, NOW(), NULL, NOW(), 'N');

-- 插入角色
INSERT INTO sys_role (id, role_name, role_key, sort, status, data_scope, create_by, create_time, update_by, update_time, is_deleted) VALUES
                                                                                                                                         (1, '超级管理员', 'admin', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                         (2, '研发人员', 'dev', 2, 1, 2, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                         (3, '财务人员', 'finance', 3, 1, 2, NULL, NOW(), NULL, NOW(), 'N');

-- 插入用户（密码都是 123456）
INSERT INTO sys_user (id, username, password, nickname, email, phone, avatar, status, dept_id, create_by, create_time, update_by, update_time, is_deleted) VALUES
                                                                                                                                                               (1, 'admin', '92af33ad1a042760d992dcd862bd71c2', '系统管理员', 'admin@test.com', '13800138000', '', 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                               (2, 'zhangsan', '92af33ad1a042760d992dcd862bd71c2', '张三', 'zhangsan@test.com', '13800138001', '', 1, 2, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                               (3, 'lisi', '92af33ad1a042760d992dcd862bd71c2', '李四', 'lisi@test.com', '13800138002', '', 1, 3, NULL, NOW(), NULL, NOW(), 'N');

-- 用户角色关联
INSERT INTO sys_user_role (id, user_id, role_id, create_by, create_time, update_by, update_time, is_deleted) VALUES
                                                                                                                 (1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                 (2, 2, 2, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                 (3, 3, 3, NULL, NOW(), NULL, NOW(), 'N');

-- 权限菜单
INSERT INTO sys_permission (id, parent_id, permission_name, permission_key, permission_type, path, component, icon, sort, visible, status, create_by, create_time, update_by, update_time, is_deleted) VALUES
                                                                                                                                                                                                           (1, 0, '系统管理', 'system', 'C', '/system', 'Layout', 'system', 3, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (2, 1, '用户管理', 'system:user', 'M', '/system/user', 'system/user/index', 'user', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (3, 2, '用户查询', 'system:user:list', 'B', '', '', '', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (4, 2, '用户新增', 'system:user:add', 'B', '', '', '', 2, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (5, 2, '用户编辑', 'system:user:edit', 'B', '', '', '', 3, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (6, 2, '用户删除', 'system:user:remove', 'B', '', '', '', 4, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (7, 1, '角色管理', 'system:role', 'M', '/system/role', 'system/role/index', 'role', 2, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (8, 7, '角色查询', 'system:role:list', 'B', '', '', '', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (9, 1, '配置管理', 'system:config', 'M', '/system/config', 'system/config/index', 'config', 3, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (10, 9, '配置查询', 'system:config:list', 'B', '', '', '', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (11, 9, '配置新增', 'system:config:add', 'B', '', '', '', 2, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (12, 9, '配置编辑', 'system:config:edit', 'B', '', '', '', 3, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (13, 9, '配置删除', 'system:config:remove', 'B', '', '', '', 4, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (14, 1, '字典管理', 'system:dict', 'M', '/system/dict', 'system/dict/index', 'dict', 4, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (15, 14, '字典查询', 'system:dict:list', 'B', '', '', '', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (16, 14, '字典新增', 'system:dict:add', 'B', '', '', '', 2, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (17, 14, '字典编辑', 'system:dict:edit', 'B', '', '', '', 3, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (18, 14, '字典删除', 'system:dict:remove', 'B', '', '', '', 4, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (19, 29, '部门管理', 'system:dept', 'M', '/system/dept', 'system/dept/index', 'dept', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (20, 19, '部门查询', 'system:dept:list', 'B', '', '', '', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (21, 19, '部门新增', 'system:dept:add', 'B', '', '', '', 2, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (22, 19, '部门编辑', 'system:dept:edit', 'B', '', '', '', 3, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (23, 19, '部门删除', 'system:dept:remove', 'B', '', '', '', 4, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (24, 29, '岗位管理', 'system:post', 'M', '/system/post', 'system/post/index', 'post', 2, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (25, 24, '岗位查询', 'system:post:list', 'B', '', '', '', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (26, 24, '岗位新增', 'system:post:add', 'B', '', '', '', 2, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (27, 24, '岗位编辑', 'system:post:edit', 'B', '', '', '', 3, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                                                                           (28, 24, '岗位删除', 'system:post:remove', 'B', '', '', '', 4, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (29, 0, '公司管理', 'company', 'C', '/company', '/company', 'company', 2, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (30, 0, '首页', 'dashboard', 'M', '/dashboard', '/dashboard/index', 'dashboard', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (31, 1, '菜单管理', 'system:menu', 'M', '/system/menu', 'system/menu/index', 'menu', 5, 1, 1, NULL, NOW(), NULL, NOW(), 'N');

-- 角色权限关联
INSERT INTO sys_role_permission (id, role_id, permission_id, create_by, create_time, update_by, update_time, is_deleted) VALUES
                                                                                                                             (1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (2, 1, 2, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (3, 1, 3, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (4, 1, 4, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (5, 1, 5, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (6, 1, 6, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (7, 1, 7, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (8, 1, 8, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (15, 1, 9, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (16, 1, 10, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (17, 1, 11, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (18, 1, 12, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (19, 1, 13, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (20, 1, 14, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (21, 1, 15, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (22, 1, 16, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (23, 1, 17, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (24, 1, 18, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (25, 1, 19, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (26, 1, 20, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (27, 1, 21, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (28, 1, 22, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (29, 1, 23, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (30, 1, 24, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (31, 1, 25, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (32, 1, 26, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (33, 1, 27, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (34, 1, 28, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (9, 2, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (10, 2, 2, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (11, 2, 3, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (12, 3, 1, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (13, 3, 7, NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                             (14, 3, 8, NULL, NOW(), NULL, NOW(), 'N'),
    (35, 1, 29, NULL, NOW(), NULL, NOW(), 'N'),
    (36, 1, 30, NULL, NOW(), NULL, NOW(), 'N'),
    (37, 1, 31, NULL, NOW(), NULL, NOW(), 'N');

-- 系统配置
INSERT INTO sys_config (id, config_name, config_key, config_value, config_type, remark, create_by, create_time, update_by, update_time, is_deleted) VALUES
                                                                                                                                                        (1, '用户初始密码', 'sys.user.initPassword', '123456', 0, '新建用户时的默认登录密码', NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                        (2, '账号自助注册', 'sys.account.registerEnabled', 'false', 0, '是否开启账号自助注册', NULL, NOW(), NULL, NOW(), 'N'),
                                                                                                                                                        (3, '登录验证码开关', 'sys.account.captchaEnabled', 'true', 1, '登录时是否校验图形验证码', NULL, NOW(), NULL, NOW(), 'N');

-- 字典类型：用户状态
INSERT INTO sys_dict_type(dict_code, dict_name, status, remark, sort) VALUES ('sys_user_status','用户状态',1,'账号启用/锁定状态',1);

-- 字典项
INSERT INTO sys_dict_data(dict_type_id, dict_code, dict_label, dict_value, list_class, sort, status) VALUES
                                                                                                         (1,'sys_user_status','正常','1','success',1,1),
                                                                                                         (1,'sys_user_status','锁定','0','danger',2,1);

-- 初始化岗位数据
INSERT INTO sys_post (id, post_code, post_name, sort, status, remark, create_by, create_time, update_by, update_time, is_deleted)
VALUES
    (1, 'ceo', '总经理', 1, 1, '总公司负责人', NULL, NOW(), NULL, NOW(), 'N'),
    (2, 'dev_manager', '研发主管', 2, 1, '研发部负责人', NULL, NOW(), NULL, NOW(), 'N'),
    (3, 'dev', '开发工程师', 3, 1, '研发人员', NULL, NOW(), NULL, NOW(), 'N'),
    (4, 'finance_manager', '财务主管', 4, 1, '财务部负责人', NULL, NOW(), NULL, NOW(), 'N'),
    (5, 'finance', '财务专员', 5, 1, '财务人员', NULL, NOW(), NULL, NOW(), 'N');

-- 用户岗位关联示例
INSERT INTO sys_user_post (user_id, post_id, create_by, create_time, is_deleted)
VALUES
    (1, 1, NULL, NOW(), 'N'),
    (2, 3, NULL, NOW(), 'N'),
    (3, 5, NULL, NOW(), 'N');

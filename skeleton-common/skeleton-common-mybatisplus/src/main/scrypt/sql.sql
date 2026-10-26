DROP TABLE IF EXISTS sys_role_permission;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_permission;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_dept;

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
CREATE TABLE sys_dept (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '部门ID',
                          parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
                          dept_name VARCHAR(50) NOT NULL COMMENT '部门名称',
                          sort INT NOT NULL DEFAULT 0 COMMENT '排序',
                          status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1正常',
                          create_by BIGINT DEFAULT NULL COMMENT '创建人ID',
                          create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          update_by BIGINT DEFAULT NULL COMMENT '更新人ID',
                          update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 N-未删除 Y-已删除',
                          KEY idx_parent_id (parent_id),
                          KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';


-- 插入部门
INSERT INTO sys_dept (id, parent_id, dept_name, sort, status, create_by, create_time, update_by, update_time, is_deleted)
VALUES (1, 0, '总公司', 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
       (2, 1, '研发部', 2, 1, NULL, NOW(), NULL, NOW(), 'N'),
       (3, 1, '财务部', 3, 1, NULL, NOW(), NULL, NOW(), 'N');

-- 插入角色
INSERT INTO sys_role (id, role_name, role_key, sort, status, data_scope, create_by, create_time, update_by, update_time, is_deleted)
VALUES (1, '超级管理员', 'admin', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
       (2, '研发人员', 'dev', 2, 1, 2, NULL, NOW(), NULL, NOW(), 'N'),
       (3, '财务人员', 'finance', 3, 1, 2, NULL, NOW(), NULL, NOW(), 'N');

-- 插入用户（密码都是 123456，BCrypt加密串）
INSERT INTO sys_user (id, username, password, nickname, email, phone, avatar, status, dept_id, create_by, create_time, update_by, update_time, is_deleted)
VALUES
    (1, 'admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '系统管理员', 'admin@test.com', '13800138000', '', 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (2, 'zhangsan', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '张三', 'zhangsan@test.com', '13800138001', '', 1, 2, NULL, NOW(), NULL, NOW(), 'N'),
    (3, 'lisi', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '李四', 'lisi@test.com', '13800138002', '', 1, 3, NULL, NOW(), NULL, NOW(), 'N');

-- 用户角色关联
INSERT INTO sys_user_role (id, user_id, role_id, create_by, create_time, update_by, update_time, is_deleted)
VALUES (1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
       (2, 2, 2, NULL, NOW(), NULL, NOW(), 'N'),
       (3, 3, 3, NULL, NOW(), NULL, NOW(), 'N');

-- 权限菜单
INSERT INTO sys_permission (id, parent_id, permission_name, permission_key, permission_type, path, component, icon, sort, visible, status, create_by, create_time, update_by, update_time, is_deleted)
VALUES
    (1, 0, '系统管理', 'system', 'C', '/system', 'Layout', 'system', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (2, 1, '用户管理', 'system:user', 'M', '/system/user', 'system/user/index', 'user', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (3, 2, '用户查询', 'system:user:list', 'B', '', '', '', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (4, 2, '用户新增', 'system:user:add', 'B', '', '', '', 2, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (5, 2, '用户编辑', 'system:user:edit', 'B', '', '', '', 3, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (6, 2, '用户删除', 'system:user:remove', 'B', '', '', '', 4, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (7, 1, '角色管理', 'system:role', 'M', '/system/role', 'system/role/index', 'role', 2, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (8, 7, '角色查询', 'system:role:list', 'B', '', '', '', 1, 1, 1, NULL, NOW(), NULL, NOW(), 'N');

-- 角色权限关联
-- admin 拥有全部权限
INSERT INTO sys_role_permission (id, role_id, permission_id, create_by, create_time, update_by, update_time, is_deleted)
VALUES
    (1, 1, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (2, 1, 2, NULL, NOW(), NULL, NOW(), 'N'),
    (3, 1, 3, NULL, NOW(), NULL, NOW(), 'N'),
    (4, 1, 4, NULL, NOW(), NULL, NOW(), 'N'),
    (5, 1, 5, NULL, NOW(), NULL, NOW(), 'N'),
    (6, 1, 6, NULL, NOW(), NULL, NOW(), 'N'),
    (7, 1, 7, NULL, NOW(), NULL, NOW(), 'N'),
    (8, 1, 8, NULL, NOW(), NULL, NOW(), 'N'),
-- dev 只有用户查询权限
    (9, 2, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (10, 2, 2, NULL, NOW(), NULL, NOW(), 'N'),
    (11, 2, 3, NULL, NOW(), NULL, NOW(), 'N'),
-- finance 只有角色查询权限
    (12, 3, 1, NULL, NOW(), NULL, NOW(), 'N'),
    (13, 3, 7, NULL, NOW(), NULL, NOW(), 'N'),
    (14, 3, 8, NULL, NOW(), NULL, NOW(), 'N');
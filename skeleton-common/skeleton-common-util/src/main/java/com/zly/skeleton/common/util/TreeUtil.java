package com.zly.skeleton.common.util;

import com.zly.skeleton.common.util.inter.CreateTree;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author ZLY
 * @since 2026/10/26 18:06
 */
public class TreeUtil
{

    /**
     * 构建树结构 (需要父节点)
     * @param list 原始数据
     * @param rootParentCode 根节点父编码
     * @return 树
     */
    public static <T extends CreateTree<T>> List<T> buildTree(List<T> list, String rootParentCode) {
        // 按code分组
        Map<String, T> map = list.stream().collect(Collectors.toMap(CreateTree::getCode, o -> o));

        List<T> rootList = new ArrayList<>();
        for (T item : list) {
            String parentCode = item.getParentCode();
            T parent = map.get(parentCode);
            if (parent != null) {
                // 找到父节点，把子节点塞进去
                List<T> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                }
                children.add(item);
                parent.setChildren(children);
            } else if (rootParentCode.equals(parentCode)) {
                // 根节点
                rootList.add(item);
            }
        }
        return rootList;
    }

    /**
     * 构建树结构
     * 规则：
     * 1. 能找到父节点的，挂载到父节点的children中
     * 2. 找不到父节点的（包括parentCode等于rootParentCode、或者parentCode在数据里不存在），全部作为一级节点展示
     * @param list 原始数据
     * @param rootParentCode 根节点父编码
     * @return 树
     */
    public static <T extends CreateTree<T>> List<T> buildTreeIgnoreParent(List<T> list, String rootParentCode) {
        Map<String, T> map = list.stream().collect(Collectors.toMap(CreateTree::getCode, o -> o));
        List<T> rootList = new ArrayList<>();
        for (T item : list) {
            String parentCode = item.getParentCode();
            T parent = map.get(parentCode);
            if (parent != null) {
                List<T> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                }
                children.add(item);
                parent.setChildren(children);
            } else {
                rootList.add(item);
            }
        }
        return rootList;
    }

}

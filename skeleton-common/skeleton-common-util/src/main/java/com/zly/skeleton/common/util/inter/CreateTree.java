package com.zly.skeleton.common.util.inter;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 18:11
 */
public interface CreateTree<T extends CreateTree<T>>
{
    String getCode();

    String getParentCode();

    void setChildren(List<T> childrenTree);

    List<T> getChildren();

}
    
package com.haiya.database.sharding;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 分页查询优化工具类
 * 提供游标分页等优化方案
 */
public class PaginationOptimizationUtil {

    /**
     * 游标分页结果
     *
     * @param <T> 数据类型
     */
    public static class CursorPageResult<T> {
        private List<T> data;
        private String nextCursor;
        private boolean hasNext;

        public CursorPageResult(List<T> data, String nextCursor, boolean hasNext) {
            this.data = data;
            this.nextCursor = nextCursor;
            this.hasNext = hasNext;
        }

        // Getters and setters
        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }

        public String getNextCursor() {
            return nextCursor;
        }

        public void setNextCursor(String nextCursor) {
            this.nextCursor = nextCursor;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }
    }

    /**
     * 基于ID的游标分页查询
     * 避免深度分页性能问题
     *
     * @param data       数据列表
     * @param pageSize   每页大小
     * @param <T>        数据类型，需要实现获取ID的方法
     * @return 游标分页结果
     */
    public static <T extends Identifiable> CursorPageResult<T> cursorPagination(
            List<T> data, int pageSize) {
        return cursorPagination(data, null, pageSize);
    }

    /**
     * 基于ID的游标分页查询（带起始游标）
     *
     * @param data       数据列表
     * @param cursor     起始游标（上次查询返回的nextCursor）
     * @param pageSize   每页大小
     * @param <T>        数据类型
     * @return 游标分页结果
     */
    public static <T extends Identifiable> CursorPageResult<T> cursorPagination(
            List<T> data, String cursor, int pageSize) {
        
        if (CollectionUtils.isEmpty(data)) {
            return new CursorPageResult<>(data, null, false);
        }

        // 根据游标过滤数据
        int startIndex = 0;
        if (cursor != null && !cursor.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                if (String.valueOf(data.get(i).getId()).equals(cursor)) {
                    startIndex = i + 1;
                    break;
                }
            }
        }

        // 获取当前页数据
        int endIndex = Math.min(startIndex + pageSize, data.size());
        List<T> pageData = data.subList(startIndex, endIndex);

        // 计算下一个游标
        String nextCursor = null;
        boolean hasNext = false;
        if (!pageData.isEmpty() && endIndex < data.size()) {
            T lastItem = pageData.get(pageData.size() - 1);
            nextCursor = String.valueOf(lastItem.getId());
            hasNext = true;
        }

        return new CursorPageResult<>(pageData, nextCursor, hasNext);
    }

    /**
     * 可标识接口
     */
    public interface Identifiable {
        /**
         * 获取唯一标识
         *
         * @return 唯一标识
         */
        Object getId();
    }
}
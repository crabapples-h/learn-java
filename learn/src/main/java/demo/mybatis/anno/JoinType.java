package demo.mybatis.anno;

/**
 * Join类型枚举
 */
public enum JoinType {
    INNER_JOIN("INNER JOIN"),
    LEFT_JOIN("LEFT JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
    FULL_JOIN("FULL JOIN");

    private final String value;

    JoinType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

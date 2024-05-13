import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RecommendUtils {
    private final static Logger logger = LoggerFactory.getLogger(RecommendUtils.class);

    /**
     * 计算两个用户皮尔逊相关系数
     *
     * @param user1 第一个用户的评分数据
     * @param user2 第二个用户的评分数据
     * @return 皮尔逊相关系数
     */
    private static double pearsonCorrelation(Double[] user1, Double[] user2) {
        double sumXY = 0, sumX = 0, sumY = 0, sumXSquare = 0, sumYSquare = 0;
        int n = user1.length;

        logger.debug("开始计算相关系数的分子和分母");
        for (int i = 0; i < n; i++) {
            sumXY += user1[i] * user2[i];
            sumX += user1[i];
            sumY += user2[i];
            sumXSquare += Math.pow(user1[i], 2);
            sumYSquare += Math.pow(user2[i], 2);
        }
        double numerator = sumXY - (sumX * sumY) / n;
        double denominator = Math.sqrt((sumXSquare - Math.pow(sumX, 2) / n) * (sumYSquare - Math.pow(sumY, 2) / n));
        // 避免除零错误
        if (denominator == 0) return 0;
        double value = numerator / denominator;
        logger.info("[{}]和[{}]的皮尔逊相似系数为:[{}]", user1, user2, value);
        return value;
    }

    /**
     * 获取与目标用户最相似的前 k 个用户
     *
     * @param ratings    评分数据
     * @param targetUser 目标用户下标
     * @param k          和目标用户最相似的用户个数
     * @return 和目标用户最相似的用户下标
     */
    private static List<Integer> getTopSimilarUsers(Double[][] ratings, int targetUser, int k) {
        Map<Integer, Double> similarities = new HashMap<>();
        logger.debug("开始计算目标用户与其他用户的相似度");
        for (int i = 0; i < ratings.length; i++) {
            if (i == targetUser) continue; // 跳过目标用户自身
            double similarity = pearsonCorrelation(ratings[targetUser], ratings[i]); // 计算相似度
            similarities.put(i, similarity);
        }
        List<Map.Entry<Integer, Double>> sortedSimilarities = new ArrayList<>(similarities.entrySet());
        logger.info("用户相似度:[{}]", sortedSimilarities);
        // 根据相似度降序排序
        sortedSimilarities.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        logger.info("相似度降序排序后的结果为:[{}]", sortedSimilarities);
        List<Integer> topSimilarUsers = new ArrayList<>();
        for (int i = 0; i < k && i < sortedSimilarities.size(); i++) {
            topSimilarUsers.add(sortedSimilarities.get(i).getKey());
        }
        return topSimilarUsers;
    }

    /**
     * 根据相似用户的喜好向目标用户推荐商品
     *
     * @param jumpSelf     是否跳过用户已经评分过的商品
     * @param ratings      所有用户的评分数据
     * @param targetUser   目标用户的下标
     * @param similarUsers 最相似的用户列表
     * @return 推荐的商品列表
     */
    private static List<Integer> recommendItems(boolean jumpSelf, Double[][] ratings, int targetUser, List<Integer> similarUsers) {
        Map<Integer, Double> recommendedItems = new HashMap<>(); // 保存推荐商品的Map

        // 遍历相似用户的评分情况，将相似用户喜欢的商品添加到推荐列表中
        for (int user : similarUsers) {
            for (int i = 0; i < ratings[user].length; i++) {
                if (ratings[user][i] > 0 && (jumpSelf || ratings[targetUser][i] == 0)) { // 如果相似用户喜欢该商品且目标用户未评分
                    recommendedItems.put(i, recommendedItems.getOrDefault(i, 0.0) + ratings[user][i]); // 将该商品加入推荐列表
                }
            }
        }
        List<Map.Entry<Integer, Double>> sortedRecommendedItems = new ArrayList<>(recommendedItems.entrySet());
        sortedRecommendedItems.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        logger.debug("给用户推荐的商品根据评分降序后列表为:[{}]", recommendedItems);
        // 获取推荐的商品列表
        List<Integer> topRecommendedItems = new ArrayList<>();
        for (int i = 0; i < sortedRecommendedItems.size(); i++) {
            topRecommendedItems.add(sortedRecommendedItems.get(i).getKey()); // 添加推荐商品到列表中
        }

        return topRecommendedItems;
    }

    public static void main(String[] args) {
        // 测试评分数据集
        Double[][] ratings = {{0.5, 0.25, 0D, 0D}, {0.5, 0.25, 0D, 1D},};
        int targetUser = 0; // 目标用户
        recommend(true, ratings, targetUser, 2);
    }

    /**
     * @param jumpSelf   是否跳过自身已评分的商品
     * @param ratings    评分数据集
     * @param targetUser 目标用户下标(行下标)
     * @param num        需要推荐的商品数量
     * @return 推荐的商品下标
     */
    public static List<Integer> recommend(boolean jumpSelf, Double[][] ratings, int targetUser, int num) {
        int k = 3; // 返回相似用户的数量
        // 获取相似用户列表
        List<Integer> similarUsers = getTopSimilarUsers(ratings, targetUser, k);
        logger.info("和用户:[{}]相似性最高的[{}]个用户为:[{}]", targetUser, k, similarUsers);
        // 获取推荐商品列表
        List<Integer> recommendedItems = recommendItems(jumpSelf, ratings, targetUser, similarUsers);
        logger.debug("给用户推荐的商品列表为:[{}]", recommendedItems);
        // 输出结果
//        System.out.println("给用户推荐的商品是：");
//        for (Integer item : recommendedItems) {
//            System.out.println("商品" + (item + 1)); // 打印推荐的商品列表
//        }
        return recommendedItems.subList(0, num);
    }
}

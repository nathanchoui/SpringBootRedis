package personal.nathan.article_vote.service;

/**
 * Description:
 * <p>
 * Created by nathan.z on 2017/12/22.
 */
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleServiceImpl implements ArticleService {
    // 一周时间（秒）
    private static final int ONE_WEEK_IN_SECONDS = 7 * 86400;
    // 一个投票的分值 （86400/200）
    private static final int VOTE_SCORE = 432;
    private static final int ARTICLES_PER_PAGE = 25;

    @Resource(name="redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void aticleVote(String user, String article) {
        // 允许文章从发布的一个之内投票，计算截止日期
        long cutoff = (System.currentTimeMillis() / 1000) - ONE_WEEK_IN_SECONDS;
        Double currentTime =  redisTemplate.opsForZSet().score("time:", article);
        if (currentTime != null &&currentTime < cutoff){
            return;
        }

        String articleId = article.substring(article.indexOf(':') + 1);
        // 第一次投票
        if (redisTemplate.opsForSet().add("voted:" + articleId, article.indexOf(':') + 1) == 1) {
            // 投票分值增加
            redisTemplate.opsForZSet().incrementScore("score:" , article , VOTE_SCORE);
            // 增加文章的票数
            redisTemplate.opsForHash().increment(article, "votes", 1L);
        }
    }
}

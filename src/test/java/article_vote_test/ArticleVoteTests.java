package article_vote_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import personal.nathan.article_vote.service.ArticleService;
import personal.nathan.config.Application;

/**
 * Description:
 * <p>
 * Created by nathan.z on 2017/12/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleVoteTests {

    @Autowired
    private ArticleService articleService;

    @Test
    public void testArticleVote() {
        articleService.aticleVote("user:nathan", "article:00001");
    }
}

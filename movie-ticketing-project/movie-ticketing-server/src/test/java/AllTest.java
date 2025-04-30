import cn.hutool.crypto.digest.DigestUtil;
import com.movie.config.SpringConfig;
import com.movie.entity.Movie;
import com.movie.entity.User;
import com.movie.service.CinemaService;
import com.movie.service.MobileDisplayService;
import com.movie.service.MovieService;
import com.movie.service.MovieTypeService;
import com.movie.utils.AesUtils;
import com.movie.utils.MD5Utils;
import com.movie.utils.TokenUtils;
import com.movie.utils.UUIDUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@SpringJUnitWebConfig
public class AllTest {
    @Autowired
    private MD5Utils md5Utils;
    @Autowired
    private AesUtils aesUtils;
    @Autowired
    private MobileDisplayService mobileDisplayService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieTypeService movieTypeService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private CinemaService cinemaService;
    @Test
    public void aesTest() throws Exception {
        String decrypt = aesUtils.decrypt("U2FsdGVkX1+lyGzhj+Nrr90rgKTR+LwDu4fxgB9Hj1M=");
        System.out.println(decrypt);
    }
    @Test
    public void md5Test() throws Exception {
        System.out.println(DigestUtil.md5Hex("qq123456"));
        System.out.println(UUIDUtils.generateUUID());
    }
    @Autowired
    private MultipartResolver multipartResolver;
    @Test
    public void multipartTest() throws Exception {
        if (multipartResolver == null) {
            throw new IllegalStateException("MultipartResolver is not initialized");
        }else {
            System.out.println("MultipartResolver is initialized");
        }
    }
    @Test
    public void UUIDTest(){
        for(int i=0;i<27;i++){
            System.out.println(UUIDUtils.generateUUID());
        }
    }
    @Test
    public void selectAllMovieType(){
        System.out.println(movieTypeService.selectAllMovieType());
    }
    @Test
    public void selectAllAllowedMobileDisplaysTest(){
        System.out.println(mobileDisplayService.selectAllAllowedMobileDisplays());
    }
    @Test
    public void selectMovieListTest(){
        System.out.println(movieService.selectMovieList(new Movie(),0,4,1));
    }
    @Test
    public void tokenUtilsTest() throws IllegalAccessException, InstantiationException {
        User user = new User();
        user.setUserId("123456");
        user.setUserEmail("123456@qq.com");
        user.setUserName("test");
        user.setUserProfilePicture("http://www.baidu.com");
        user.setUserPoints(100);
        user.setUserLevel(1);
        String token = tokenUtils.createToken(user);
        System.out.println(token);
        boolean b = tokenUtils.verifyToken(token);
        if(b) System.out.println("验证成功");
        else System.out.println("验证失败");
        User user2 = tokenUtils.parseToken(token, User.class);
        System.out.println(user2);
    }
    @Test
    public void customerSelectLowPriceCinema(){
        System.out.println(cinemaService.customerSelectLowPriceCinema(null,null,"b6d825944ee241998d0723336c407f79",1,10));
    }
}

import cn.hutool.crypto.digest.DigestUtil;
import com.movie.config.SpringConfig;
import com.movie.utils.AesUtils;
import com.movie.utils.MD5Utils;
import com.movie.utils.UUIDUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@SpringJUnitWebConfig
public class AllTest {
    @Autowired
    private AesUtils aesUtils;
    @Autowired
    private MD5Utils md5Utils;
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
}

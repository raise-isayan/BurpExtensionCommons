package extension.helpers;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.interfaces.EdECPrivateKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author isayan
 */
public class CerfiticateTest {
    private final static BouncyCastleProvider BC_PROVIDER = new BouncyCastleProvider();


    public CerfiticateTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(BC_PROVIDER);
        }
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     */
    @Test
    public void testKeyPairGenerator() throws NoSuchProviderException {
        System.out.println("testKeyPairGenerator");
        org.bouncycastle.asn1.x500.X500Name subjectDN = new org.bouncycastle.asn1.x500.X500Name("cn=hoge, ou=fuga, o=\"Foo Co., Ltd.\", c=JP");
        try {
            {
                // RSA
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
                keyGen.initialize(1024);
                KeyPair keyPair = keyGen.generateKeyPair();
                System.out.println("algo:" + keyPair.getPrivate().getAlgorithm());
                java.security.cert.X509Certificate issuerCA = BouncyUtil.createRootCA(keyPair, subjectDN, 10);
            }
            {
                // DSA
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", BouncyCastleProvider.PROVIDER_NAME);
                keyGen.initialize(2048);
                KeyPair keyPair = keyGen.generateKeyPair();
                System.out.println("algo:" + keyPair.getPrivate().getAlgorithm());
                java.security.cert.X509Certificate issuerCA = BouncyUtil.createRootCA(keyPair, subjectDN, 10);
            }
            {
                // EC
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
//                keyGen.initialize(256);
                keyGen.initialize(224);
                KeyPair keyPair = keyGen.generateKeyPair();
                System.out.println("algo:" + keyPair.getPrivate().getAlgorithm());
                java.security.cert.X509Certificate issuerCA = BouncyUtil.createRootCA(keyPair, subjectDN, 10);
            }
            {
                // Ed25519
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("Ed25519", BouncyCastleProvider.PROVIDER_NAME);
                keyGen.initialize(256);
                KeyPair keyPair = keyGen.generateKeyPair();
                System.out.println("algo:" + keyPair.getPrivate().getAlgorithm());
                System.out.println("isEdECPrivateKey:" + (keyPair.getPrivate() instanceof EdECPrivateKey));
                java.security.cert.X509Certificate issuerCA = BouncyUtil.createRootCA(keyPair, subjectDN, 10);
            }
            {
                // Ed448
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("Ed448", BouncyCastleProvider.PROVIDER_NAME);
                keyGen.initialize(448);
                KeyPair keyPair = keyGen.generateKeyPair();
                System.out.println("algo:" + keyPair.getPrivate().getAlgorithm());
                System.out.println("isEdECPrivateKey:" + (keyPair.getPrivate() instanceof EdECPrivateKey));
                java.security.cert.X509Certificate issuerCA = BouncyUtil.createRootCA(keyPair, subjectDN, 10);
            }

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (CertificateException ex) {
            ex.printStackTrace();
        }
    }

}

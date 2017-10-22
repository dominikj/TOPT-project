package pl.mkoi.project;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RsaPssAlgorithmTest {
//  @Spy
//  CryptoFacade cryptoUtils = new CryptoFacade();
//
//  @Spy
//  RsaCryptoService rsacryptoService = new RsaCryptoService(cryptoUtils);
//
//  @InjectMocks
//  private RsapssAlgorithmService rsapssAlgorithmService;
//
//  private byte[] fileToTest;
//
//  @Test
//  public void testSignFileWithoutEncryption() throws IOException, ClassNotFoundException {
//    Path path = Paths.get("src/main/webapp/rsapss.jsp");
//    fileToTest = Files.readAllBytes(path);
//
//    doAnswer(returnsFirstArg()).when(rsacryptoService).encrypt(any(), any());
//    doAnswer(returnsFirstArg()).when(rsacryptoService).decrypt(any(), any());
//
//    KeyPair keys = Mockito.spy(RsaKeyPair.class);
//
//    byte[] sign = rsapssAlgorithmService.signFile(fileToTest, keys);
//
//    assertTrue(rsapssAlgorithmService.verifySign(fileToTest, sign, keys));
//
//  }
//
//  @Test
//  public void testSignFileWithEncryption() throws IOException, ClassNotFoundException {
//    Path path = Paths.get("src/main/webapp/rsapss.jsp");
//    fileToTest = Files.readAllBytes(path);
//
//    KeyPair keys = rsacryptoService.generateKeys(1024);
//    byte[] sign = rsapssAlgorithmService.signFile(fileToTest, keys);
//
//    assertTrue(rsapssAlgorithmService.verifySign(fileToTest, sign, keys));
//
//  }
}
